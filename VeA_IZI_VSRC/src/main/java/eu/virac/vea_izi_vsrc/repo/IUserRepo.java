package eu.virac.vea_izi_vsrc.repo;

import org.springframework.data.repository.CrudRepository;

import eu.virac.vea_izi_vsrc.model.User;

public interface IUserRepo extends CrudRepository<User, Long> {

}
