package com.token.authenticate.configuration;

import com.token.authenticate.service.UserService;
import com.token.authenticate.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //권한 주거나 안주기
        //개찰구 역할
        //현재는 모두 닫혀있다.
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization :{}",authorization);

        //토큰 안보내면 막는다.
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("authorization을 잘못보냈습니다.");
            filterChain.doFilter(request,response); //필터체인은 넘기고 문을 열지 않고 종료한다.
            return;
        }

        //토큰 꺼내기 (authorization에서 꺼낸다.)
        String token = authorization.split(" ")[1]; //[0]은 Bearer이다
        log.info("token :{}",token);

        //만료되었는지 확인
        if (JwtUtil.isExpired(token, secretKey)) {
            log.error("토큰이 만료되었습니다.");
            filterChain.doFilter(request,response); //필터체인은 넘기고 문을 열지 않고 종료한다.
            return;
        }


        //토큰에서 userName 꺼내기
        String userName = JwtUtil.getUserName(token,secretKey);
        log.info("userName:{}", userName);

        //문 열어주기
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                (userName, null, List.of(new SimpleGrantedAuthority("USER")));
        //Detail을 넣는다.
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
