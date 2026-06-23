package eu.virac.vea_izi_vsrc.service;

import eu.virac.vea_izi_vsrc.model.Enums.TaskStatus;
import eu.virac.vea_izi_vsrc.model.KPI;
import eu.virac.vea_izi_vsrc.model.SubCategory;
import eu.virac.vea_izi_vsrc.model.Task;

import java.util.ArrayList;

public interface ICRUDTaskService{
    public abstract Task createNewTask(Task task) throws Exception;

    public abstract ArrayList<Task> getAllTasks() throws Exception;

    public abstract Task getTaskById(long idTask) throws Exception;

    public abstract Task updateTask(long idTask, String title, String description, TaskStatus status, KPI kpi, SubCategory sub_category) throws Exception;

    public abstract void deleteTask(long idTask) throws Exception;
}
