package com.einuvy.apigateway.configuration;

import com.einuvy.apigateway.configuration.jwt.JwtAuthorizationFilter;
import com.einuvy.apigateway.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.einuvy.apigateway.enums.Role.ADMIN;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CutomUserDetailsService cutomUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);

        auth.userDetailsService(cutomUserDetailsService).passwordEncoder(passwordEncoder);

        AuthenticationManager authenticationManager = auth.build();

/*        http.cors()
                .and()
                .csrf().disable();*/
        http.authenticationManager(authenticationManager);
        http.sessionManagement().sessionCreationPolicy(STATELESS);



        http.csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/sign-in", "/api/auth/sign-up").permitAll()
                .antMatchers(GET, "/gateway/inmueble").permitAll()
                .antMatchers("/gateway/inmueble/**").hasRole(ADMIN.name())
                .anyRequest().authenticated()
                .and().authenticationManager(authenticationManager)
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and().addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
               registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}
