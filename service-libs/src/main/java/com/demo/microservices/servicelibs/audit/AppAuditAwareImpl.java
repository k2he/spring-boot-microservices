package com.demo.microservices.servicelibs.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.demo.microservices.servicelibs.security.user.AppUserPrincipal;

public class AppAuditAwareImpl implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return Optional.empty();
		}

		AppUserPrincipal userPrincipal = (AppUserPrincipal) authentication.getPrincipal();
		return Optional.ofNullable(userPrincipal.getId());
	}

}
