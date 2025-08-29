package com.barbosoft.biblioteca.bibliotecaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();

        // ⚠️ En Spring s’ha d’incloure l’ESQUEMA i el PORT
        cfg.setAllowedOrigins(List.of(
                "http://10.0.2.2:8080",      // emulador → host
                "http://192.168.1.145:8080", // mòbil real → IP del teu PC (canvia-la si cal)
                "http://localhost:8080"      // opcional
        ));
        // Si prefereixes patrons (per IP variable/ports), usa:
        // cfg.setAllowedOriginPatterns(List.of("http://10.0.2.2:*", "http://192.168.1.*:*"));

        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}