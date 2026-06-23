package eu.virac.vea_izi_vsrc.repo;

import eu.virac.vea_izi_vsrc.model.Project;
import org.springframework.data.repository.CrudRepository;

public interface IProjectRepo extends CrudRepository<Project, Long> {
}
