package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.virac.vea_izi_vsrc.model.User;
import eu.virac.vea_izi_vsrc.service.ICRUDDepartmentService;
import eu.virac.vea_izi_vsrc.service.ICRUDUserService;

@Controller
@RequestMapping("/admin")
public class CRUDAdminControllerUser {

    @Autowired private ICRUDUserService userService;
	@Autowired private ICRUDDepartmentService departmentService;
    
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
            model.addAttribute("user", user);

            try {
                model.addAttribute("departments", departmentService.getAllDepartments());
            }
            catch (Exception ignored) {}

            model.addAttribute("errorMsg", e.getMessage());
            return "admin-user-add-page";
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
            model.addAttribute("user",user);

            try {
                model.addAttribute("departments",departmentService.getAllDepartments());
            }
            catch (Exception ignored) {}

            model.addAttribute("errorMsg", e.getMessage());
            return "admin-user-update-page";
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

    
    
}
