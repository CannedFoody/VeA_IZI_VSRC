package eu.virac.vea_izi_vsrc.service;

import java.util.ArrayList;

import eu.virac.vea_izi_vsrc.model.Project;

public interface IFilterProjectService {
	public abstract ArrayList<Project> filterByKeyword(String keyword) throws Exception;
}
