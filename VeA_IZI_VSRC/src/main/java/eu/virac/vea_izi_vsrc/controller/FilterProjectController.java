package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import eu.virac.vea_izi_vsrc.service.IFilterProjectService;

public class FilterProjectController {

    @Autowired private IFilterProjectService projectFilterService;
    
    
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
    
    
}
