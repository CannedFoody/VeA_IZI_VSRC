package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.virac.vea_izi_vsrc.model.Task;
import eu.virac.vea_izi_vsrc.model.Enums.TaskStatus;
import eu.virac.vea_izi_vsrc.service.ICRUDKPIService;
import eu.virac.vea_izi_vsrc.service.ICRUDSubCategoryService;
import eu.virac.vea_izi_vsrc.service.ICRUDTaskService;

@Controller
@RequestMapping("/admin")
public class CRUDAdminControllerTask {

    @Autowired private ICRUDTaskService taskService;
    @Autowired private ICRUDKPIService kpiService;
    @Autowired private ICRUDSubCategoryService subCategoryService;
    
    
	// ------------------------------------------------------------
    // --- ADMIN TASK ---
    // ------------------------------------------------------------

    // SHOW ALL tasks
    // localhost:8080/admin/task/show/all
    @GetMapping("/task/show/all")
    public String showAllTasks(Model model) {
        
        try {
            model.addAttribute("package", taskService.getAllTasks());
            return "admin-task-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // SHOW 1 task by ID
    // localhost:8080/admin/task/show/1
    @GetMapping("/task/show/{id}")
    public String showTaskById(@PathVariable(name = "id") long id, Model model) {
       
        try {
            model.addAttribute("package", taskService.getTaskById(id));
            return "admin-task-show-one-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }


    // ADD task GET
    // localhost:8080/admin/task/add
    @GetMapping("/task/add")
    public String addTaskGet(Model model) {

        try {
            model.addAttribute("task", new Task());
            model.addAttribute("statuses", TaskStatus.values());
            model.addAttribute("kpis", kpiService.getAllKPIs());
            model.addAttribute("subcategories", subCategoryService.getAllSubCategories());
            return "admin-task-add-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }
    
    // ADD task POST
    // After successful creation redirects to: localhost:8080/admin/task/show/all
    @PostMapping("/task/add")
    public String addTaskPost(Task task, Model model) {
        
        try {
            taskService.createNewTask(task);
            model.addAttribute("package", taskService.getAllTasks());
            return "admin-task-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("task", task);

            try {
                model.addAttribute("statuses", TaskStatus.values());
                model.addAttribute("kpis", kpiService.getAllKPIs());
                model.addAttribute("subcategories",subCategoryService.getAllSubCategories());
            }
            catch (Exception ignored) {}

            model.addAttribute("errorMsg",e.getMessage());
            return "admin-task-add-page";
        }
    }

    
    // UPDATE task by ID GET
    // localhost:8080/admin/task/update/2
    @GetMapping("/task/update/{id}")
    public String updateTaskGet(@PathVariable(name = "id") long id, Model model) {
        
        try {
            model.addAttribute("task", taskService.getTaskById(id));
            model.addAttribute("statuses", TaskStatus.values());
            model.addAttribute("kpis", kpiService.getAllKPIs());
            model.addAttribute("subcategories", subCategoryService.getAllSubCategories());
            return "admin-task-update-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // UPDATE task by ID POST
    // After successful update redirects to: localhost:8080/admin/task/show/all
    @PostMapping("/task/update/{id}")
    public String updateTaskPost(@PathVariable(name = "id") long id,  Task task, Model model) {

        try {
            taskService.updateTask(id,
                    task.getTitle(),
                    task.getDescription(),
                    task.getStatus(),
                    task.getKpi(),
                    task.getSubCategory());

            model.addAttribute("package", taskService.getAllTasks());
            return "admin-task-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("task", task);

            try {
                model.addAttribute("statuses", TaskStatus.values());
                model.addAttribute("kpis", kpiService.getAllKPIs());
                model.addAttribute("subcategories", subCategoryService.getAllSubCategories());
            }
            catch (Exception ignored) {}
            model.addAttribute("errorMsg",e.getMessage());
            return "admin-task-update-page";
        }
    }

    // REMOVE/delete task by ID
    // localhost:8080/admin/task/remove/2
    // After deletion redirects to: localhost:8080/admin/task/show/all
    @GetMapping("/task/remove/{id}")
    public String deleteTask(@PathVariable(name = "id") long id, Model model) {
        
        try {
            taskService.deleteTask(id);
            model.addAttribute("package", taskService.getAllTasks());
            return "admin-task-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }


    
    
}
