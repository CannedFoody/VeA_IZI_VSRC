package eu.virac.vea_izi_vsrc.service;

import eu.virac.vea_izi_vsrc.model.Department;

import java.util.ArrayList;

public interface IDepartmentFilterService {
    public abstract ArrayList<Department> filterByKeyword(String keyword) throws Exception;
}
