package com.example.runawaytravel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsMvcConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization");
        config.addAllowedOrigin("http://localhost:5174"); // Vue.js dev server
        //config.addAllowedOrigin("https://example.com"); // 배포 환경

        source.registerCorsConfiguration("/api**", config); //Cors 정책을 사용할 곳만 여기에 정의
        return new CorsFilter(source);
    }

}