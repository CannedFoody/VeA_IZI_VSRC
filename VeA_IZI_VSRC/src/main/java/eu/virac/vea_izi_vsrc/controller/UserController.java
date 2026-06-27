package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class    UserController {

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
    public String getAllUsers(Model model) throws Exception {
        model.addAttribute("users", userService.getAllUsers());
        return "user-user-show-all-page";
    }

    @GetMapping("/{id}")
    public String getUserById(
            @PathVariable long id,
            Model model) throws Exception {

        model.addAttribute("user", userService.getUserById(id));
        return "user-user-show-one-page";
    }

    @GetMapping("/categories")
    public String getAllCategories(Model model) throws Exception {
        model.addAttribute(
                "categories",
                categoryService.getAllCategories()
        );
        return "user-category-show-all-page";
    }

    @GetMapping("/categories/{id}")
    public String getCategoryById(
            @PathVariable long id,
            Model model) throws Exception {

        model.addAttribute(
                "category",
                categoryService.getCategoryById(id)
        );
        return "user-category-show-one-page";
    }

    @GetMapping("/departments")
    public String getAllDepartments(Model model) throws Exception {
        model.addAttribute(
                "departments",
                departmentService.getAllDepartments()
        );
        return "user-department-show-all-page";
    }

    @GetMapping("/departments/{id}")
    public String getDepartmentById(
            @PathVariable long id,
            Model model) throws Exception {

        model.addAttribute(
                "department",
                departmentService.getDepartmentById(id)
        );
        return "user-department-show-one-page";
    }

    @GetMapping("/department-heads")
    public String getAllDepartmentHeads(Model model) throws Exception {
        model.addAttribute(
                "departmentHeads",
                departmentHeadService.getAllDepartmentHeads()
        );
        return "user-departmenthead-show-all-page";
    }

    @GetMapping("/department-heads/{id}")
    public String getDepartmentHeadById(
            @PathVariable long id,
            Model model) throws Exception {

        model.addAttribute(
                "departmentHead",
                departmentHeadService.getDepartmentHeadById(id)
        );
        return "user-departmenthead-show-one-page";
    }

    @GetMapping("/kpis")
    public String getAllKPIs(Model model) throws Exception {
        model.addAttribute("kpis", kpiService.getAllKPIs());
        return "user-kpi-show-all-page";
    }

    @GetMapping("/kpis/{id}")
    public String getKPIById(
            @PathVariable long id,
            Model model) throws Exception {

        model.addAttribute("kpi", kpiService.getKPIById(id));
        return "user-kpi-show-one-page";
    }

    @GetMapping("/projects")
    public String getAllProjects(Model model) throws Exception {
        model.addAttribute(
                "projects",
                projectService.getAllProjects()
        );
        return "user-project-show-all-page";
    }

    @GetMapping("/projects/{id}")
    public String getProjectById(
            @PathVariable long id,
            Model model) throws Exception {

        model.addAttribute(
                "project",
                projectService.getProjectById(id)
        );
        return "user-project-show-one-page";
    }

    @GetMapping("/subcategories")
    public String getAllSubCategories(Model model) throws Exception {
        model.addAttribute(
                "subcategories",
                subCategoryService.getAllSubCategories()
        );
        return "user-subcategory-show-all-page";
    }

    @GetMapping("/subcategories/{id}")
    public String getSubCategoryById(
            @PathVariable long id,
            Model model) throws Exception {

        model.addAttribute(
                "subcategory",
                subCategoryService.getSubCategoryById(id)
        );
        return "user-subcategory-show-one-page";
    }

    @GetMapping("/tasks")
    public String getAllTasks(Model model) throws Exception {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "user-task-show-all-page";
    }

    @GetMapping("/tasks/{id}")
    public String getTaskById(
            @PathVariable long id,
            Model model) throws Exception {

        model.addAttribute("task", taskService.getTaskById(id));
        return "user-task-show-one-page";
    }
}