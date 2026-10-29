package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.virac.vea_izi_vsrc.model.SubCategory;
import eu.virac.vea_izi_vsrc.service.ICRUDSubCategoryService;

@Controller
@RequestMapping("/admin")
public class CRUDAdminControllerSubCategory {

	@Autowired private ICRUDSubCategoryService subCategoryService;
	
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


}
