package eu.virac.vea_izi_vsrc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import eu.virac.vea_izi_vsrc.model.security.SecUser;
import eu.virac.vea_izi_vsrc.repo.security.ISecUserRepo;

@Service
public class SecUserDetailsManager implements UserDetailsManager {
	
	@Autowired
	private ISecUserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (userRepo.existsByUsername(username)) {
			SecUser user = userRepo.findByUsername(username);
			return new SecUserDetails(user);
		}
		else {
			throw new UsernameNotFoundException(username + " not found");
		}
	}

	@Override
	public void createUser(UserDetails user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(UserDetails user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userExists(String username) {
		return userRepo.existsByUsername(username);
	}
}
