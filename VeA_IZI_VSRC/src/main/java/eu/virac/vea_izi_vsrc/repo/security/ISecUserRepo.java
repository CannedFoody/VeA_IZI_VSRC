package eu.virac.vea_izi_vsrc.repo.security;

import org.springframework.data.repository.CrudRepository;

import eu.virac.vea_izi_vsrc.model.security.SecUser;

public interface ISecUserRepo extends CrudRepository<SecUser, Long> {

	boolean existsByUsername(String username);

	SecUser findByUsername(String username);

}
