package eu.virac.vea_izi_vsrc.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import eu.virac.vea_izi_vsrc.model.*;
import eu.virac.vea_izi_vsrc.model.fileConvert.ExcelRow;
import eu.virac.vea_izi_vsrc.service.*;
import eu.virac.vea_izi_vsrc.service.fileConvert.IFileService;

@Controller
@RequestMapping("/admin")
public class FileController {

    @Autowired private IFileService fileService;
    

    @Autowired private ICRUDCategoryService categoryService;
    @Autowired private ICRUDSubCategoryService subCategoryService;
    @Autowired private ICRUDDepartmentService departmentService;
    @Autowired private ICRUDDepartmentHeadService departmentHeadService;
    @Autowired private ICRUDKPIService kpiService;
    @Autowired private ICRUDProjectService projectService;
    @Autowired private ICRUDTaskService taskService;
    @Autowired private ICRUDUserService userService;


    // EXPORT EXCEL -> ALL
    // URL: .../admin/{entity}/show/all/export/excel
    
    // http://localhost:8080/admin/category/show/all/export/excel    
    // http://localhost:8080/admin/subcategory/show/all/export/excel
    // http://localhost:8080/admin/kpi/show/all/export/excel
    // http://localhost:8080/admin/department/show/all/export/excel
    // http://localhost:8080/admin/departmenthead/show/all/export/excel
    // http://localhost:8080/admin/project/show/all/export/excel
    // http://localhost:8080/admin/task/show/all/export/excel
    // http://localhost:8080/admin/user/show/all/export/excel
    @GetMapping("/{entity}/show/all/export/excel")
    public ResponseEntity<?> exportExcelAll(@PathVariable String entity) {

        try {
            List<ExcelRow> rows = convertEntityToRows(entity);
            byte[] fileBytes = fileService.exportExcel(rows);

            return ResponseEntity
                    .status(HttpStatusCode.valueOf(200))
                    .header("Content-Disposition", "attachment; filename=" + entity + "_all.xlsx")
                    .body(fileBytes);
        }
        catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(409));
        }
    }


    
    // EXPORT EXCEL -> ONE
    // URL: /admin/{entity}/show/{id}/export/excel

    // http://localhost:8080/admin/category/show/1/export/excel
    // http://localhost:8080/admin/subcategory/show/1/export/excel
    // http://localhost:8080/admin/kpi/show/2/export/excel
    // http://localhost:8080/admin/department/show/2/export/excel
    // http://localhost:8080/admin/departmenthead/show/1/export/excel
    // http://localhost:8080/admin/project/show/3/export/excel
    // http://localhost:8080/admin/task/show/2/export/excel
    // http://localhost:8080/admin/user/show/1/export/excel
    @GetMapping("/{entity}/show/{id}/export/excel")
    public ResponseEntity<?> exportExcelSingle(@PathVariable String entity, @PathVariable long id) {

        try {
            List<ExcelRow> rows = convertSingleEntityToRows(entity, id);
            byte[] fileBytes = fileService.exportExcel(rows);

            return ResponseEntity
                    .status(HttpStatusCode.valueOf(200))
                    .header("Content-Disposition", "attachment; filename=" + entity + "_" + id + ".xlsx")
                    .body(fileBytes);
        }
        catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(409));
        }
    }


    
    // EXPORT WORD -> ALL
    // URL: /admin/{entity}/show/all/export/word
    
    // http://localhost:8080/admin/category/show/all/export/word
    // http://localhost:8080/admin/subcategory/show/all/export/word
    // http://localhost:8080/admin/kpi/show/all/export/word
    // http://localhost:8080/admin/department/show/all/export/word
    // http://localhost:8080/admin/departmenthead/show/all/export/word
    // http://localhost:8080/admin/project/show/all/export/word
    // http://localhost:8080/admin/task/show/all/export/word
    // http://localhost:8080/admin/user/show/all/export/word
    @GetMapping("/{entity}/show/all/export/word")
    public ResponseEntity<?> exportWordAll(@PathVariable String entity) {

        try {
            List<ExcelRow> rows = convertEntityToRows(entity);
            byte[] fileBytes = fileService.exportWord(rows);

            return ResponseEntity
                    .status(HttpStatusCode.valueOf(200))
                    .header("Content-Disposition", "attachment; filename=" + entity + "_all.docx")
                    .body(fileBytes);
        }
        catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(409));
        }
    }


    
    // EXPORT WORD -> ONE
    // URL: /admin/{entity}/show/{id}/export/word
    
    // http://localhost:8080/admin/category/show/1/export/word
    // http://localhost:8080/admin/subcategory/show/1/export/word
    // http://localhost:8080/admin/kpi/show/2/export/word
    // http://localhost:8080/admin/department/show/1/export/word
    // http://localhost:8080/admin/departmenthead/show/1/export/word
    // http://localhost:8080/admin/project/show/3/export/word
    // http://localhost:8080/admin/task/show/2/export/word
    // http://localhost:8080/admin/user/show/2/export/word
    @GetMapping("/{entity}/show/{id}/export/word")
    public ResponseEntity<?> exportWordSingle(@PathVariable String entity, @PathVariable long id) {

        try {
            List<ExcelRow> rows = convertSingleEntityToRows(entity, id);
            byte[] fileBytes = fileService.exportWord(rows);

            return ResponseEntity
                    .status(HttpStatusCode.valueOf(200))
                    .header("Content-Disposition", "attachment; filename=" + entity + "_" + id + ".docx")
                    .body(fileBytes);
        }
        catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(409));
        }
    }


    
    // IMPORT EXCEL -> ALL
    // URL: /admin/{entity}/show/all/import/excel
    
    // http://localhost:8080/admin/category/show/all/import/excel
    // http://localhost:8080/admin/subcategory/show/all/import/excel
    // http://localhost:8080/admin/kpi/show/all/import/excel
    // http://localhost:8080/admin/department/show/all/import/excel
    // http://localhost:8080/admin/departmenthead/show/all/import/excel
    // http://localhost:8080/admin/project/show/all/import/excel
    // http://localhost:8080/admin/task/show/all/import/excel
    // http://localhost:8080/admin/user/show/all/import/excel
    @PostMapping("/{entity}/show/all/import/excel")
    public ResponseEntity<?> importExcelAll(@PathVariable String entity, @RequestParam MultipartFile file) {

        try {
            List<ExcelRow> rows = fileService.importExcel(file.getBytes());
            saveRowsToEntity(entity, rows);

            return new ResponseEntity<String>("Excel import successful", HttpStatusCode.valueOf(200));
        }
        catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(409));
        }
    }


    
    // IMPORT EXCEL -> ONE 
    // URL: /admin/{entity}/show/{id}/import/excel
    
    // http://localhost:8080/admin/category/show/2/import/excel
    // http://localhost:8080/admin/category/show/1/import/excel
    // http://localhost:8080/admin/kpi/show/3/import/excel
    // http://localhost:8080/admin/department/show/1/import/excel
    // http://localhost:8080/admin/departmenthead/show/1/import/excel
    // http://localhost:8080/admin/project/show/2/import/excel
    // http://localhost:8080/admin/task/show/2/import/excel
    // http://localhost:8080/admin/user/show/1/import/excel
    @PostMapping("/{entity}/show/{id}/import/excel")
    public ResponseEntity<?> importExcelSingle(@PathVariable String entity, 
    		@PathVariable long id, @RequestParam MultipartFile file) {

        try {
            List<ExcelRow> rows = fileService.importExcel(file.getBytes());
            saveRowsToEntity(entity, rows);

            return new ResponseEntity<String>("Excel import successful", HttpStatusCode.valueOf(200));
        }
        catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(409));
        }
    }


    // IMPORT WORD -> ALL 
    // URL: /admin/{entity}/show/all/import/word
    
    // http://localhost:8080/admin/category/show/all/import/word
    // http://localhost:8080/admin/subcategory/show/all/import/word
    // http://localhost:8080/admin/kpi/show/all/import/word
    // http://localhost:8080/admin/department/show/all/import/word
    // http://localhost:8080/admin/departmenthead/show/all/import/word
    // http://localhost:8080/admin/project/show/all/import/word
    // http://localhost:8080/admin/task/show/all/import/word
    // http://localhost:8080/admin/user/show/all/import/word
    @PostMapping("/{entity}/show/all/import/word")
    public ResponseEntity<?> importWordAll(@PathVariable String entity, @RequestParam MultipartFile file) {
        try {
            List<ExcelRow> rows = fileService.importWord(file.getBytes());
            saveRowsToEntity(entity, rows);

            return new ResponseEntity<String>("Word import successful", HttpStatusCode.valueOf(200));
        }
        catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(409));
        }
    }


    // IMPORT WORD -> ONE 
    // URL: /admin/{entity}/show/{id}/import/word
    
    // http://localhost:8080/admin/category/show/1/import/word
    // http://localhost:8080/admin/subcategory/show/1/import/word
    // http://localhost:8080/admin/kpi/show/1/import/word
    // http://localhost:8080/admin/department/2/all/import/word
    // http://localhost:8080/admin/departmenthead/show/2/import/word
    // http://localhost:8080/admin/project/show/1/import/word
    // http://localhost:8080/admin/task/show/1/import/word
    // http://localhost:8080/admin/user/show/3/import/word
    @PostMapping("/{entity}/show/{id}/import/word")
    public ResponseEntity<?> importWordSingle(@PathVariable String entity,  
    		@PathVariable long id, @RequestParam MultipartFile file) {

        try {
            List<ExcelRow> rows = fileService.importWord(file.getBytes());
            saveRowsToEntity(entity, rows);

            return new ResponseEntity<String>("Word import successful", HttpStatusCode.valueOf(200));
        }
        catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(409));
        }
    }

    
    
    
    
    //---------------------------------------------------------------
    // URL HELPERS 
    //---------------------------------------------------------------
    
    
    // SINGLE ENTITY -> ROW
    // for:
    // .../admin/{entity}/show/{id}/export/excel
    // .../admin/{entity}/show/{id}/export/word
    private List<ExcelRow> convertSingleEntityToRows(String entity, long id) throws Exception {

        List<ExcelRow> rows = new ArrayList<>();

        switch (entity) {

            case "user":
                User u = userService.getUserById(id);
                rows.add(new ExcelRow("user", u.getIdUser(), "Name", u.getName()));
                rows.add(new ExcelRow("user", u.getIdUser(), "Surname", u.getSurname()));
                rows.add(new ExcelRow("user", u.getIdUser(), "Email", u.getEmail()));
                rows.add(new ExcelRow("user", u.getIdUser(), "Role", u.getRole()));
                rows.add(new ExcelRow("user", u.getIdUser(), "Department",
                        u.getDepartment() == null ? "" : String.valueOf(u.getDepartment().getIdDepartment())));
                break;

            case "category":
                Category c = categoryService.getCategoryById(id);
                rows.add(new ExcelRow("category", c.getIdCategory(), "Name", c.getName()));
                rows.add(new ExcelRow("category", c.getIdCategory(), "Description", c.getDescription()));
                break;

            case "subcategory":
                SubCategory s = subCategoryService.getSubCategoryById(id);
                rows.add(new ExcelRow("subcategory", s.getIdSubcategory(), "Title", s.getTitle()));
                rows.add(new ExcelRow("subcategory", s.getIdSubcategory(), "Description", s.getDescription()));
                break;

            case "department":
                Department d = departmentService.getDepartmentById(id);
                rows.add(new ExcelRow("department", d.getIdDepartment(), "Name", d.getName()));
                rows.add(new ExcelRow("department", d.getIdDepartment(), "Description", d.getDescription()));
                rows.add(new ExcelRow("department", d.getIdDepartment(), "Goals", d.getGoals()));
                break;

            case "departmenthead":
                DepartmentHead dh = departmentHeadService.getDepartmentHeadById(id);
                rows.add(new ExcelRow("departmenthead", dh.getDepartmentHeadId(), "Starting Date", dh.getStartingDate().toString()));
                rows.add(new ExcelRow("departmenthead", dh.getDepartmentHeadId(), "Ending Date", dh.getEndingDate().toString()));
                rows.add(new ExcelRow("departmenthead", dh.getDepartmentHeadId(), "Department",
                        dh.getDepartment() == null ? "" : String.valueOf(dh.getDepartment().getIdDepartment())));
                rows.add(new ExcelRow("departmenthead", dh.getDepartmentHeadId(), "User",
                        dh.getUser() == null ? "" : String.valueOf(dh.getUser().getIdUser())));
                break;

            case "kpi":
                KPI k = kpiService.getKPIById(id);
                rows.add(new ExcelRow("kpi", k.getIdKPI(), "Title", k.getTitle()));
                rows.add(new ExcelRow("kpi", k.getIdKPI(), "Description", k.getDescription()));
                rows.add(new ExcelRow("kpi", k.getIdKPI(), "Status", k.getStatus().name()));
                break;

            case "project":
                Project p = projectService.getProjectById(id);
                rows.add(new ExcelRow("project", p.getIdProject(), "Title", p.getTitle()));
                rows.add(new ExcelRow("project", p.getIdProject(), "Description", p.getDescription()));
                break;

            case "task":
                Task t = taskService.getTaskById(id);
                rows.add(new ExcelRow("task", t.getIdTask(), "Title", t.getTitle()));
                rows.add(new ExcelRow("task", t.getIdTask(), "Description", t.getDescription()));
                rows.add(new ExcelRow("task", t.getIdTask(), "Status", t.getStatus().name()));
                break;

            default:
                throw new Exception("Unknown entity: " + entity);
        }

        return rows;
    }



    // ENTITY -> ROWS (ALL)
	// for:
	// .../admin/{entity}/show/all/export/excel
	// .../admin/{entity}/show/all/export/word
    private List<ExcelRow> convertEntityToRows(String entity) throws Exception {

        List<ExcelRow> rows = new ArrayList<>();

        switch (entity) {

            // USER
            case "user":
                for (User u : userService.getAllUsers()) {
                    rows.add(new ExcelRow("user", u.getIdUser(), "Name", u.getName()));
                    rows.add(new ExcelRow("user", u.getIdUser(), "Surname", u.getSurname()));
                    rows.add(new ExcelRow("user", u.getIdUser(), "Email", u.getEmail()));
                    rows.add(new ExcelRow("user", u.getIdUser(), "Role", u.getRole()));
                    rows.add(new ExcelRow("user", u.getIdUser(), "Department",
                            u.getDepartment() == null ? "" : String.valueOf(u.getDepartment().getIdDepartment())));
                }
                break;


                
            // CATEGORY
            case "category":
                for (Category c : categoryService.getAllCategories()) {
                    rows.add(new ExcelRow("category", c.getIdCategory(), "Name", c.getName()));
                    rows.add(new ExcelRow("category", c.getIdCategory(), "Description", c.getDescription()));
                }
                break;


            // SUBCATEGORY
            case "subcategory":
                for (SubCategory s : subCategoryService.getAllSubCategories()) {
                    rows.add(new ExcelRow("subcategory", s.getIdSubcategory(), "Title", s.getTitle()));
                    rows.add(new ExcelRow("subcategory", s.getIdSubcategory(), "Description", s.getDescription()));
                }
                break;


                
            // DEPARTMENT
            case "department":
                for (Department d : departmentService.getAllDepartments()) {
                    rows.add(new ExcelRow("department", d.getIdDepartment(), "Name", d.getName()));
                    rows.add(new ExcelRow("department", d.getIdDepartment(), "Description", d.getDescription()));
                    rows.add(new ExcelRow("department", d.getIdDepartment(), "Goals", d.getGoals()));
                }
                break;


                
            // DEPARTMENT HEAD
            case "departmenthead":
                for (DepartmentHead dh : departmentHeadService.getAllDepartmentHeads()) {
                    rows.add(new ExcelRow("departmenthead", dh.getDepartmentHeadId(), "Starting Date", dh.getStartingDate().toString()));
                    rows.add(new ExcelRow("departmenthead", dh.getDepartmentHeadId(), "Ending Date", dh.getEndingDate().toString()));
                    rows.add(new ExcelRow("departmenthead", dh.getDepartmentHeadId(), "Department",
                            dh.getDepartment() == null ? "" : String.valueOf(dh.getDepartment().getIdDepartment())));
                    rows.add(new ExcelRow("departmenthead", dh.getDepartmentHeadId(), "User",
                            dh.getUser() == null ? "" : String.valueOf(dh.getUser().getIdUser())));
                }
                break;


                
            // KPI
            case "kpi":
                for (KPI k : kpiService.getAllKPIs()) {
                    rows.add(new ExcelRow("kpi", k.getIdKPI(), "Title", k.getTitle()));
                    rows.add(new ExcelRow("kpi", k.getIdKPI(), "Description", k.getDescription()));
                    rows.add(new ExcelRow("kpi", k.getIdKPI(), "Status", k.getStatus().name()));
                }
                break;


            // PROJECT
            case "project":
                for (Project p : projectService.getAllProjects()) {
                    rows.add(new ExcelRow("project", p.getIdProject(), "Title", p.getTitle()));
                    rows.add(new ExcelRow("project", p.getIdProject(), "Description", p.getDescription()));
                }
                break;


                
            // TASK
            case "task":
                for (Task t : taskService.getAllTasks()) {
                    rows.add(new ExcelRow("task", t.getIdTask(), "Title", t.getTitle()));
                    rows.add(new ExcelRow("task", t.getIdTask(), "Description", t.getDescription()));
                    rows.add(new ExcelRow("task", t.getIdTask(), "Status", t.getStatus().name()));
                }
                break;

            default:
                throw new Exception("Unknown entity: " + entity);
                
                
        }

        return rows;
    }



    // ROWS -> ENTITY (CREATE NEW)
    // for:
	// .../admin/{entity}/show/all/import/excel
	// .../admin/{entity}/show/all/import/word
	// .../admin/{entity}/show/{id}/import/excel
	// .../admin/{entity}/show/{id}/import/word
    
    // Converts imported ExcelRow list into real entity objects and saves them.
    // Rows are grouped by entityId so each object receives all its fields before saving.
    
	// Converts imported ExcelRow list into real entity objects and saves them.
	// ID is ignored because database generates it automatically.
	private void saveRowsToEntity(String entity, List<ExcelRow> rows) throws Exception {
	
		if (rows == null || rows.isEmpty()) {
			throw new Exception("File contains no rows. Cannot import.");
	    }
	
	    // Group rows by entityId, but DO NOT use entityId for saving.
	    // Each group represents one new object to create.
	    Map<Long, List<ExcelRow>> grouped = new LinkedHashMap<>();
	    for (ExcelRow r : rows) {
	        grouped.computeIfAbsent(r.getEntityId(), k -> new ArrayList<>()).add(r);
	    }
	    
	    
	
	    switch (entity) {


			// CATEGORY
			case "category":
	            for (var entry : grouped.entrySet()) {
	
	                Category c = new Category(); // ID auto-generated
	
	                for (ExcelRow r : entry.getValue()) {
	
	                    if (r.getColumnName().equals("Name"))
	                    	c.setName(r.getColumnValue());
	
	                    if (r.getColumnName().equals("Description"))
	                        c.setDescription(r.getColumnValue());
	                }
	
	                categoryService.createNewCategory(c);
	            }
	            break;
	


	        // SUBCATEGORY
	        case "subcategory":
	            for (var entry : grouped.entrySet()) {
	
	                SubCategory s = new SubCategory(); // ID auto-generated
	
	                for (ExcelRow r : entry.getValue()) {
	
	                    if (r.getColumnName().equals("Title"))
	                    	s.setTitle(r.getColumnValue());
	
	                    if (r.getColumnName().equals("Description"))
	                        s.setDescription(r.getColumnValue());
	                }
	
	                subCategoryService.createNewSubCategory(s);
	            }
	            break;
	


	            
	        // DEPARTMENT
	        case "department":
	            for (var entry : grouped.entrySet()) {
	
	                Department d = new Department(); // ID auto-generated
	
	                for (ExcelRow r : entry.getValue()) {
	
	                    if (r.getColumnName().equals("Name"))
	                        d.setName(r.getColumnValue());
	
	                    if (r.getColumnName().equals("Description"))
	                        d.setDescription(r.getColumnValue());
	
	                    if (r.getColumnName().equals("Goals"))
	                        d.setGoals(r.getColumnValue());
	                }
	
	                departmentService.createNewDepartment(d);
	            }
	            break;
	
	            
	            
	            
            // DEPARTMENT HEAD
	        case "departmenthead":
	        	for (var entry : grouped.entrySet()) {
	
	                DepartmentHead dh = new DepartmentHead(); // ID auto-generated
	
	                for (ExcelRow r : entry.getValue()) {
	
	                    if (r.getColumnName().equals("Starting Date"))
	                        dh.setStartingDate(LocalDate.parse(r.getColumnValue()));
	
	                    if (r.getColumnName().equals("Ending Date"))
	                        dh.setEndingDate(LocalDate.parse(r.getColumnValue()));
	
	                    if (r.getColumnName().equals("Department")) {
	                        long depId = Long.parseLong(r.getColumnValue());
	                        dh.setDepartment(departmentService.getDepartmentById(depId));
	                    }
	
	                    if (r.getColumnName().equals("User")) {
	                        long userId = Long.parseLong(r.getColumnValue());
	                        dh.setUser(userService.getUserById(userId));
	                     }
	                 }
	
	                 departmentHeadService.createNewDepartmentHead(dh);
	             }
	             break;
	
	             
	
	        // KPI
	        case "kpi":
	            for (var entry : grouped.entrySet()) {
	
	                KPI k = new KPI(); // ID auto-generated
	
	                for (ExcelRow r : entry.getValue()) {
	
	                    if (r.getColumnName().equals("Title"))
	                        k.setTitle(r.getColumnValue());
	
	                    if (r.getColumnName().equals("Description"))
	                        k.setDescription(r.getColumnValue());
	
	                    if (r.getColumnName().equals("Status"))
	                        k.setStatus(eu.virac.vea_izi_vsrc.model.Enums.KPIStatus.valueOf(r.getColumnValue()));
	                }
	
	                kpiService.createNewKPI(k);
	            }
	            break;
	


	            
	        // PROJECT
	        case "project":
	            for (var entry : grouped.entrySet()) {
	
	                Project p = new Project(); // ID auto-generated
	
	                for (ExcelRow r : entry.getValue()) {
	
	                    if (r.getColumnName().equals("Title"))
	                        p.setTitle(r.getColumnValue());
	
	                    if (r.getColumnName().equals("Description"))
	                        p.setDescription(r.getColumnValue());
	                }
	
	                projectService.createNewProject(p);
	            }
	            break;
	


	            
	        // TASK
	        case "task":
	            for (var entry : grouped.entrySet()) {
	
	                Task t = new Task(); // ID auto-generated
	
	                for (ExcelRow r : entry.getValue()) {
	
	                    if (r.getColumnName().equals("Title"))
	                        t.setTitle(r.getColumnValue());
	
	                    if (r.getColumnName().equals("Description"))
	                        t.setDescription(r.getColumnValue());
	
	                    if (r.getColumnName().equals("Status"))
	                        t.setStatus(eu.virac.vea_izi_vsrc.model.Enums.TaskStatus.valueOf(r.getColumnValue()));
	                }
	
	                taskService.createNewTask(t);
	            }
	            break;
	


	            
	        // USER
	        case "user":
	            for (var entry : grouped.entrySet()) {
	
	                User u = new User(); // ID auto-generated
	
	                for (ExcelRow r : entry.getValue()) {
	
	                    if (r.getColumnName().equals("Name"))
	                        u.setName(r.getColumnValue());
	
	                    if (r.getColumnName().equals("Surname"))
	                        u.setSurname(r.getColumnValue());
	
	                    if (r.getColumnName().equals("Email"))
	                        u.setEmail(r.getColumnValue());
	
	                    if (r.getColumnName().equals("Role"))
	                        u.setRole(r.getColumnValue());
	
	                    if (r.getColumnName().equals("Department")) {
	                        long depId = Long.parseLong(r.getColumnValue());
	                        u.setDepartment(departmentService.getDepartmentById(depId));
	                    }
	                }
	
	                userService.createNewUser(u);
	            }
	            break;
	
	
	        default:
	            throw new Exception("Unknown entity type: " + entity);
	    }
	}



    
}
