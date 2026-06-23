package eu.virac.vea_izi_vsrc.service;

import eu.virac.vea_izi_vsrc.model.Department;

import java.util.ArrayList;

public interface ICRUDDepartmentService {
    public abstract Department createNewDepartment(Department department) throws Exception;

    public abstract ArrayList<Department> getAllDepartments()throws Exception;

    public abstract Department getDepartmentById(long idDepartment)throws Exception;

    public abstract Department updateDepartment(long idDepartment, String name, String description, String goals)throws Exception;

    public abstract void deleteDepartment(long idDepartment)throws Exception;
}
