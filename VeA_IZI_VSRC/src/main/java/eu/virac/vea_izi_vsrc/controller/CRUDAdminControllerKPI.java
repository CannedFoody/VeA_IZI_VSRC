package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.virac.vea_izi_vsrc.model.KPI;
import eu.virac.vea_izi_vsrc.model.Enums.KPIStatus;
import eu.virac.vea_izi_vsrc.service.ICRUDCategoryService;
import eu.virac.vea_izi_vsrc.service.ICRUDKPIService;
import eu.virac.vea_izi_vsrc.service.ICRUDUserService;

@Controller
@RequestMapping("/admin")
public class CRUDAdminControllerKPI {

    @Autowired private ICRUDKPIService kpiService;
    @Autowired private ICRUDUserService userService;
    @Autowired private ICRUDCategoryService categoryService;
    
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
    public String addKPIPost(KPI kpi, Model model, Authentication authentication) {
        
        try {
        	kpiService.createNewKPI(kpi, authentication.getName());
            model.addAttribute("package", kpiService.getAllKPIs());
            return "admin-kpi-show-all-page";
        }
        catch (Exception e) {
        	model.addAttribute("kpi", kpi);
        	model.addAttribute("errorMsg", e.getMessage());
            return "admin-kpi-add-page";
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
        	model.addAttribute("kpi", kpi);
        	model.addAttribute("errorMsg", e.getMessage());
            return "admin-kpi-update-page";
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

    
    
}
