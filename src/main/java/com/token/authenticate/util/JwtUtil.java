package com.token.authenticate.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {


    //Token에서 UserName 꺼내기
    public static String getUserName(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token)
                .getBody().get("userName", String.class);
    }
    public static boolean isExpired(String token, String secretKey) {
        // SecretKey와 Token을 가지고 토큰이 만료 되었는지 확인한다.
        // before은 유효기간 안에 있는지 확인하는 것.
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public static String CreateToken(String userName, String key, long expireTimeMs) {
        Claims claims = Jwts.claims(); //일종의 map
        claims.put("userName", userName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                //getBytes를 넣지 않으면 Jwts 오류가 난다.
                .signWith(SignatureAlgorithm.HS256,key.getBytes())
                .compact()
                ;
    }
}