package eu.virac.vea_izi_vsrc.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.virac.vea_izi_vsrc.model.Category;
import eu.virac.vea_izi_vsrc.model.Enums.KPIStatus;
import eu.virac.vea_izi_vsrc.service.ICRUDCategoryService;
import eu.virac.vea_izi_vsrc.service.IKPIFilterStatsService;


@Controller
@RequestMapping("/admin")
public class FilterKPIController {

    
    @Autowired private IKPIFilterStatsService kpiFilterService;
    @Autowired private ICRUDCategoryService categoryService;
    
    
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
    
    
}
