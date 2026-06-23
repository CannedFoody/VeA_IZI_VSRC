package eu.virac.vea_izi_vsrc.service.impl;

import eu.virac.vea_izi_vsrc.model.Department;
import eu.virac.vea_izi_vsrc.model.DepartmentHead;
import eu.virac.vea_izi_vsrc.repo.IDepartmentHeadRepo;
import eu.virac.vea_izi_vsrc.repo.IDepartmentRepo;
import eu.virac.vea_izi_vsrc.service.IDepartmentHeadFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class DepartmentHeadFilterServiceImpl implements IDepartmentHeadFilterService {

    @Autowired
    private IDepartmentHeadRepo d_head_repo;

    @Autowired
    private IDepartmentRepo department_repo;


//    Finds all the Department Heads that started work after the passed date.
    @Override
    public ArrayList<DepartmentHead> filterByStartingDateAfter(LocalDate date) throws Exception {
        if(date == null){
            throw new Exception("Passed date variable is null...");
        }

        if(date.isAfter(LocalDate.now()) || date.isBefore(LocalDate.of(1900, 1, 1))){
            throw new Exception("Passed date is not valid...");
        }

        if(d_head_repo.count() == 0){
            throw new Exception("Department head repo is empty...");
        }

        ArrayList<DepartmentHead> filtered_list = d_head_repo.findByStartingDateAfter(date);

        if(filtered_list.isEmpty()){
            throw new Exception("No Department heads that joined after the passed date were found...");
        }
        else{
            return filtered_list;
        }
    }

//    Finds all the Department Heads belonging to the passed department
    @Override
    public ArrayList<DepartmentHead> filterByDepartment(Department department) throws Exception {
        if(department == null){
            throw new Exception("Passed department object is null...");
        }

        if(!department_repo.existsById(department.getIdDepartment())){
            throw new Exception("Could not find the passed department in the system...");
        }

        if(d_head_repo.count() == 0){
            throw new Exception("No Department Heads to filter...");
        }

        ArrayList<DepartmentHead> filtered_list = d_head_repo.findByDepartment(department);

        if(filtered_list.isEmpty()){
            throw new Exception("No Department heads were found in that department...");
        }
        else{
            return filtered_list;
        }
    }


}
