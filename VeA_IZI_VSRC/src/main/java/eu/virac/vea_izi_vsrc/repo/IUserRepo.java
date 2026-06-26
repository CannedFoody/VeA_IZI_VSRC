package eu.virac.vea_izi_vsrc.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import eu.virac.vea_izi_vsrc.model.User;

public interface IUserRepo extends CrudRepository<User, Long> {

	ArrayList<User> findByRole(String role);

	ArrayList<User> findByDepartmentIdDepartment(long idDepartment);

}
