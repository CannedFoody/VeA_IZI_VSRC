package eu.virac.vea_izi_vsrc.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.virac.vea_izi_vsrc.service.IDepartmentHeadFilterService;


@Controller
@RequestMapping("/admin")
public class FilterDepartmentHeadController {


    @Autowired private IDepartmentHeadFilterService departmentHeadFilterService;
    
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
    
    
    // FILTER department heads that are active
    // localhost:8080/admin/departmenthead/filter/active
    @GetMapping("/departmenthead/filter/active")
    public String filterActiveDepartmentHeads(Model model) {

        try {
            model.addAttribute("package", departmentHeadFilterService.filterActiveDepartmentHeads());
            return "admin-departmenthead-show-all-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }
    
    
	// FILTER active Department Heads who started after a given date
	// localhost:8080/admin/departmenthead/filter/active/after/2025-01-01
	@GetMapping("/departmenthead/filter/active/after/{date}")
	public String filterActiveDepartmentHeadsByStartDate(@PathVariable(name = "date") LocalDate date, Model model) {
	
	try {	
	    model.addAttribute("package", departmentHeadFilterService.filterActiveByStartingDateAfter(date));
	    return "admin-departmenthead-show-all-page";
	}
	catch (Exception e) {
	    model.addAttribute("package", e.getMessage());
	    return "error-page";
	    }
	}
    
	
	
}
