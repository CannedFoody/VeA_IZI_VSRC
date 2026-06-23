package eu.virac.vea_izi_vsrc.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.virac.vea_izi_vsrc.model.Department;
import eu.virac.vea_izi_vsrc.model.DepartmentHead;
import eu.virac.vea_izi_vsrc.model.User;
import eu.virac.vea_izi_vsrc.repo.IDepartmentHeadRepo;
import eu.virac.vea_izi_vsrc.service.ICRUDDepartmentHeadService;

@Service
public class CRUDDepartmentHeadServiceImpl implements ICRUDDepartmentHeadService {

	@Autowired
	private IDepartmentHeadRepo departmentHeadRepo;
	
	@Override
	public DepartmentHead createNewDepartmentHead(DepartmentHead departmentHead) throws Exception {
		if (departmentHead == null) {
			throw new Exception("Passed DepartmentHead object is null...");
		}
		if (departmentHeadRepo.existsById(departmentHead.getDepartmentHeadId())) {
			throw new Exception("Category with that ID already exists...");
		}
		if (departmentHead.getStarting_date() == null || departmentHead.getEnding_date() == null 
				|| departmentHead.getDepartment() == null || departmentHead.getUser() == null) {
			throw new Exception("One or more of the DepartmentHead fields are empty...");
		}
		
		return departmentHeadRepo.save(departmentHead);
	}

	@Override
	public ArrayList<DepartmentHead> getAllDepartmentHeads() throws Exception {
		if (departmentHeadRepo.count() == 0) {
			throw new Exception("DepartmentHead repo is empty...");
		}
		
		ArrayList<DepartmentHead> returned_list = (ArrayList<DepartmentHead>) departmentHeadRepo.findAll();
		
		return returned_list;
	}

	@Override
	public DepartmentHead getDepartmentHeadById(long departmentHeadId) throws Exception {
		if (departmentHeadId < 1) {
			throw new Exception("Invalid DepartmentHead ID passed...");
		}
		if (departmentHeadRepo.count() == 0) {
			throw new Exception("DepartmentHead repo is empty...");
		}
		if (!departmentHeadRepo.existsById(departmentHeadId)) {
			throw new Exception("No DepartmentHead exists by that ID...");
		}
		
		return departmentHeadRepo.findById(departmentHeadId).get();
	}

	@Override
	public DepartmentHead updateDepartmentHead(long departmentHeadId, LocalDate starting_date, LocalDate ending_date,
			Department department, User user) throws Exception {
		if (departmentHeadId < 1) {
			throw new Exception("Invalid DepartmentHead ID passed...");
		}
		if (!departmentHeadRepo.existsById(departmentHeadId)) {
			throw new Exception("No DepartmentHead exists by that ID...");
		}
		if (starting_date == null || ending_date == null || department == null || user == null) {
			throw new Exception("Incorrect DepartmentHead input data...");
		}
		
		DepartmentHead department_head_to_update = departmentHeadRepo.findById(departmentHeadId).get();
		
		if (!department_head_to_update.getStarting_date().equals(starting_date)) {
			department_head_to_update.setStarting_date(starting_date);
		}
		if (!department_head_to_update.getEnding_date().equals(ending_date)) {
			department_head_to_update.setEnding_date(ending_date);
		}
		if (!department_head_to_update.getDepartment().equals(department)) {
			department_head_to_update.setDepartment(department);
		}
		if (!department_head_to_update.getUser().equals(user)) {
			department_head_to_update.setUser(user);
		}
		
		return departmentHeadRepo.save(department_head_to_update);
	}

	@Override
	public void deleteDepartmentHead(long departmentHeadId) throws Exception {
		if (departmentHeadId < 1) {
			throw new Exception("Invalid DepartmentHead ID passed...");
		}
		
		if (departmentHeadRepo.existsById(departmentHeadId)) {
			departmentHeadRepo.deleteById(departmentHeadId);
		}
		else{
            throw new Exception("No KPI exists by that ID...");
        }
	}

}
