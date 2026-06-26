package eu.virac.vea_izi_vsrc.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import eu.virac.vea_izi_vsrc.model.Department;
import eu.virac.vea_izi_vsrc.model.DepartmentHead;
import eu.virac.vea_izi_vsrc.service.ICRUDDepartmentHeadService;
import eu.virac.vea_izi_vsrc.service.ICRUDDepartmentService;
import eu.virac.vea_izi_vsrc.service.IDepartmentHeadFilterService;

@Controller
@RequestMapping("/DepartmentHead")
public class DepartmentHeadController {

    @Autowired
    private ICRUDDepartmentHeadService departmentHeadService;

    @Autowired
    private IDepartmentHeadFilterService departmentHeadFilterService;

    @Autowired
    private ICRUDDepartmentService departmentService;

    @PostMapping("/create")
    public ResponseEntity<?> createDepartmentHead(@RequestBody DepartmentHead departmentHead) {
        try {
            return ResponseEntity.ok(departmentHeadService.createNewDepartmentHead(departmentHead));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDepartmentHeads() {
        try {
            return ResponseEntity.ok(departmentHeadService.getAllDepartmentHeads());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentHeadById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(departmentHeadService.getDepartmentHeadById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDepartmentHead(
            @PathVariable long id,
            @RequestBody DepartmentHead departmentHead) {
        try {
            return ResponseEntity.ok(
                    departmentHeadService.updateDepartmentHead(
                            id,
                            departmentHead.getStartingDate(),
                            departmentHead.getEndingDate(),
                            departmentHead.getDepartment(),
                            departmentHead.getUser()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDepartmentHead(@PathVariable long id) {
        try {
            departmentHeadService.deleteDepartmentHead(id);
            return ResponseEntity.ok("Department head deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/filter/starting-date-after")
    public ResponseEntity<?> filterByStartingDateAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            return ResponseEntity.ok(departmentHeadFilterService.filterByStartingDateAfter(date));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/filter/department/{departmentId}")
    public ResponseEntity<?> filterByDepartment(@PathVariable long departmentId) {
        try {
            Department department = departmentService.getDepartmentById(departmentId);
            return ResponseEntity.ok(departmentHeadFilterService.filterByDepartment(department));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}