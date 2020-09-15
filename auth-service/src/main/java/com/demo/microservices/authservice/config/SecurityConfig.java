package com.demo.microservices.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.demo.microservices.authservice.security.CustomUserDetailsService;
import com.demo.microservices.authservice.security.JwtAuthenticationEntryPoint;
import com.demo.microservices.authservice.security.JwtAuthenticationFilter;
import com.demo.microservices.authservice.security.oauth2.CustomOAuth2UserService;
import com.demo.microservices.authservice.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.demo.microservices.authservice.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.demo.microservices.authservice.security.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @NonNull
  private JwtAuthenticationEntryPoint authEntryPoint;

  @NonNull
  private CustomUserDetailsService customUserDetailsService;

  @NonNull
  private CustomOAuth2UserService customOAuth2UserService;

  @NonNull
  private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

  @NonNull
  private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

  @NonNull
  private HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository;

//  @Bean
//  public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
//    return new HttpCookieOAuth2AuthorizationRequestRepository();
//  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint(authEntryPoint)
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests().antMatchers(HttpMethod.POST, "/login/**").permitAll()
        .antMatchers(HttpMethod.GET, "/public-key/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(new JwtAuthenticationFilter("/login", authenticationManager()),
            UsernamePasswordAuthenticationFilter.class)
        .httpBasic().and().csrf().disable()
        .oauth2Login()
        .authorizationEndpoint()
        .baseUri("/oauth2/authorize")
        .authorizationRequestRepository(cookieAuthorizationRequestRepository)
        .and()
        .redirectionEndpoint().baseUri("/login/oauth2/code/*")
        .and().userInfoEndpoint().userService(customOAuth2UserService)
        .and()
        .successHandler(oAuth2AuthenticationSuccessHandler)
        .failureHandler(oAuth2AuthenticationFailureHandler);
  }
}
