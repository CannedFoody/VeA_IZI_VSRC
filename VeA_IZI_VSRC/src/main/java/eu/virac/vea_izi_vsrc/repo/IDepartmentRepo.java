package eu.virac.vea_izi_vsrc.repo;

import org.springframework.data.repository.CrudRepository;

import eu.virac.vea_izi_vsrc.model.Department;

public interface IDepartmentRepo extends CrudRepository<Department, Long> {

}
