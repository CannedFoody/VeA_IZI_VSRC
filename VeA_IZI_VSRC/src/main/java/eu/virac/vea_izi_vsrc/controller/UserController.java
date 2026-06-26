package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import eu.virac.vea_izi_vsrc.service.ICRUDCategoryService;
import eu.virac.vea_izi_vsrc.service.ICRUDDepartmentHeadService;
import eu.virac.vea_izi_vsrc.service.ICRUDDepartmentService;
import eu.virac.vea_izi_vsrc.service.ICRUDKPIService;
import eu.virac.vea_izi_vsrc.service.ICRUDProjectService;
import eu.virac.vea_izi_vsrc.service.ICRUDSubCategoryService;
import eu.virac.vea_izi_vsrc.service.ICRUDTaskService;
import eu.virac.vea_izi_vsrc.service.ICRUDUserService;

@Controller
@RequestMapping("/User")
public class UserController {

    @Autowired
    private ICRUDUserService userService;

    @Autowired
    private ICRUDCategoryService categoryService;

    @Autowired
    private ICRUDDepartmentService departmentService;

    @Autowired
    private ICRUDDepartmentHeadService departmentHeadService;

    @Autowired
    private ICRUDKPIService kpiService;

    @Autowired
    private ICRUDProjectService projectService;

    @Autowired
    private ICRUDSubCategoryService subCategoryService;

    @Autowired
    private ICRUDTaskService taskService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        try {
            return ResponseEntity.ok(categoryService.getAllCategories());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/departments")
    public ResponseEntity<?> getAllDepartments() {
        try {
            return ResponseEntity.ok(departmentService.getAllDepartments());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/department-heads")
    public ResponseEntity<?> getAllDepartmentHeads() {
        try {
            return ResponseEntity.ok(
                    departmentHeadService.getAllDepartmentHeads()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/kpis")
    public ResponseEntity<?> getAllKPIs() {
        try {
            return ResponseEntity.ok(kpiService.getAllKPIs());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/projects")
    public ResponseEntity<?> getAllProjects() {
        try {
            return ResponseEntity.ok(projectService.getAllProjects());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/subcategories")
    public ResponseEntity<?> getAllSubCategories() {
        try {
            return ResponseEntity.ok(
                    subCategoryService.getAllSubCategories()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks() {
        try {
            return ResponseEntity.ok(taskService.getAllTasks());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(categoryService.getCategoryById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(departmentService.getDepartmentById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/department-heads/{id}")
    public ResponseEntity<?> getDepartmentHeadById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(
                    departmentHeadService.getDepartmentHeadById(id)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/kpis/{id}")
    public ResponseEntity<?> getKPIById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(kpiService.getKPIById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(projectService.getProjectById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/subcategories/{id}")
    public ResponseEntity<?> getSubCategoryById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(
                    subCategoryService.getSubCategoryById(id)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(taskService.getTaskById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}