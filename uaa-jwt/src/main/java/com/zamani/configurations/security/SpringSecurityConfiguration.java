package com.zamani.configurations.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private CustomUserDetailsService userDetailsService;
    private CustomJwtAuthenticationFilter customJwtAuthenticationFilter;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SpringSecurityConfiguration(CustomUserDetailsService userDetailsService, CustomJwtAuthenticationFilter customJwtAuthenticationFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.customJwtAuthenticationFilter = customJwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/authenticate").permitAll()
                //.antMatchers("/api/test/**").hasAuthority("admin")
                .anyRequest().fullyAuthenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
