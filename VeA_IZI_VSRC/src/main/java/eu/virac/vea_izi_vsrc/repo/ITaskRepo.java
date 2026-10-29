package eu.virac.vea_izi_vsrc.repo;

import eu.virac.vea_izi_vsrc.model.KPI;
import eu.virac.vea_izi_vsrc.model.Task;
import eu.virac.vea_izi_vsrc.model.Enums.TaskStatus;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

public interface ITaskRepo extends CrudRepository<Task,Long> {
	
	ArrayList<Task> findByStatus(TaskStatus status);
	
	ArrayList<Task> findByKpi(KPI kpi);
	
	ArrayList<Task> findByStatusAndKpi(TaskStatus status, KPI kpi);
}
