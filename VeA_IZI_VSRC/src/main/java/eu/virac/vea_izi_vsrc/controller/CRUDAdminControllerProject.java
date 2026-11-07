package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.virac.vea_izi_vsrc.model.Project;
import eu.virac.vea_izi_vsrc.service.ICRUDKPIService;
import eu.virac.vea_izi_vsrc.service.ICRUDProjectService;

@Controller
@RequestMapping("/admin")
public class CRUDAdminControllerProject {


    @Autowired private ICRUDProjectService projectService;
    @Autowired private ICRUDKPIService kpiService;
    
	// ------------------------------------------------------------
    // --- ADMIN PROJECT ---
    // ------------------------------------------------------------

    // SHOW ALL projects
    // localhost:8080/admin/project/show/all
    @GetMapping("/project/show/all")
    public String showAllProjects(Model model) {
        
        try {
            model.addAttribute("package", projectService.getAllProjects());
            return "admin-project-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
    // SHOW 1 project by ID
    // localhost:8080/admin/project/show/1
    @GetMapping("/project/show/{id}")
    public String showProjectById(@PathVariable(name = "id") long id, Model model) {
        
        try {
            model.addAttribute("package", projectService.getProjectById(id));
            return "admin-project-show-one-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
    // ADD project GET
    // localhost:8080/admin/project/add
    @GetMapping("/project/add")
    public String addProjectGet(Model model) {
        try {
            model.addAttribute("project", new Project());
            return "admin-project-add-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }
    
    // ADD project POST
    // After successful creation redirects to: localhost:8080/admin/project/show/all
    @PostMapping("/project/add")
    public String addProjectPost(Project project, Model model) {
        
        try {
            projectService.createNewProject(project);
            model.addAttribute("package", projectService.getAllProjects());
            return "admin-project-show-all-page";
        }
        catch (Exception e) {
        	model.addAttribute("project", project);
            model.addAttribute("errorMsg", e.getMessage());
            return "admin-project-add-page";
        }
    }

    
    
    // UPDATE project by ID GET
    // localhost:8080/admin/project/update/2
    @GetMapping("/project/update/{id}")
    public String updateProjectGet(@PathVariable(name = "id") long id, Model model) {
        
        try {
            model.addAttribute("project", projectService.getProjectById(id));
            model.addAttribute("kpis", kpiService.getAllKPIs());
            return "admin-project-update-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // UPDATE project by ID POST
    // After successful update redirects to: localhost:8080/admin/project/show/all
    @PostMapping("/project/update/{id}")
    public String updateProjectPost(@PathVariable(name = "id") long id, Project project, Model model) {
    	
        try {
            projectService.updateProject(id,
                    project.getTitle(),
                    project.getDescription());

            model.addAttribute("package", projectService.getAllProjects());
            return "admin-project-show-all-page";
        }
        catch (Exception e) {
        	model.addAttribute("project", project);
            model.addAttribute("errorMsg", e.getMessage());
            return "admin-project-update-page";
        }
    }

    // REMOVE/delete project by ID
    // localhost:8080/admin/project/remove/1
    // After deletion redirects to: localhost:8080/admin/project/show/all	
    @GetMapping("/project/remove/{id}")
    public String deleteProject(@PathVariable(name = "id") long id, Model model) {
        
        try {
            projectService.deleteProject(id);
            model.addAttribute("package", projectService.getAllProjects());
            return "admin-project-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }
    
    
}
