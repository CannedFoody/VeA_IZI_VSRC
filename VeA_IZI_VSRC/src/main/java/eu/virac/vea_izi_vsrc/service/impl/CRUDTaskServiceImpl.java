package eu.virac.vea_izi_vsrc.service.impl;

import eu.virac.vea_izi_vsrc.model.Task;
import eu.virac.vea_izi_vsrc.model.Enums.TaskStatus;
import eu.virac.vea_izi_vsrc.model.KPI;
import eu.virac.vea_izi_vsrc.model.SubCategory;
import eu.virac.vea_izi_vsrc.model.Task;
import eu.virac.vea_izi_vsrc.repo.IKPIRepo;
import eu.virac.vea_izi_vsrc.repo.ISubCategoryRepo;
import eu.virac.vea_izi_vsrc.repo.ITaskRepo;
import eu.virac.vea_izi_vsrc.service.ICRUDTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class CRUDTaskServiceImpl implements ICRUDTaskService {

    @Autowired
    private ITaskRepo task_repo;

    @Autowired
    private IKPIRepo kpi_repo;

    @Autowired
    private ISubCategoryRepo sub_cat_repo;

    @Override
    public Task createNewTask(Task task) throws Exception {
        if(task == null){
            throw new Exception("Passed task object is null...");
        }

        if(task_repo.existsById(task.getIdTask())){
            throw new Exception("Task with that ID already exists...");
        }

        if(task.getTitle().isEmpty() || task.getDescription().isEmpty() || task.getStatus() == null || task.getKpi() == null || task.getSubCategory() == null){
            throw new Exception("One of the task fields was passed as empty or null...");
        }

        return task_repo.save(task);
    }

    @Override
    public ArrayList<Task> getAllTasks() throws Exception {
        if(task_repo.count() == 0){
            throw new Exception("Task repo is empty...");
        }

        ArrayList<Task> returned_list = (ArrayList<Task>) task_repo.findAll();

        return returned_list;
    }

    @Override
    public Task getTaskById(long idTask) throws Exception {
        if (idTask < 1) {
            throw new Exception("Invalid Task ID passed...");
        }

        if(task_repo.count() == 0){
            throw new Exception("Task repo is empty...");
        }

        if(!task_repo.existsById(idTask)){
            throw new Exception("No Task exists by that ID...");
        }

        return task_repo.findById(idTask).get();
    }

    @Override
    public Task updateTask(long idTask, String title, String description, TaskStatus status, KPI kpi, SubCategory sub_category) throws Exception {
        if(!task_repo.existsById(idTask)){
            throw new Exception("No department with that ID exists...");
        }

        if(idTask < 1){
            throw new Exception("Incorrect department ID passed...");
        }

        if(title.isEmpty() || description.isEmpty() || status == null || kpi == null || sub_category == null){
            throw new Exception("One of the task fields was passed as empty or null...");
        }

        if(!kpi_repo.existsById(kpi.getIdKPI()) || !sub_cat_repo.existsById(sub_category.getIdSubcategory()) || !Arrays.asList(TaskStatus.values()).contains(status)){
            throw new Exception("Passed KPI or Sub Category or TaskStatus does not exist in the system...");
        }

        Task task_to_update = task_repo.findById(idTask).get();

        if(!task_to_update.getTitle().equals(title)){
            task_to_update.setTitle(title);
        }

        if(!task_to_update.getDescription().equals(description)){
            task_to_update.setDescription(description);
        }

        if(task_to_update.getStatus() != status){
            task_to_update.setStatus(status);
        }

        if(task_to_update.getKpi() != kpi){
            task_to_update.setKpi(kpi);
        }

        if(task_to_update.getSubCategory() != sub_category){
            task_to_update.setSubCategory(sub_category);
        }

        return task_repo.save(task_to_update);
    }

    @Override
    public void deleteTask(long idTask) throws Exception {
        if(idTask < 1){
            throw new Exception("Invalid Task ID passed...");
        }

        if(task_repo.existsById(idTask)){
            task_repo.deleteById(idTask);
        }
        else{
            throw new Exception("No Task exists by that ID...");
        }
    }
}
