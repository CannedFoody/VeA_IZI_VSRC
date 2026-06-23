package eu.virac.vea_izi_vsrc.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.virac.vea_izi_vsrc.model.User;
import eu.virac.vea_izi_vsrc.repo.IUserRepo;
import eu.virac.vea_izi_vsrc.service.IFilterUserService;

@Service
public class FilterUserServiceImpl implements IFilterUserService {

	@Autowired
	private IUserRepo userRepo;
	
	@Override
	public ArrayList<User> filterUsersByRole(String role) throws Exception {
		if (role.isEmpty()) {
			throw new Exception("No role passed...");
		}
		if (userRepo.count() == 0) {
			throw new Exception("User repo is empty...");
		}
		
		ArrayList<User> filtered_result = userRepo.findByRole(role);
		
		return filtered_result;
	}

	@Override
	public ArrayList<User> filterUsersByDepartmentId(long idDepartment) throws Exception {
		if (idDepartment < 1) {
			throw new Exception("Incorrect department ID passed...");
		}
		if (userRepo.count() == 0) {
			throw new Exception("User repo is empty...");
		}
		
		ArrayList<User> filtered_result = userRepo.findByDepartmentIdDepartment(idDepartment);
		
		return filtered_result;
	}

}
