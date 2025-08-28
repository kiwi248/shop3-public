package com.shop3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 인가 규칙
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/css/**", "/js/**", "/img/**", "/images/**", // 정적 리소스 허용
                                "/", "/members/**", "/item/**", "/favicon.ico", "/error"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")   // DB에는 ROLE_ADMIN 저장
                        .anyRequest().authenticated()
                )

                // 폼 로그인
                .formLogin(login -> login
                        .loginPage("/members/login")          // GET 로그인 페이지
                        .loginProcessingUrl("/members/login") // ★ 폼 POST 경로와 일치해야 함
                        .defaultSuccessUrl("/", true)
                        .usernameParameter("email")
                        .failureUrl("/members/login/error")   // 커스텀 핸들러 없이도 동작
                        .permitAll()
                )

                // 로그아웃 (기본 CSRF on → POST 로그아웃 권장)
                .logout(logout -> logout
                        .logoutUrl("/members/logout")
                        .logoutSuccessUrl("/")
                );

        // CSRF는 기본 활성화(권장). H2 콘솔 등을 쓰면 예외만 별도 추가.

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

