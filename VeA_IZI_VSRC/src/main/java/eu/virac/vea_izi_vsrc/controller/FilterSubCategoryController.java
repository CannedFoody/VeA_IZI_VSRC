package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eu.virac.vea_izi_vsrc.model.KPISearch;
import eu.virac.vea_izi_vsrc.service.ICRUDCategoryService;
import eu.virac.vea_izi_vsrc.service.ICRUDSubCategoryService;
import eu.virac.vea_izi_vsrc.service.ICRUDUserService;
import eu.virac.vea_izi_vsrc.service.IKPISearchService;
import eu.virac.vea_izi_vsrc.service.ISubCategoryFilterService;

@Controller
@RequestMapping("/admin")
public class FilterSubCategoryController {

	@Autowired private ISubCategoryFilterService subCategoryFilterService;
	@Autowired private IKPISearchService kpiSearchService;
    @Autowired private ICRUDUserService userService;
    @Autowired private ICRUDCategoryService categoryService;
	
	// -------- SubCategory search --------

    // SEARCH subcategories by keyword in title (form page)
    // localhost:8080/admin/subcategory/search
    // localhost:8080/admin/subcategory/search?keyword=SubC
    @GetMapping("/subcategory/search")
    public String searchSubCategories(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        try {
            boolean searched = (keyword != null && !keyword.isBlank());
            model.addAttribute("keyword", keyword);
            model.addAttribute("searched", searched);

            if (searched) {
                model.addAttribute("subcategories", subCategoryFilterService.filterByKeyword(keyword.trim()));
            }
            return "subcategory-search-page";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/kpi/search")
    public String searchKpi(Model model){
        try{
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("search_params", new KPISearch());

            return "kpi-search";
        }
        catch (Exception e){
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/kpi/search")
    public String searchKpi(KPISearch search_params, Model model){
        try{
            model.addAttribute("search", kpiSearchService.search(search_params));
            return "kpi-search-results";
        }
        catch (Exception e){
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    
    
}
