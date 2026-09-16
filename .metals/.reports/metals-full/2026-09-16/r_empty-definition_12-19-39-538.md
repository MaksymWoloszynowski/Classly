error id: file://<WORKSPACE>/backend/api-gateway/src/main/java/org/edziennik/apigateway/config/SecurityConfig.java:_empty_/`<any>`#permitAll#
file://<WORKSPACE>/backend/api-gateway/src/main/java/org/edziennik/apigateway/config/SecurityConfig.java
empty definition using pc, found symbol in pc: _empty_/`<any>`#permitAll#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1220
uri: file://<WORKSPACE>/backend/api-gateway/src/main/java/org/edziennik/apigateway/config/SecurityConfig.java
text:
```scala
package org.edziennik.apigateway.config;

import org.edziennik.apigateway.filter.JwtGatewayFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtGatewayFilter jwtFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").@@permitAll()
                        .requestMatchers(PublicPaths.PATHS).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/`<any>`#permitAll#