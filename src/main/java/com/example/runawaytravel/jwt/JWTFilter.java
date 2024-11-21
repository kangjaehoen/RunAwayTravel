package com.example.runawaytravel.jwt;

import com.example.runawaytravel.DTO.CustomUserDetails;
import com.example.runawaytravel.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil; //jwtutil받아와야 한다

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        //request에서 authorization 헤더를 찾음
        String authorization = req.getHeader("Authorization");
        //뽑아온 키값에서 authorization 변수에 토큰이 담겼는지, 그게 null인지 아님 인증방식이 bearer 적두사를 가지는지 (아닌경우 메서드 종료)
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null or invalid");
            filterChain.doFilter(req, res);
            return;
        }
//토큰 분리후 토큰의 소멸시간을 검증한다
        String token = authorization.split(" ")[1]; //토큰의 앞부분 bearer를 삭제 (List 두개 중 첫번째를 토큰으로 받는다)
        System.out.println(token+"JWTFilter토큰");
        if (jwtUtil.isExpired(token)) { //토큰이 존재하는데 소멸시간이 지났는지 보기 위해 if true 토큰이 만료되어 종료 하고 필터체인으로 다음 필터한테 받은 response, request를 넘겨준다
            System.out.println("token expired");
            filterChain.doFilter(req, res);

            return;
        }
        //그 토큰을 기반으로 일시적인 세션을 만들어서 security context holder <- 시큐리티 세션에다 우리의 유저를 일시적으로 저장시킨다
        //특정한 어드민 경로나 유저 정보를 요구하는 요청을 진행할 수 있다.

        String username = jwtUtil.getUsername(token); //유저 객체를 담는 유저엔티티클래스에 그 값을 초기화 시킨다.
        String role = jwtUtil.getRole(token);
        //비밀번호는 토큰에 안담겨있지만 같이 초기화 진행 해줘야 한다
        //비밀번호를 디비에서 조회 하면 매번 요청이 올때마다 디비에서 계속 조회하게 된다. (노굿) <- 그래서 임시로 하나 만들면 된다

        User user = new User();
        user.setUsername(username);
        user.setPassword("temppw");
        user.setRole(role); //유저 객체를 만든다

        //커스텀유저디테일에 객체를 넣어서 유저디테일을 하나 만들어 준다
        CustomUserDetails userDetails = new CustomUserDetails(user);
        //커스텀유저디테일 객체로 토큰을 만들어서 auth토큰을 생성한다 (디테일 담고 잇는 걸 담아주고 authority값을 리턴해주면 된다)
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken); //이 요청으로 유저 세션 생성 가능 생성됐기땜에 특정한 경로에 접근 가능
        //메서드 종료후 필터 체인을 통해 그 다음 필터한테 우리의 필터가 받았던 request, response를 넘겨준다.
        filterChain.doFilter(req, res); //jwt 검증하는 필터 생성 완
    }
}
