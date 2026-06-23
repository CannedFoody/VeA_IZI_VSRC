package eu.virac.vea_izi_vsrc.service.impl;

import eu.virac.vea_izi_vsrc.model.Department;
import eu.virac.vea_izi_vsrc.repo.IDepartmentRepo;
import eu.virac.vea_izi_vsrc.service.ICRUDDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CRUDDepartmentServiceImpl implements ICRUDDepartmentService {

    @Autowired
    private IDepartmentRepo departmentRepo;

    @Override
    public Department createNewDepartment(Department department) throws Exception{
        if(department == null){
            throw new Exception("Passed department object is null...");
        }

        if(departmentRepo.existsById(department.getIdDepartment())){
            throw new Exception("Department with that ID already exists...");
        }

        if(department.getName().isEmpty() || department.getDescription().isEmpty() || department.getGoals().isEmpty()){
            throw new Exception("Department name or description or goals field is empty...");
        }

        return  departmentRepo.save(department);
    }

    @Override
    public ArrayList<Department> getAllDepartments() throws Exception{
        if(departmentRepo.count() == 0){
            throw new Exception("Department repo is empty...");
        }

        ArrayList<Department> returned_list = (ArrayList<Department>) departmentRepo.findAll();

        return returned_list;
    }

    @Override
    public Department getDepartmentById(long idDepartment) throws Exception{
        if (idDepartment < 1) {
            throw new Exception("Invalid department ID passed...");
        }

        if(departmentRepo.count() == 0){
            throw new Exception("Department repo is empty...");
        }

        if(!departmentRepo.existsById(idDepartment)){
            throw new Exception("No department exists by that ID...");
        }

        return departmentRepo.findById(idDepartment).get();
    }

    @Override
    public Department updateDepartment(long idDepartment, String name, String description, String goals) throws Exception{
        if(departmentRepo.count() == 0){
            throw new Exception("The department repo is empty...");
        }

        if(idDepartment < 1){
            throw new Exception("Incorrect department ID passed...");
        }

        if(name == null || description == null || goals == null){
            throw new Exception("Incorrect department input data...");
        }

        Department department_to_update = departmentRepo.findById(idDepartment).get();

        if(!department_to_update.getName().equals(name)){
            department_to_update.setName(name);
        }

        if(!department_to_update.getDescription().equals(description)){
            department_to_update.setDescription(description);
        }

        if(!department_to_update.getGoals().equals(goals)){
            department_to_update.setGoals(goals);
        }

        return departmentRepo.save(department_to_update);
    }

    @Override
    public void deleteDepartment(long idDepartment) throws Exception{
        if(departmentRepo.count() == 0){
            throw new Exception("The department repo is empty...");
        }

        if(idDepartment < 1){
            throw new Exception("Invalid department ID passed...");
        }

        if(departmentRepo.existsById(idDepartment)){
            departmentRepo.deleteById(idDepartment);
        }
        else{
            throw new Exception("No department exists by that ID...");
        }
    }
}
