package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import eu.virac.vea_izi_vsrc.service.IDepartmentFilterService;

public class FilterDepartmentController {

    @Autowired private IDepartmentFilterService departmentFilterService;
	
	// -------- Department filters --------
    
    // FILTER departments by keyword in name
    // localhost:8080/admin/department/filter/name/Department
    @GetMapping("/department/filter/name/{keyword}")
    public String filterDepartmentsByName(@PathVariable(name = "keyword") String keyword, Model model) {
        try {
            model.addAttribute("package", departmentFilterService.filterByKeyword(keyword));
            return "admin-department-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }
    
    
}
