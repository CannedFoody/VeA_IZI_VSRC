package eu.virac.vea_izi_vsrc.service.impl;

import eu.virac.vea_izi_vsrc.model.Department;
import eu.virac.vea_izi_vsrc.model.Project;
import eu.virac.vea_izi_vsrc.repo.IProjectRepo;
import eu.virac.vea_izi_vsrc.service.ICRUDProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CRUDProjectServiceImpl implements ICRUDProjectService {

    @Autowired
    private IProjectRepo project_repo;

    @Override
    public Project createNewProject(Project project) throws Exception {
        if(project == null){
            throw new Exception("Passed project is null...");
        }

        if(project_repo.existsById(project.getIdProject())){
            throw new Exception("Project with that ID already exists...");
        }

        if(project.getTitle().isEmpty() || project.getDescription().isEmpty()){
            throw new Exception("Project title or description field is empty...");
        }

        return  project_repo.save(project);
    }

    @Override
    public ArrayList<Project> getAllProjects() throws Exception {
        if(project_repo.count() == 0){
            throw new Exception("Project repo is empty...");
        }

        ArrayList<Project> returned_list = (ArrayList<Project>) project_repo.findAll();

        return returned_list;
    }

    @Override
    public Project getProjectById(long idProject) throws Exception {
        if (idProject < 1) {
            throw new Exception("Invalid project ID passed...");
        }

        if(project_repo.count() == 0){
            throw new Exception("Project repo is empty...");
        }

        if(!project_repo.existsById(idProject)){
            throw new Exception("No Project exists by that ID...");
        }

        return project_repo.findById(idProject).get();
    }

    @Override
    public Project updateProject(long idProject, String title, String description) throws Exception {
        if(!project_repo.existsById(idProject)){
            throw new Exception("No Project with that ID exists...");
        }

        if(idProject < 1){
            throw new Exception("Incorrect Project ID passed...");
        }

        if(title == null || description == null){
            throw new Exception("Incorrect Project input data...");
        }

        Project project_to_update = project_repo.findById(idProject).get();

        if(!project_to_update.getTitle().equals(title)){
            project_to_update.setTitle(title);
        }

        if(!project_to_update.getDescription().equals(description)){
            project_to_update.setDescription(description);
        }

        return project_repo.save(project_to_update);
    }

    @Override
    public void deleteProject(long idProject) throws Exception {
        if(idProject < 1){
            throw new Exception("Invalid Project ID passed...");
        }

        if(project_repo.existsById(idProject)){
            project_repo.deleteById(idProject);
        }
        else{
            throw new Exception("No Project exists by that ID...");
        }
    }
}
