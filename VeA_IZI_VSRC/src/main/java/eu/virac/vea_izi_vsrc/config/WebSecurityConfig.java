package eu.virac.vea_izi_vsrc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	public UserDetailsManager createDummyUsers() {
		PasswordEncoder encoder = 
				PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		UserDetails userD1 = 
				User.builder()
				.username("normunds")
				.password(encoder.encode("normunds"))
				.authorities("USER")
				.build();
		UserDetails userD2 = 
				User.builder()
				.username("gustavs")
				.password(encoder.encode("gustavs"))
				.authorities("ADMIN")
				.build();
		UserDetails userD3 = 
				User.builder()
				.username("anna")
				.password(encoder.encode("anna"))
				.authorities("DEPARTMENTHEAD")
				.build();
		
		InMemoryUserDetailsManager manager = 
				new InMemoryUserDetailsManager(userD1, userD2, userD3);
		return manager;
	}
	
	@Bean
	public SecurityFilterChain httpPermissions(HttpSecurity http) {
		http.authorizeHttpRequests(
				auth->auth
				.requestMatchers("/User/**").permitAll()
				.requestMatchers("/DepartmentHead/**").hasAnyAuthority("ADMIN", "DEPARTMENTHEAD")
				.requestMatchers("/admin/**").hasAuthority("ADMIN"));
		
		http.formLogin(auth->auth.permitAll());
		
		return http.build();
	}
	
}
