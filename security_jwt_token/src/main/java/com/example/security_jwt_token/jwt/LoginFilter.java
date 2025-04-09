package com.example.security_jwt_token.jwt;

import com.example.security_jwt_token.dto.AuthDto;
import com.example.security_jwt_token.entity.RefreshEntity;
import com.example.security_jwt_token.repository.RefreshRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author jiyoung
 */
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
            AuthenticationException {

        if (!request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            throw new AuthenticationServiceException("지원하지 않는 Content-Type: " + request.getContentType());
        }

        try {
            // JSON 데이터 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            AuthDto loginRequest = objectMapper.readValue(request.getInputStream(), AuthDto.class);

            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            System.out.println("[LoginFilter] username = " + username);

            // 스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password,
                    null);

            //token에 담은 검증을 위한 AuthenticationManager로 전달
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new AuthenticationServiceException("요청 본문 파싱 실패", e);
        }
    }

    // 로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) {

        // 유저 정보
        String username = authentication.getName();

        // role 값 알아내기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        List<RefreshEntity> refreshList = refreshRepository.findByUsername(username);

        if (!refreshList.isEmpty()) {
            refreshRepository.deleteByUsername(username);
        }

        //토큰 생성
        String access = jwtUtil.createJwt("access", username, role, 600000L);
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        //RefreshToken 저장
        addRefreshEntity(username, refresh, 86400000L);

        //응답 설정
        // 1. Access Token → Authorization 헤더에 표준 Bearer 형식으로 설정
        response.setHeader("Authorization", "Bearer " + access);
        // 2. Refresh Token → HttpOnly Cookie로 설정
        response.addCookie(createCookie("refresh", refresh));
        // 3. 상태 코드 설정 (OK)
        response.setStatus(HttpStatus.OK.value());
    }

    // 로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {
        // 실패시 401 응답 코드 반환
        response.setStatus(401);
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {
        // 현재 시간
        LocalDateTime now = LocalDateTime.now();
        // 만료 시간 계산
        LocalDateTime expiration = now.plusNanos(expiredMs * 1_000_000);

        expiration = expiration.withNano(0);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(expiration);

        refreshRepository.save(refreshEntity);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        // 쿠키 생명 주기
        cookie.setMaxAge(24 * 60 * 60);
        //cookie.setSecure(true); -> https 통신일 경우 사용
        //cookie.setPath("/"); -> 쿠키가 적용될 범위
        // 자바스크립트 접근 방어
        cookie.setHttpOnly(true);

        return cookie;
    }
}
