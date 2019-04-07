package com.demo.microservices.gatewayservice.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.microservices.servicelibs.security.JwtConstants;
import com.demo.microservices.servicelibs.security.JwtTokenValidator;
import com.demo.microservices.servicelibs.security.user.AppUserPrincipal;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private JwtTokenValidator jwtTokenProvider;

	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			// Initialize jwtTokenProvider and userDetailService
			if (jwtTokenProvider == null) {
				ServletContext servletContext = request.getServletContext();
				WebApplicationContext webApplicationContext = WebApplicationContextUtils
						.getWebApplicationContext(servletContext);
				jwtTokenProvider = webApplicationContext.getBean(JwtTokenValidator.class);
			}

			String token = getJwtFromRequest((HttpServletRequest) request);
			if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
				AppUserPrincipal userDetails = jwtTokenProvider.getUserDetailFromJWT(token);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception ex) {
			logger.error("Could not set user authentication in security context", ex);
		}

		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(JwtConstants.HEADER_STRING);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtConstants.TOKEN_PREFIX)) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
