package com.demo.microservices.testservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import com.demo.microservices.servicelibs.security.JwtAuthenticationEntryPoint;
import com.demo.microservices.servicelibs.security.JwtTokenValidator;
import com.demo.microservices.testservice.security.JwtAuthorizationFilter;
/**
 * @author kaihe
 *
 */
 
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  RestTemplate restTemplate;

  @Bean
  public JwtAuthenticationEntryPoint authEntryPoint() {
    return new JwtAuthenticationEntryPoint();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtTokenValidator jwtTokenProvider() {
    return new JwtTokenValidator(restTemplate);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().exceptionHandling()
        .authenticationEntryPoint(authEntryPoint()).and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
        .anyRequest().authenticated().and()
        .addFilterAfter(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

}
