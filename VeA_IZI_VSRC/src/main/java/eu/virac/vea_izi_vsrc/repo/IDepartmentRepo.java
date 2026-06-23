package eu.virac.vea_izi_vsrc.repo;

import org.springframework.data.repository.CrudRepository;

import eu.virac.vea_izi_vsrc.model.Department;

import java.util.ArrayList;

public interface IDepartmentRepo extends CrudRepository<Department, Long> {

    ArrayList<Department> findByNameOrDescriptionContaining(String keyword, String keyword1);
}
