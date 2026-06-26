package eu.virac.vea_izi_vsrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import eu.virac.vea_izi_vsrc.model.User;
import eu.virac.vea_izi_vsrc.service.ICRUDUserService;
import eu.virac.vea_izi_vsrc.service.IFilterUserService;

@Controller
@RequestMapping("/User")
public class UserController {

    @Autowired
    private ICRUDUserService userService;

    @Autowired
    private IFilterUserService filterUserService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.createNewUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable long id,
            @RequestBody User user) {
        try {
            return ResponseEntity.ok(
                    userService.updateUser(
                            id,
                            user.getName(),
                            user.getSurname(),
                            user.getEmail(),
                            user.getRole(),
                            user.getDepartment()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/filter/role")
    public ResponseEntity<?> filterUsersByRole(@RequestParam String role) {
        try {
            return ResponseEntity.ok(
                    filterUserService.filterUsersByRole(role)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/filter/department/{departmentId}")
    public ResponseEntity<?> filterUsersByDepartment(
            @PathVariable long departmentId) {
        try {
            return ResponseEntity.ok(
                    filterUserService.filterUsersByDepartmentId(departmentId)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}