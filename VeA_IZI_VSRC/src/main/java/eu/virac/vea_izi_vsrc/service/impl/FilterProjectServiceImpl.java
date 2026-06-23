package eu.virac.vea_izi_vsrc.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.virac.vea_izi_vsrc.model.Project;
import eu.virac.vea_izi_vsrc.repo.IProjectRepo;
import eu.virac.vea_izi_vsrc.service.IFilterProjectService;

@Service
public class FilterProjectServiceImpl implements IFilterProjectService {
	
	@Autowired
	private IProjectRepo project_repo;

	@Override
	public ArrayList<Project> filterByKeyword(String keyword) throws Exception {
		if (keyword == null) {
			throw new Exception("Invalid keyword");
		}
		if (project_repo.count() == 0) {
			throw new Exception("No project objects exist to filter...");
		}
		
		ArrayList<Project> filtered_list = project_repo.findByTitleOrDescriptionContaining(keyword, keyword);
		
		if (filtered_list.isEmpty()) {
			throw new Exception("No projects were found with that keyword...");
		}
		
		return filtered_list;
	}

}
