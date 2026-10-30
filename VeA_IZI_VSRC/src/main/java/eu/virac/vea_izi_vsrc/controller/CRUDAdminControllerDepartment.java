package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.virac.vea_izi_vsrc.model.Department;
import eu.virac.vea_izi_vsrc.service.ICRUDDepartmentService;

@Controller
@RequestMapping("/admin")
public class CRUDAdminControllerDepartment {

	@Autowired private ICRUDDepartmentService departmentService;
	
	// ------------------------------------------------------------
    // --- ADMIN DEPARTMENT ---
    // ------------------------------------------------------------

    // SHOW ALL departments
    // localhost:8080/admin/department/show/all
    @GetMapping("/department/show/all")
    public String showAllDepartments(Model model) {
        
        try {
            model.addAttribute("package", departmentService.getAllDepartments());
            return "admin-department-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // SHOW 1 department by ID
    // localhost:8080/admin/department/show/1
    @GetMapping("/department/show/{id}")
    public String showDepartmentById(@PathVariable(name = "id") long id, Model model) {
        
        try {
            model.addAttribute("package", departmentService.getDepartmentById(id));
            return "admin-department-show-one-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }


	// ADD department GET
	// localhost:8080/admin/department/add
	@GetMapping("/department/add")
	public String addDepartmentGet(Model model) {
	
	    try {
	        model.addAttribute("department", new Department());
	        return "admin-department-add-page";
	    }
	    catch (Exception e) {
	        model.addAttribute("package", e.getMessage());
	        return "error-page";
	    }
	}

	// ADD department POST
	// After successful creation redirects to: localhost:8080/admin/department/show/all
    @PostMapping("/department/add")
    public String addDepartmentPost(Department department, Model model) {
        
        try {
            departmentService.createNewDepartment(department);
            model.addAttribute("package", departmentService.getAllDepartments());
            return "admin-department-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("department", department);
            model.addAttribute("errorMsg", e.getMessage());
            return "admin-department-add-page";
        }
    }

    // UPDATE department by ID GET
    // localhost:8080/admin/department/update/2
    @GetMapping("/department/update/{id}")
    public String updateDepartmentGet(@PathVariable(name = "id") long id, Model model) {
        
        try {
            model.addAttribute("department", departmentService.getDepartmentById(id));
            return "admin-department-update-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // UPDATE department by ID POST
    // After successful update redirects to: localhost:8080/admin/department/show/all
    @PostMapping("/department/update/{id}")
    public String updateDepartmentPost(@PathVariable(name = "id") long id, 
    Department department, Model model) {
        
        try {
            departmentService.updateDepartment(id,
                    department.getName(),
                    department.getDescription(),
                    department.getGoals());

            model.addAttribute("package", departmentService.getAllDepartments());
            return "admin-department-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("department", department);
            model.addAttribute("errorMsg", e.getMessage());
            return "admin-department-update-page";
        }
    }

    
    // REMOVE/delete department by ID
    // localhost:8080/admin/department/remove/2
    // After deletion redirects to: localhost:8080/admin/department/show/all
    @GetMapping("/department/remove/{id}")
    public String deleteDepartment(@PathVariable(name = "id") long id, Model model) {
        
        try {
            departmentService.deleteDepartment(id);
            model.addAttribute("package", departmentService.getAllDepartments());
            return "admin-department-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
    
}
