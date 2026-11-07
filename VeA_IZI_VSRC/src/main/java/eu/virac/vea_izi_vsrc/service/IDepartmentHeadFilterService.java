package eu.virac.vea_izi_vsrc.service;

import eu.virac.vea_izi_vsrc.model.DepartmentHead;
import eu.virac.vea_izi_vsrc.model.Department;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IDepartmentHeadFilterService {
    public abstract ArrayList<DepartmentHead> filterByStartingDateAfter(LocalDate date) throws Exception;

    public abstract ArrayList<DepartmentHead> filterByDepartment(Department department) throws Exception;
    
    ArrayList<DepartmentHead> filterActiveDepartmentHeads() throws Exception;
    
    // ACTIVE department heads who started after the given date
    ArrayList<DepartmentHead> filterActiveByStartingDateAfter(LocalDate date) throws Exception;
    
}
