package com.example.runawaytravel.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    private Key key;//객체 키 만들기


    public JWTUtil(@Value("${spring.jwt.secret}")String secret) { //변수 데이터 들고 오기 (변수 받기)

        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(byteSecretKey); //암호화 하기 위한 메서드
    }

    public String getUsername(String token) { //검증 진행하는 메서드 (유저이름)
        System.out.println("JWTUtil getUsername");
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class); //Jwts.parser 를 사용해 토큰을 전달받아 내부 데이터 확인
    }// 토큰이 이 서버에서 생성되었는지 토큰이 내가 가지고 있는 키랑 맞는지 확인해준다. 클래임 확인하고 페이로드부분에서 특정 데이터 가져온다 (get을 통해서) <- 데이터 형식 string

    public String getRole(String token) { //검증 진행하는 메서드 (롤)
        System.out.println("JWTUtil getRole");
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class); //Jwts.parser 를 사용해 토큰을 전달받아 내부 데이터 확인
    }

    public Boolean isExpired(String token) { //토큰이 만료되었는지 확인 하는

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date()); //Jwts.parser 를 사용해 토큰을 전달받아 내부 데이터 확인해서 꺼내준다
    }

    public String createJwt(String username, String role, Long expiredMs) { //로그인 성공했을때 successful handler를 통해서 유저이름, 롤, 만료 기간을 전달받아 토큰생성해서 응답해주는 토큰 생성 메서드
        System.out.println("JWTUtil createJwt");
        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("role", role);

        return Jwts.builder() //빌더를 통해서 토큰만들기
                .setClaims(claims) //클레임으로
                .setIssuedAt(new Date(System.currentTimeMillis())) //발생시간 넣어주기
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs)) //토큰이 언제 소멸될지
                .signWith(key, SignatureAlgorithm.HS256) //토큰을 정보를 갖고 암호화 진행 키를 가지고 HS256알고리즘을 통해 사인하기
                .compact(); //컴팩트를 통해 리턴하기
    }
}
