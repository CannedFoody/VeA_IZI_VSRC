package eu.virac.vea_izi_vsrc.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import eu.virac.vea_izi_vsrc.service.IDepartmentHeadFilterService;

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
    
    
}
