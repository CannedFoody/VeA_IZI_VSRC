package eu.virac.vea_izi_vsrc.config;

import java.util.ArrayList;
import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import eu.virac.vea_izi_vsrc.model.security.SecAuthority;
import eu.virac.vea_izi_vsrc.model.security.SecUser;

public class SecUserDetails implements UserDetails {
	
	private SecUser user;
	
	public SecUserDetails(SecUser user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<SimpleGrantedAuthority> authForSecurity = new ArrayList<>();
		
		for (SecAuthority tempA : user.getAuthorities()) {
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(tempA.getTitle());
			authForSecurity.add(sga);
		}
		
		return authForSecurity;
	}

	@Override
	public @Nullable String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

}
