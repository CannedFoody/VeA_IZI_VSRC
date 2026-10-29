package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import eu.virac.vea_izi_vsrc.service.IFilterUserService;

public class FilterUserController {
	
	
    @Autowired private IFilterUserService userFilterService;

    // -------- User filters --------
    
    // FILTER users by role
    // localhost:8080/admin/user/filter/role/Worker
    @GetMapping("/user/filter/role/{role}")
    public String filterUsersByRole(@PathVariable(name = "role") String role, Model model) {
        try {
            model.addAttribute("package", userFilterService.filterUsersByRole(role));
            return "admin-user-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // FILTER users by department ID
    // localhost:8080/admin/user/filter/department/2    
    @GetMapping("/user/filter/department/{id}")
    public String filterUsersByDepartment(@PathVariable(name = "id") long id, Model model) {
        try {
            model.addAttribute("package", userFilterService.filterUsersByDepartmentId(id));
            return "admin-user-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }
    
    
}
