package eu.virac.vea_izi_vsrc.repo;

import eu.virac.vea_izi_vsrc.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface ITaskRepo extends CrudRepository<Task,Long> {
}
