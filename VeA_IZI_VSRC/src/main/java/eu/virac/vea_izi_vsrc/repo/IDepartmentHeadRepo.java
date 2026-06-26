package eu.virac.vea_izi_vsrc.repo;

import eu.virac.vea_izi_vsrc.model.Department;
import eu.virac.vea_izi_vsrc.model.DepartmentHead;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IDepartmentHeadRepo extends CrudRepository<DepartmentHead, Long> {
    ArrayList<DepartmentHead> findByStartingDateAfter(LocalDate date);

    ArrayList<DepartmentHead> findByDepartment(Department department);
}
