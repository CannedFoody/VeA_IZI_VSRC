package eu.virac.vea_izi_vsrc.repo.security;

import org.springframework.data.repository.CrudRepository;

import eu.virac.vea_izi_vsrc.model.security.SecAuthority;

public interface ISecAuthorityRepo extends CrudRepository<SecAuthority, Long> {
	
}
