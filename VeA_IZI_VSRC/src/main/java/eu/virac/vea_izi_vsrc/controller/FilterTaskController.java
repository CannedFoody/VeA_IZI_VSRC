package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eu.virac.vea_izi_vsrc.model.Enums.TaskStatus;
import eu.virac.vea_izi_vsrc.service.ICRUDKPIService;
import eu.virac.vea_izi_vsrc.service.ITaskFilterService;

@Controller
@RequestMapping("/admin")
public class FilterTaskController {

	
	@Autowired private ITaskFilterService taskFilterService;
    @Autowired private ICRUDKPIService kpiService;
	
	// -------- Task filters --------

    // FILTER tasks by status and/or KPI (form page with dropdowns)
    // localhost:8080/admin/task/filter
    // localhost:8080/admin/task/filter?status=InProgress
    // localhost:8080/admin/task/filter?status=InProgress&kpiId=1
    @GetMapping("/task/filter")
    public String filterTasks(@RequestParam(name = "status", required = false) TaskStatus status,
                              @RequestParam(name = "kpiId", required = false) Long kpiId,
                              Model model) {
        try {
            model.addAttribute("statuses", TaskStatus.values());
            model.addAttribute("kpis", kpiService.getAllKPIs());
            model.addAttribute("selectedStatus", status);
            model.addAttribute("selectedKpiId", kpiId);

            boolean searched = (status != null || kpiId != null);
            model.addAttribute("searched", searched);

            if (searched) {
                model.addAttribute("tasks", taskFilterService.filterTasks(status, kpiId));
            }
            return "task-filter-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }
    
    
}
