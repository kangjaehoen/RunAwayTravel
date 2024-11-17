package com.example.runawaytravel.jwt;

import com.example.runawaytravel.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res){
        String username = obtainUsername(req);
        String password = obtainPassword(req);

        System.out.println(username);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal(); //user 가져나오기
        String username = userDetails.getUsername(); //username 뽑아내기

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();//collection 에서 authority를 뽑아낸다
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator(); //iterator 를 통해서 반복을 시켜서 내부 객체를 뽑아 낸다
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority(); //role값 가져오기

        //뽑아낸 username 과 role값을 가지고 jwtUtil에 토큰을 만들어 달라고 전달한다.
        String token = jwtUtil.createJwt(username, role, 60*60*10L);

        res.addHeader("Authorization", "Bearer " + token); //인증방식을 붙이고 토큰 붙이고 사용 (필수) HTTP 인증 방식 -> rfc 7235 정의에 따라 인증 헤더 형태를 가져야 함
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) {
        res.setStatus(401);
    }
}

