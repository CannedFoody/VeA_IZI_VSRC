package eu.virac.vea_izi_vsrc.service.impl;

import java.util.ArrayList;

import eu.virac.vea_izi_vsrc.model.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.virac.vea_izi_vsrc.model.KPI;
import eu.virac.vea_izi_vsrc.model.Task;
import eu.virac.vea_izi_vsrc.model.Enums.TaskStatus;
import eu.virac.vea_izi_vsrc.repo.IKPIRepo;
import eu.virac.vea_izi_vsrc.repo.ITaskRepo;
import eu.virac.vea_izi_vsrc.service.ITaskFilterService;

@Service
public class TaskFilterServiceImpl implements ITaskFilterService {

	@Autowired
	private IKPIRepo kpiRepo;

	@Autowired
	private ITaskRepo taskRepo;

	@Override
	public ArrayList<Task> filterTasks(TaskStatus status, Long idKPI) throws Exception {
		if (status == null && idKPI == null) {
			throw new Exception("No status or KPI selected...");
		}
		if (taskRepo.count() == 0) {
			throw new Exception("No Tasks in DB...");
		}
		if (idKPI == null) {
			return taskRepo.findByStatus(status);
		}
		if (idKPI < 1 || !kpiRepo.existsById(idKPI)) {
			throw new Exception("Passed KPI does not exist...");
		}

		KPI kpi = kpiRepo.findById(idKPI).get();

		if (status == null) {
			return taskRepo.findByKpi(kpi);
		}

		return taskRepo.findByStatusAndKpi(status, kpi);
	}

}
