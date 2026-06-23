package eu.virac.vea_izi_vsrc.service;

import java.time.LocalDate;
import java.util.ArrayList;

import eu.virac.vea_izi_vsrc.model.Department;
import eu.virac.vea_izi_vsrc.model.DepartmentHead;
import eu.virac.vea_izi_vsrc.model.User;

public interface ICRUDDepartmentHeadService {

	public abstract DepartmentHead createNewDepartmentHead(DepartmentHead departmentHead) throws Exception;
	
	public abstract ArrayList<DepartmentHead> getAllDepartmentHeads() throws Exception;
	
	public abstract DepartmentHead getDepartmentHeadById(long departmentHeadId) throws Exception;
	
	public abstract DepartmentHead updateDepartmentHead(long departmentHeadId, LocalDate starting_date, LocalDate ending_date, 
			Department department, User user) throws Exception;
	
	public abstract void deleteDepartmentHead(long departmentHeadId) throws Exception;
}
