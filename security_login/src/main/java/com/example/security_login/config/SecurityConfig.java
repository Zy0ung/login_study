package com.example.security_login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author jiyoung
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/my/**").hasAnyRole("USER")
                .anyRequest().authenticated()
        );

        // formLoign 로그인 방식
        http.formLogin((auth) -> auth.loginPage("/login")
                                     .loginProcessingUrl("/loginProc")
                                     .permitAll()
        );

//        // 페이지가 필요하지않고 브라우저에서 바로 헤더에 인증 함
//        // 아이디와 비밀번호를 Base64 방식으로 인코딩한 뒤 HTTP 인증 헤더에 부착하여 서버측으로 요청 보냄
//        http
//                .httpBasic(Customizer.withDefaults());

        // 하나의 ID당 동시 접속할 수 있는 로그인 허용 개수(1)
        // 다중 로그인 개수 초과하였을 경우 처리
        // true : 초과시 새로운 로그인 차단
        // false : 초과시 기존 세션 하나 삭제
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));

        // 세션 고정 보호
        // sessionManagement().sessionFixation().none() : 로그인 시 세션 정보 변경 안함
        // sessionManagement().sessionFixation().newSession() : 로그인 시 세션 새로 생성
        // sessionManagement().sessionFixation().changeSessionId() : 로그인 시 동일한 세션에 대한 id 변경
        http.sessionManagement((auth) -> auth.sessionFixation().changeSessionId());

        // 로그아웃 추가
        http
                .logout((auth) -> auth.logoutUrl("/logout")
                                      .logoutSuccessUrl("/"));


        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 권한 등급 설정
    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("""
                ROLE_ADMIN > ROLE_USER
                """);
    }
}