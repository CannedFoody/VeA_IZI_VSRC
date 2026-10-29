package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.virac.vea_izi_vsrc.model.DepartmentHead;
import eu.virac.vea_izi_vsrc.service.ICRUDDepartmentHeadService;
import eu.virac.vea_izi_vsrc.service.ICRUDDepartmentService;
import eu.virac.vea_izi_vsrc.service.ICRUDUserService;

@Controller
@RequestMapping("/admin")
public class CRUDAdminControllerDepartmentHead {


    @Autowired private ICRUDDepartmentHeadService departmentHeadService;
	@Autowired private ICRUDDepartmentService departmentService;
    @Autowired private ICRUDUserService userService;
	
	// ------------------------------------------------------------
    // --- ADMIN DEPARTMENT HEAD ---
    // ------------------------------------------------------------

    // SHOW ALL department heads
    // localhost:8080/admin/departmenthead/show/all
    @GetMapping("/departmenthead/show/all")
    public String showAllDepartmentHeads(Model model) {
        
        try {
            model.addAttribute("package", departmentHeadService.getAllDepartmentHeads());
            return "admin-departmenthead-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
    // SHOW 1 department head by ID
    // localhost:8080/admin/departmenthead/show/1
    @GetMapping("/departmenthead/show/{id}")
    public String showDepartmentHeadById(@PathVariable(name = "id") long id, Model model) {
        
        try {
            model.addAttribute("package", departmentHeadService.getDepartmentHeadById(id));
            return "admin-departmenthead-show-one-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // ADD department head GET
    // localhost:8080/admin/departmenthead/add
    @GetMapping("/departmenthead/add")
    public String addDepartmentHeadGet(Model model) {
        
        try {
            model.addAttribute("departmenthead", new DepartmentHead());
            model.addAttribute("departments", departmentService.getAllDepartments());
            model.addAttribute("users", userService.getAllUsers());
            return "admin-departmenthead-add-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // ADD department head POST
    // After successful creation redirects to: localhost:8080/admin/departmenthead/show/all
    @PostMapping("/departmenthead/add")
    public String addDepartmentHeadPost(DepartmentHead dh, Model model) {
        
        try {
            departmentHeadService.createNewDepartmentHead(dh);
            model.addAttribute("package", departmentHeadService.getAllDepartmentHeads());
            return "admin-departmenthead-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // UPDATE department head by ID GET
    // localhost:8080/admin/departmenthead/update/2
    @GetMapping("/departmenthead/update/{id}")
    public String updateDepartmentHeadGet(@PathVariable(name = "id") long id, Model model) {
        
        try {
            model.addAttribute("departmenthead", departmentHeadService.getDepartmentHeadById(id));
            model.addAttribute("departments", departmentService.getAllDepartments());
            model.addAttribute("users", userService.getAllUsers());
            return "admin-departmenthead-update-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // UPDATE department head by ID POST
    // After successful update redirects to: localhost:8080/admin/departmenthead/show/all
    @PostMapping("/departmenthead/update/{id}")
    public String updateDepartmentHeadPost(@PathVariable(name = "id") long id,
    DepartmentHead dh, Model model) {
    
        try {
            departmentHeadService.updateDepartmentHead(id,
                    dh.getStartingDate(),
                    dh.getEndingDate(),
                    dh.getDepartment(),
                    dh.getUser());

            model.addAttribute("package", departmentHeadService.getAllDepartmentHeads());
            return "admin-departmenthead-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
    // REMOVE/delete department head by ID
    // localhost:8080/admin/departmenthead/remove/1
    // After deletion redirects to: localhost:8080/admin/departmenthead/show/all
    @GetMapping("/departmenthead/remove/{id}")
    public String deleteDepartmentHead(@PathVariable(name = "id") long id, Model model) {
        
        try {
            departmentHeadService.deleteDepartmentHead(id);
            model.addAttribute("package", departmentHeadService.getAllDepartmentHeads());
            return "admin-departmenthead-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
    
}
