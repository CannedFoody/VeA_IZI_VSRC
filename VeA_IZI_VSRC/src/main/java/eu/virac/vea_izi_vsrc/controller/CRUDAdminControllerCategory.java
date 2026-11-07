package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.virac.vea_izi_vsrc.model.Category;
import eu.virac.vea_izi_vsrc.service.ICRUDCategoryService;

@Controller
@RequestMapping("/admin")
public class CRUDAdminControllerCategory {


    @Autowired private ICRUDCategoryService categoryService;
    
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
            model.addAttribute("category",category);
            model.addAttribute("errorMsg",e.getMessage());
            return "admin-category-add-page";
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
            model.addAttribute("category", category);
            model.addAttribute("errorMsg", e.getMessage());
            return "admin-category-update-page";
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
}
