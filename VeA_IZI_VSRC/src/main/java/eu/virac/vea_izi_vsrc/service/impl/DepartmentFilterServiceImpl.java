package eu.virac.vea_izi_vsrc.service.impl;

import eu.virac.vea_izi_vsrc.model.Department;
import eu.virac.vea_izi_vsrc.repo.IDepartmentRepo;
import eu.virac.vea_izi_vsrc.service.IDepartmentFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DepartmentFilterServiceImpl implements IDepartmentFilterService {

    @Autowired
    private IDepartmentRepo department_repo;

//    Finds all the departments that the passed keyword in their name or description.
    @Override
    public ArrayList<Department> filterByKeyword(String keyword) throws Exception {
        if(keyword.isEmpty()){
            throw new Exception("Passed keyword is empty...");
        }

        if(department_repo.count() == 0){
            throw new Exception("No departments were found to filter...");
        }

        ArrayList<Department> filtered_list = department_repo.findByNameOrDescriptionContaining(keyword, keyword);

        if(filtered_list.isEmpty()){
            throw new Exception("No departments were found that contain the passed keyword...");
        }
        else{
            return filtered_list;
        }
    }
}
