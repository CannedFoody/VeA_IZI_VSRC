package eu.virac.vea_izi_vsrc.service;

import java.util.ArrayList;

import eu.virac.vea_izi_vsrc.model.User;

public interface IFilterUserService {

	public abstract ArrayList<User> filterUsersByRole(String role) throws Exception;
	
	public abstract ArrayList<User> filterUsersByDepartmentId(long idDepartment) throws Exception;
}
