error id: file://<WORKSPACE>/backend/common/src/main/java/org/edziennik/security/SecurityConfig.java:_empty_/`<any>`#requestMatchers#permitAll#
file://<WORKSPACE>/backend/common/src/main/java/org/edziennik/security/SecurityConfig.java
empty definition using pc, found symbol in pc: _empty_/`<any>`#requestMatchers#permitAll#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1558
uri: file://<WORKSPACE>/backend/common/src/main/java/org/edziennik/security/SecurityConfig.java
text:
```scala
package org.edziennik.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AutoConfiguration
public class SecurityConfig {

    @Bean
    public JwtVerifier jwtVerifier(@Value("${jwt.secret}") String secret) {
        return new JwtVerifier(secret);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtVerifier jwtVerifier) {
        return new JwtAuthenticationFilter(jwtVerifier);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").@@permitAll()
                    .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/`<any>`#requestMatchers#permitAll#