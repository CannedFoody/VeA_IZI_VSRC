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
		if (departmentHead.getStartingDate() == null
				|| departmentHead.getDepartment() == null || departmentHead.getUser() == null) {
			throw new Exception("One or more of the DepartmentHead fields are empty...");
		}

		if (departmentHead.getEndingDate() == null) {
			DepartmentHead old_head = departmentHeadRepo
					.findFirstByDepartmentAndEndingDateIsNull(departmentHead.getDepartment());

			if (old_head != null) {
				if (old_head.getUser().getIdUser() == departmentHead.getUser().getIdUser()) {
					throw new Exception("This user is already the head of the department...");
				}
				if (departmentHead.getStartingDate().isBefore(old_head.getStartingDate())) {
					throw new Exception("New head cannot start before the current head...");
				}

				old_head.setEndingDate(departmentHead.getStartingDate());
				departmentHeadRepo.save(old_head);
			}
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
	public DepartmentHead updateDepartmentHead(long departmentHeadId, LocalDate startingDate, LocalDate endingDate,
			Department department, User user) throws Exception {
		if (departmentHeadId < 1) {
			throw new Exception("Invalid DepartmentHead ID passed...");
		}
		if (!departmentHeadRepo.existsById(departmentHeadId)) {
			throw new Exception("No DepartmentHead exists by that ID...");
		}
		if (startingDate == null || department == null || user == null) {
			throw new Exception("Incorrect DepartmentHead input data...");
		}
		
		DepartmentHead department_head_to_update = departmentHeadRepo.findById(departmentHeadId).get();
		
		if (department_head_to_update.getUser().getIdUser() != user.getIdUser()) {
			if (department_head_to_update.getEndingDate() != null) {
				throw new Exception("This head's term has already ended, create a new record instead...");
			}

			department_head_to_update.setEndingDate(LocalDate.now());
			departmentHeadRepo.save(department_head_to_update);

			return departmentHeadRepo.save(new DepartmentHead(department_head_to_update.getDepartment(), user, LocalDate.now()));
		}
		
		if (!department_head_to_update.getStartingDate().equals(startingDate)) {
			department_head_to_update.setStartingDate(startingDate);
		}
		if (endingDate != null){
			department_head_to_update.setEndingDate(endingDate);
		}
		if (!department_head_to_update.getDepartment().equals(department)) {
			department_head_to_update.setDepartment(department);
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
            throw new Exception("No DepartmentHead exists by that ID...");
        }
	}

}
