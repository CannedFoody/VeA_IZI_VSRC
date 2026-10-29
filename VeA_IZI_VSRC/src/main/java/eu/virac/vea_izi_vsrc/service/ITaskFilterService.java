package eu.virac.vea_izi_vsrc.service;

import java.util.ArrayList;

import eu.virac.vea_izi_vsrc.model.SubCategory;
import eu.virac.vea_izi_vsrc.model.Task;
import eu.virac.vea_izi_vsrc.model.Enums.TaskStatus;

public interface ITaskFilterService {
	public abstract ArrayList<Task> filterTasks(TaskStatus status, Long idKPI) throws Exception;
}
