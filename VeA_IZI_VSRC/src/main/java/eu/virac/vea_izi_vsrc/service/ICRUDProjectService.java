package eu.virac.vea_izi_vsrc.service;

import eu.virac.vea_izi_vsrc.model.Project;

import java.util.ArrayList;

public interface ICRUDProjectService{
    public abstract Project createNewProject(Project category) throws Exception;

    public abstract ArrayList<Project> getAllProjects() throws Exception;

    public abstract Project getProjectById(long idProject) throws Exception;

    public abstract Project updateProject(long idProject, String title, String description) throws Exception;

    public abstract void deleteProject(long idProject) throws Exception;
}
