package com.demo.microservices.servicelibs.security.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class AppUserPrincipal implements UserDetails {
	
	@NonNull
	private Long id;
	@NonNull
	private String username;
	@NonNull
	private String name;
	private String email;
    
	@NonNull
    private Collection<? extends GrantedAuthority> authorities;
    
    public static AppUserPrincipal create(AppUser user) {
    	List<GrantedAuthority> authorities = user.getRoles().stream().map(
    			role -> new SimpleGrantedAuthority(role.name())
		).collect(Collectors.toList());
    	
    	return new AppUserPrincipal(
	    			user.getId(),
	    			user.getUsername(),
	    			user.getName(),
	    			authorities
    			);
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	@Override
	public String getPassword() {
		return null;
	}
}
