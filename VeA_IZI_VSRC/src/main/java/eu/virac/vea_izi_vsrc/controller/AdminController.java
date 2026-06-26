package eu.virac.vea_izi_vsrc.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.virac.vea_izi_vsrc.model.Category;
import eu.virac.vea_izi_vsrc.model.Department;
import eu.virac.vea_izi_vsrc.model.DepartmentHead;
import eu.virac.vea_izi_vsrc.model.KPI;
import eu.virac.vea_izi_vsrc.model.Project;
import eu.virac.vea_izi_vsrc.model.SubCategory;
import eu.virac.vea_izi_vsrc.model.Task;
import eu.virac.vea_izi_vsrc.model.User;
import eu.virac.vea_izi_vsrc.model.Enums.KPIStatus;
import eu.virac.vea_izi_vsrc.model.Enums.TaskStatus;
import eu.virac.vea_izi_vsrc.service.ICRUDCategoryService;
import eu.virac.vea_izi_vsrc.service.ICRUDDepartmentHeadService;
import eu.virac.vea_izi_vsrc.service.ICRUDDepartmentService;
import eu.virac.vea_izi_vsrc.service.ICRUDKPIService;
import eu.virac.vea_izi_vsrc.service.ICRUDProjectService;
import eu.virac.vea_izi_vsrc.service.ICRUDSubCategoryService;
import eu.virac.vea_izi_vsrc.service.ICRUDTaskService;
import eu.virac.vea_izi_vsrc.service.ICRUDUserService;
import eu.virac.vea_izi_vsrc.service.IDepartmentFilterService;
import eu.virac.vea_izi_vsrc.service.IDepartmentHeadFilterService;
import eu.virac.vea_izi_vsrc.service.IFilterProjectService;
import eu.virac.vea_izi_vsrc.service.IFilterUserService;
import eu.virac.vea_izi_vsrc.service.IKPIFilterStatsService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // ------------------------------------------------------------
    // --- SERVICE INJECTIONS ---
    // ------------------------------------------------------------

    @Autowired private ICRUDCategoryService categoryService;
    @Autowired private ICRUDSubCategoryService subCategoryService;
    @Autowired private ICRUDDepartmentService departmentService;
    @Autowired private ICRUDDepartmentHeadService departmentHeadService;
    @Autowired private ICRUDKPIService kpiService;
    @Autowired private ICRUDProjectService projectService;
    @Autowired private ICRUDTaskService taskService;
    @Autowired private ICRUDUserService userService;

    @Autowired private IDepartmentFilterService departmentFilterService;
    @Autowired private IDepartmentHeadFilterService departmentHeadFilterService;
    @Autowired private IKPIFilterStatsService kpiFilterService;
    @Autowired private IFilterProjectService projectFilterService;
    @Autowired private IFilterUserService userFilterService;

	
    // ------------------------------------------------------------
    // --- ADMIN CATEGORY ---
    // ------------------------------------------------------------

    // SHOW ALL categories
    // localhost:8080/admin/category/show/all
    @GetMapping("/category/show/all")
    public String showAllCategories(Model model) {
        
        try {
            model.addAttribute("package", categoryService.getAllCategories());
            return "admin-category-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // SHOW 1 category by ID
    // localhost:8080/admin/category/show/1
    @GetMapping("/category/show/{id}")
    public String showCategoryById(@PathVariable(name = "id") long id, Model model) {
        
        try {
            model.addAttribute("package", categoryService.getCategoryById(id));
            return "admin-category-show-one-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // ADD category GET
    // localhost:8080/admin/category/add
    @GetMapping("/category/add")
    public String addCategoryGet(Model model) {

        try {
            model.addAttribute("category", new Category());
            return "admin-category-add-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // ADD category POST
    // After successful creation redirects to: localhost:8080/admin/category/show/all
    @PostMapping("/category/add")
    public String addCategoryPost(Category category, Model model) {
        
        try {
            categoryService.createNewCategory(category);
            model.addAttribute("package", categoryService.getAllCategories());
            return "admin-category-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // UPDATE category by ID GET
    // localhost:8080/admin/category/update/2
    @GetMapping("/category/update/{id}")
    public String updateCategoryGet(@PathVariable(name = "id") long id, Model model) {
        
        try {
            model.addAttribute("category", categoryService.getCategoryById(id));
            return "admin-category-update-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }


    // UPDATE category by ID POST
    // After successful update, user is redirected to: localhost:8080/admin/category/show/all
    @PostMapping("/category/update/{id}")
    public String updateCategoryPost(@PathVariable(name = "id") long id, @ModelAttribute("category") 
    Category category, Model model) {
        
        try {
            categoryService.updateCategory(id, category.getName(), category.getDescription());
            model.addAttribute("package", categoryService.getAllCategories());
            return "admin-category-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // REMOVE/delete by ID
    // localhost:8080/admin/category/remove/2
    // After deletion, user is redirected to: localhost:8080/admin/category/show/all
    @GetMapping("/category/remove/{id}")
    public String deleteCategory(@PathVariable(name = "id") long id, Model model) {
        
        try {
            categoryService.deleteCategory(id);
            model.addAttribute("package", categoryService.getAllCategories());
            return "admin-category-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }



    // ------------------------------------------------------------
    // --- ADMIN SUBCATEGORY ----
    // ------------------------------------------------------------

    // SHOW ALL subcategories
    // localhost:8080/admin/subcategory/show/all
    @GetMapping("/subcategory/show/all")
    public String showAllSubCategories(Model model) {
        
        try {
            model.addAttribute("package", subCategoryService.getAllSubCategories());
            return "admin-subcategory-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

	// SHOW 1 subcategory by ID
	// localhost:8080/admin/subcategory/show/1
    @GetMapping("/subcategory/show/{id}")
    public String showSubCategoryById(@PathVariable(name = "id") long id, Model model) {
        
        try {
            model.addAttribute("package", subCategoryService.getSubCategoryById(id));
            return "admin-subcategory-show-one-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
	// ADD subcategory GET
	// localhost:8080/admin/subcategory/add
	@GetMapping("/subcategory/add")
	public String addSubCategoryGet(Model model) {
	
	    try {
	        model.addAttribute("subcategory", new SubCategory());
	        return "admin-subcategory-add-page";
	    }
	    catch (Exception e) {
	        model.addAttribute("package", e.getMessage());
	        return "error-page";
	    }
	}
	
	// ADD subcategory POST
	// After successful creation redirects to: localhost:8080/admin/subcategory/show/all
    @PostMapping("/subcategory/add")
    public String addSubCategoryPost(SubCategory subCategory, Model model) {
        
        try {
            subCategoryService.createNewSubCategory(subCategory);
            model.addAttribute("package", subCategoryService.getAllSubCategories());
            return "admin-subcategory-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // UPDATE subcategory by ID GET
    // localhost:8080/admin/subcategory/update/1
    @GetMapping("/subcategory/update/{id}")
    public String updateSubCategoryGet(@PathVariable(name = "id") long id, Model model) {
        try {
            model.addAttribute("subcategory", subCategoryService.getSubCategoryById(id));
            return "admin-subcategory-update-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
    // UPDATE subcategory by ID POST
    // After successful update redirects to: localhost:8080/admin/subcategory/show/all
    @PostMapping("/subcategory/update/{id}")
    public String updateSubCategoryPost(@PathVariable(name = "id") long id, @ModelAttribute("subcategory") 
    SubCategory subCategory, Model model) {
        
        try {
            subCategoryService.updateSubCategory(id, subCategory.getTitle(), subCategory.getDescription());
            model.addAttribute("package", subCategoryService.getAllSubCategories());
            return "admin-subcategory-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // REMOVE/delete subcategory by ID
    // localhost:8080/admin/subcategory/remove/2
    // After deletion redirects to: localhost:8080/admin/subcategory/show/all
    @GetMapping("/subcategory/remove/{id}")
    public String deleteSubCategory(@PathVariable(name = "id") long id, Model model) {
        
        try {
            subCategoryService.deleteSubCategory(id);
            model.addAttribute("package", subCategoryService.getAllSubCategories());
            return "admin-subcategory-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }



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
            model.addAttribute("package", e.getMessage());
            return "error-page";
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
            model.addAttribute("package", e.getMessage());
            return "error-page";
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



    // ------------------------------------------------------------
    // --- ADMIN KPI ---
    // ------------------------------------------------------------

    // SHOW ALL KPIs
    // localhost:8080/admin/kpi/show/all
    @GetMapping("/kpi/show/all")
    public String showAllKPIs(Model model) {
        
        try {
            model.addAttribute("package", kpiService.getAllKPIs());
            return "admin-kpi-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // SHOW 1 KPI by ID
    // localhost:8080/admin/kpi/show/1
    @GetMapping("/kpi/show/{id}")
    public String showKPIById(@PathVariable(name = "id") long id, Model model) {
        
        try {
            model.addAttribute("package", kpiService.getKPIById(id));
            return "admin-kpi-show-one-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
    // ADD KPI GET
    // localhost:8080/admin/kpi/add
    @GetMapping("/kpi/add")
    public String addKPIGet(Model model) {
        
        try {
            model.addAttribute("kpi", new KPI());
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("statuses", KPIStatus.values());	//dropdown in HTML
            return "admin-kpi-add-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // ADD KPI POST
    // After successful creation redirects to: localhost:8080/admin/kpi/show/all
    @PostMapping("/kpi/add")
    public String addKPIPost(KPI kpi, Model model) {
        
        try {
            kpiService.createNewKPI(kpi);
            model.addAttribute("package", kpiService.getAllKPIs());
            return "admin-kpi-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // UPDATE KPI by ID GET
    // localhost:8080/admin/kpi/update/3
    @GetMapping("/kpi/update/{id}")
    public String updateKPIGet(@PathVariable(name = "id") long id, Model model) {
        
        try {
            model.addAttribute("kpi", kpiService.getKPIById(id));
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("statuses", KPIStatus.values());
            return "admin-kpi-update-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // UPDATE KPI by ID POST
    // After successful update redirects to: localhost:8080/admin/kpi/show/all
    @PostMapping("/kpi/update/{id}")
    public String updateKPIPost(@PathVariable(name = "id") long id, KPI kpi, Model model) {
    
        try {
            kpiService.updateKPI(id,
                    kpi.getCreationDate(),
                    kpi.getDeadline(),
                    kpi.getTitle(),
                    kpi.getDescription(),
                    kpi.getStatus(),
                    kpi.getCategory(),
                    kpi.getCreator(),
                    kpi.getOverlooker());

            model.addAttribute("package", kpiService.getAllKPIs());
            return "admin-kpi-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
    // REMOVE/delete KPI by ID
    // localhost:8080/admin/kpi/remove/5
    // After deletion redirects to: localhost:8080/admin/kpi/show/all
    @GetMapping("/kpi/remove/{id}")
    public String deleteKPI(@PathVariable(name = "id") long id, Model model) {
        
        try {
            kpiService.deleteKPI(id);
            model.addAttribute("package", kpiService.getAllKPIs());
            return "admin-kpi-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }



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
            model.addAttribute("package", e.getMessage());
            return "error-page";
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
            model.addAttribute("package", e.getMessage());
            return "error-page";
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
            model.addAttribute("package", e.getMessage());
            return "error-page";
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
            model.addAttribute("package", e.getMessage());
            return "error-page";
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



    // ------------------------------------------------------------
    // --- ADMIN USER ---
    // ------------------------------------------------------------

    // SHOW ALL users
    // localhost:8080/admin/user/show/all
    @GetMapping("/user/show/all")
    public String showAllUsers(Model model) {
        
        try {
            model.addAttribute("package", userService.getAllUsers());
            return "admin-user-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // SHOW 1 user by ID
    // localhost:8080/admin/user/show/1
    @GetMapping("/user/show/{id}")
    public String showUserById(@PathVariable(name = "id") long id, Model model) {
        
        try {
            model.addAttribute("package", userService.getUserById(id));
            return "admin-user-show-one-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // ADD user GET
    // localhost:8080/admin/user/add
    @GetMapping("/user/add")
    public String addUserGet(Model model) {
        
        try {
            model.addAttribute("user", new User());
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "admin-user-add-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }
    
    // ADD user POST
    // After successful creation redirects to: localhost:8080/admin/user/show/all
    @PostMapping("/user/add")
    public String addUserPost(User user, Model model) {
       
        try {
            userService.createNewUser(user);
            model.addAttribute("package", userService.getAllUsers());
            return "admin-user-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // UPDATE user by ID GET
    // localhost:8080/admin/user/update/2
    @GetMapping("/user/update/{id}")
    public String updateUserGet(@PathVariable(name = "id") long id, Model model) {
        
        try {
            model.addAttribute("user", userService.getUserById(id));
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "admin-user-update-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // UPDATE user by ID POST
    // After successful update redirects to: localhost:8080/admin/user/show/all
    @PostMapping("/user/update/{id}")
    public String updateUserPost(@PathVariable(name = "id") long id,  User user, Model model) {
        
        try {
            userService.updateUser(id,
                    user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    user.getRole(),
                    user.getDepartment());

            model.addAttribute("package", userService.getAllUsers());
            return "admin-user-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
    
    // REMOVE/delete user by ID
    // localhost:8080/admin/user/remove/2
    // After deletion redirects to: localhost:8080/admin/user/show/all
    @GetMapping("/user/remove/{id}")
    public String deleteUser(@PathVariable(name = "id") long id, Model model) {
        
        try {
            userService.deleteUser(id);
            model.addAttribute("package", userService.getAllUsers());
            return "admin-user-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }



    // ------------------------------------------------------------
    // --- ADMIN FILTERS ---
    // ------------------------------------------------------------

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

    
    
    // -------- DepartmentHead filters --------
    
    // FILTER department heads by starting date AFTER given date
    // localhost:8080/admin/departmenthead/filter/after/2025-01-01
    @GetMapping("/departmenthead/filter/after/{date}")
    public String filterDepartmentHeadsByStartDate(@PathVariable(name = "date") LocalDate date, Model model) {

        try {
            model.addAttribute("package", departmentHeadFilterService.filterByStartingDateAfter(date));
            return "admin-departmenthead-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }


    
    // -------- KPI filters --------
    
    // FILTER KPIs by creation date AFTER given date
    // localhost:8080/admin/kpi/filter/after/2026-01-01
    @GetMapping("/kpi/filter/after/{date}")
    public String filterKPIsByCreationDate(@PathVariable(name = "date") LocalDate date,  Model model) {

        try {
            model.addAttribute("package", kpiFilterService.filterByUploadDateAfter(date));
            return "admin-kpi-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
    // FILTER KPIs by status
    // localhost:8080/admin/kpi/filter/status/Active
    @GetMapping("/kpi/filter/status/{status}")
    public String filterKPIsByStatus(@PathVariable(name = "status") KPIStatus status, Model model) {
        
    	try {
            model.addAttribute("package", kpiFilterService.filterByStatus(status));
            return "admin-kpi-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // FILTER KPIs by category ID
    // localhost:8080/admin/kpi/filter/category/1
    @GetMapping("/kpi/filter/category/{id}")
    public String filterKPIsByCategory(@PathVariable(name = "id") long id, Model model) {
        
    	try {
            Category category = categoryService.getCategoryById(id);
            model.addAttribute("package", kpiFilterService.filterByCategory(category));
            return "admin-kpi-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    // FILTER KPIs by creator ID
    // localhost:8080/admin/kpi/filter/creator/4
    @GetMapping("/kpi/filter/creator/{id}")
    public String filterKPIsByCreator(@PathVariable(name = "id") long id, Model model) {
        
    	try {
            model.addAttribute("package", kpiFilterService.filterByCreatorId(id));
            return "admin-kpi-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
    // FILTER KPIs where deadline has passed
    // localhost:8080/admin/kpi/filter/deadline/passed
    @GetMapping("/kpi/filter/deadline/passed")
    public String filterKPIsByDeadlinePassed(Model model) {
        try {
            model.addAttribute("package", kpiFilterService.filterByAfterDeadline());
            return "admin-kpi-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
    
    // -------- Project filters --------
    
    // FILTER projects by keyword in title/description
    // localhost:8080/admin/project/filter/keyword/Project
    @GetMapping("/project/filter/keyword/{keyword}")
    public String filterProjectsByKeyword(@PathVariable(name = "keyword") String keyword, Model model) {
        try {
            model.addAttribute("package", projectFilterService.filterByKeyword(keyword));
            return "admin-project-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

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

	 

	
	

