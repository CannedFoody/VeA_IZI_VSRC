package eu.virac.vea_izi_vsrc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import eu.virac.vea_izi_vsrc.model.*;
import eu.virac.vea_izi_vsrc.model.fileConvert.ExcelRow;
import eu.virac.vea_izi_vsrc.service.*;
import eu.virac.vea_izi_vsrc.service.fileConvert.IFileService;
import jakarta.servlet.http.HttpServletResponse;

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
    public void exportExcelAll(@PathVariable String entity, HttpServletResponse response) throws Exception {

        List<ExcelRow> rows = convertEntityToRows(entity);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + entity + ".xlsx");

        fileService.exportExcel(rows, response.getOutputStream());
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
    public void exportExcelSingle(@PathVariable String entity, @PathVariable long id, HttpServletResponse response) throws Exception {

        List<ExcelRow> rows = convertSingleEntityToRow(entity, id);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + entity + "_" + id + ".xlsx");

        fileService.exportExcel(rows, response.getOutputStream());
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
    public void exportWordAll(@PathVariable String entity, HttpServletResponse response) throws Exception {

        List<ExcelRow> rows = convertEntityToRows(entity);

        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=" + entity + ".docx");

        fileService.exportWord(rows, response.getOutputStream());
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
    public void exportWordSingle(@PathVariable String entity, @PathVariable long id, HttpServletResponse response) throws Exception {

        List<ExcelRow> rows = convertSingleEntityToRow(entity, id);

        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=" + entity + "_" + id + ".docx");

        fileService.exportWord(rows, response.getOutputStream());
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
    public String importExcelAll(@PathVariable String entity, @RequestParam MultipartFile file) throws Exception {

        List<ExcelRow> rows = fileService.importExcel(file.getInputStream());
        saveRowsToEntity(entity, rows);

        return "redirect:/admin/" + entity + "/show/all";
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
    public String importExcelSingle(@PathVariable String entity, @PathVariable long id, @RequestParam MultipartFile file) throws Exception {

        List<ExcelRow> rows = fileService.importExcel(file.getInputStream());
        saveRowsToEntity(entity, rows);

        return "redirect:/admin/" + entity + "/show/all";
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
    public String importWordAll(@PathVariable String entity, @RequestParam MultipartFile file) throws Exception {

        List<ExcelRow> rows = fileService.importWord(file.getInputStream());
        saveRowsToEntity(entity, rows);

        return "redirect:/admin/" + entity + "/show/all";
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
    public String importWordSingle(@PathVariable String entity, @PathVariable long id, @RequestParam MultipartFile file) throws Exception {

        List<ExcelRow> rows = fileService.importWord(file.getInputStream());
        saveRowsToEntity(entity, rows);

        return "redirect:/admin/" + entity + "/show/all";
    }

    
    
    
    
    //---------------------------------------------------------------
    // URL HELPERS 
    //---------------------------------------------------------------
    
    
    // SINGLE ENTITY -> ROW
    // for:
    // .../admin/{entity}/show/{id}/export/excel
    // .../admin/{entity}/show/{id}/export/word
    private List<ExcelRow> convertSingleEntityToRow(String entity, long id) throws Exception {

        List<ExcelRow> rows = new ArrayList<>();
        ExcelRow r = new ExcelRow();

        switch (entity) {

            case "category":
                Category c = categoryService.getCategoryById(id);
                r.setEntityId(c.getIdCategory());
                r.setCol1(c.getName());
                r.setCol2(c.getDescription());
                break;

            case "subcategory":
                SubCategory s = subCategoryService.getSubCategoryById(id);
                r.setEntityId(s.getIdSubcategory());
                r.setCol1(s.getTitle());
                r.setCol2(s.getDescription());
                break;

            case "department":
                Department d = departmentService.getDepartmentById(id);
                r.setEntityId(d.getIdDepartment());
                r.setCol1(d.getName());
                r.setCol2(d.getDescription());
                r.setCol3(d.getGoals());
                break;

            case "departmenthead":
                DepartmentHead dh = departmentHeadService.getDepartmentHeadById(id);
                r.setEntityId(dh.getDepartmentHeadId());
                r.setCol1(dh.getUser().getName());
                r.setCol2(dh.getDepartment().getName());
                r.setCol3(String.valueOf(dh.getStartingDate()));
                r.setCol4(String.valueOf(dh.getEndingDate()));
                break;

            case "kpi":
                KPI k = kpiService.getKPIById(id);
                r.setEntityId(k.getIdKPI());
                r.setCol1(k.getTitle());
                r.setCol2(k.getDescription());
                r.setCol3(k.getStatus().name());
                break;

            case "project":
                Project p = projectService.getProjectById(id);
                r.setEntityId(p.getIdProject());
                r.setCol1(p.getTitle());
                r.setCol2(p.getDescription());
                break;

            case "task":
                Task t = taskService.getTaskById(id);
                r.setEntityId(t.getIdTask());
                r.setCol1(t.getTitle());
                r.setCol2(t.getDescription());
                r.setCol3(t.getStatus().name());
                break;

            case "user":
                User u = userService.getUserById(id);
                r.setEntityId(u.getIdUser());
                r.setCol1(u.getName());
                r.setCol2(u.getEmail());
                r.setCol3(u.getDepartment().getName());
                break;
        }

        rows.add(r);
        return rows;
    }


    // ENTITY -> ROWS (ALL)
	// for:
	// .../admin/{entity}/show/all/export/excel
	// .../admin/{entity}/show/all/export/word
    private List<ExcelRow> convertEntityToRows(String entity) throws Exception {

        List<ExcelRow> rows = new ArrayList<>();

        switch (entity) {

            case "category":
                for (Category c : categoryService.getAllCategories()) {
                    ExcelRow r = new ExcelRow();
                    r.setEntityId(c.getIdCategory());
                    r.setCol1(c.getName());
                    r.setCol2(c.getDescription());
                    rows.add(r);
                }
                break;

            case "subcategory":
                for (SubCategory s : subCategoryService.getAllSubCategories()) {
                    ExcelRow r = new ExcelRow();
                    r.setEntityId(s.getIdSubcategory());
                    r.setCol1(s.getTitle());
                    r.setCol2(s.getDescription());
                    rows.add(r);
                }
                break;

            case "department":
                for (Department d : departmentService.getAllDepartments()) {
                    ExcelRow r = new ExcelRow();
                    r.setEntityId(d.getIdDepartment());
                    r.setCol1(d.getName());
                    r.setCol2(d.getDescription());
                    r.setCol3(d.getGoals());
                    rows.add(r);
                }
                break;

            case "departmenthead":
                for (DepartmentHead dh : departmentHeadService.getAllDepartmentHeads()) {
                    ExcelRow r = new ExcelRow();
                    r.setEntityId(dh.getDepartmentHeadId());
                    r.setCol1(dh.getUser().getName());
                    r.setCol2(dh.getDepartment().getName());
                    r.setCol3(String.valueOf(dh.getStartingDate()));
                    r.setCol4(String.valueOf(dh.getEndingDate()));
                    rows.add(r);
                }
                break;

            case "kpi":
                for (KPI k : kpiService.getAllKPIs()) {
                    ExcelRow r = new ExcelRow();
                    r.setEntityId(k.getIdKPI());
                    r.setCol1(k.getTitle());
                    r.setCol2(k.getDescription());
                    r.setCol3(k.getStatus().name());
                    rows.add(r);
                }
                break;

            case "project":
                for (Project p : projectService.getAllProjects()) {
                    ExcelRow r = new ExcelRow();
                    r.setEntityId(p.getIdProject());
                    r.setCol1(p.getTitle());
                    r.setCol2(p.getDescription());
                    rows.add(r);
                }
                break;

            case "task":
                for (Task t : taskService.getAllTasks()) {
                    ExcelRow r = new ExcelRow();
                    r.setEntityId(t.getIdTask());
                    r.setCol1(t.getTitle());
                    r.setCol2(t.getDescription());
                    r.setCol3(t.getStatus().name());
                    rows.add(r);
                }
                break;

            case "user":
                for (User u : userService.getAllUsers()) {
                    ExcelRow r = new ExcelRow();
                    r.setEntityId(u.getIdUser());
                    r.setCol1(u.getName());
                    r.setCol2(u.getEmail());
                    r.setCol3(u.getDepartment().getName());
                    rows.add(r);
                }
                break;
        }

        return rows;
    }


    // ROWS -> ENTITY (CREATE NEW)
    // for:
	// .../admin/{entity}/show/all/import/excel
	// .../admin/{entity}/show/all/import/word
	// .../admin/{entity}/show/{id}/import/excel
	// .../admin/{entity}/show/{id}/import/word
    private void saveRowsToEntity(String entity, List<ExcelRow> rows) throws Exception {

        switch (entity) {

            case "category":
                for (ExcelRow r : rows) {
                    Category c = new Category();
                    c.setName(r.getCol1());
                    c.setDescription(r.getCol2());
                    categoryService.createNewCategory(c);
                }
                break;

            case "subcategory":
                for (ExcelRow r : rows) {
                    SubCategory s = new SubCategory();
                    s.setTitle(r.getCol1());
                    s.setDescription(r.getCol2());
                    subCategoryService.createNewSubCategory(s);
                }
                break;

            case "department":
                for (ExcelRow r : rows) {
                    Department d = new Department();
                    d.setName(r.getCol1());
                    d.setDescription(r.getCol2());
                    d.setGoals(r.getCol3());
                    departmentService.createNewDepartment(d);
                }
                break;

            case "departmenthead":
                for (ExcelRow r : rows) {
                    DepartmentHead dh = new DepartmentHead();
                    dh.setStartingDate(java.time.LocalDate.parse(r.getCol3()));
                    dh.setEndingDate(java.time.LocalDate.parse(r.getCol4()));
                    dh.setDepartment(departmentService.getDepartmentById(Long.parseLong(r.getCol2())));
                    dh.setUser(userService.getUserById(Long.parseLong(r.getCol1())));
                    departmentHeadService.createNewDepartmentHead(dh);
                }
                break;

            case "kpi":
                for (ExcelRow r : rows) {
                    KPI k = new KPI();
                    k.setTitle(r.getCol1());
                    k.setDescription(r.getCol2());
                    k.setStatus(eu.virac.vea_izi_vsrc.model.Enums.KPIStatus.valueOf(r.getCol3()));
                    kpiService.createNewKPI(k);
                }
                break;

            case "project":
                for (ExcelRow r : rows) {
                    Project p = new Project();
                    p.setTitle(r.getCol1());
                    p.setDescription(r.getCol2());
                    projectService.createNewProject(p);
                }
                break;

            case "task":
                for (ExcelRow r : rows) {
                    Task t = new Task();
                    t.setTitle(r.getCol1());
                    t.setDescription(r.getCol2());
                    t.setStatus(eu.virac.vea_izi_vsrc.model.Enums.TaskStatus.valueOf(r.getCol3()));
                    taskService.createNewTask(t);
                }
                break;

            case "user":
                for (ExcelRow r : rows) {
                    User u = new User();
                    u.setName(r.getCol1());
                    u.setEmail(r.getCol2());
                    u.setDepartment(departmentService.getDepartmentById(
                            Long.parseLong(r.getCol3())
                    ));
                    userService.createNewUser(u);
                }
                break;
        }
    }
}
