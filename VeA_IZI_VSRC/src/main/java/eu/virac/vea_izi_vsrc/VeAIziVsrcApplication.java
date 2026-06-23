package eu.virac.vea_izi_vsrc;

import eu.virac.vea_izi_vsrc.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import eu.virac.vea_izi_vsrc.model.*;

import java.util.Arrays;

@SpringBootApplication
public class VeAIziVsrcApplication {

    public static void main(String[] args) {
        SpringApplication.run(VeAIziVsrcApplication.class, args);
    }

    @Bean
    public CommandLineRunner saveDataInDB(ICategoryRepo category_repo, IDepartmentRepo department_repo, IUserRepo user_repo,
                                          IDepartmentHeadRepo department_head_repo, IKPIRepo kpi_repo, ISubCategoryRepo sub_category_repo,
                                          ITaskRepo task_repo, IProjectRepo project_repo) {
        return new CommandLineRunner() {

            @Override
            public void run(String... args) throws Exception {
//                Title, Desc
                SubCategory sc1 = new SubCategory("", "");
                SubCategory sc2 = new SubCategory("", "");
                SubCategory sc3 = new SubCategory("", "");

                sub_category_repo.saveAll(Arrays.asList(sc1, sc2, sc3));

//                Name, Desc, Goals
                Department d1 = new Department("", "", "");
                Department d2 = new Department("", "", "");
                Department d3 = new Department("", "", "");

                department_repo.saveAll(Arrays.asList(d1, d2, d3));

//                Department heads

//                Name, Surname, email, role, department
                User u1 = new User("", "", "", "Department Head", d1);
                User u2 = new User("", "", "", "Department Head", d2);
                User u3 = new User("", "", "", "Department Head", d3);

//                Regular workers

//                Name, Surname, email, role, department
                User u4 = new User("", "", "", "Worker", d1);
                User u5 = new User("", "", "", "Worker", d2);
                User u6 = new User("", "", "", "Worker", d3);


                user_repo.saveAll(Arrays.asList(u1, u2, u3));

//                Department, User, starting_date
                DepartmentHead dh1 = new DepartmentHead(d1, u1, null);
                DepartmentHead dh2 = new DepartmentHead(d2, u2, null);
                DepartmentHead dh3 = new DepartmentHead(d3, u3, null);

                department_head_repo.saveAll(Arrays.asList(dh1, dh2, dh3));

//                Name, Desc
                Category c1 = new Category("", "");
                Category c2 = new Category("", "");
                Category c3 = new Category("", "");

                category_repo.saveAll(Arrays.asList(c1, c2, c3));

//                Creation date, Title, Desc, Category, Creator, Overlooker
                KPI k1 = new KPI(null, "", "", c1, u4, u1);
                KPI k2 = new KPI(null, "", "", c2, u5, u2);
                KPI k3 = new KPI(null, "", "", c3, u6, u3);

                kpi_repo.saveAll(Arrays.asList(k1, k2, k3));

//                Title, Desc, status (enum), kpi, subcategory
                Task t1 = new Task("", "", null, k1, sc1);
                Task t2 = new Task("", "", null, k2, sc2);
                Task t3 = new Task("", "", null, k3, sc3);

                task_repo.saveAll(Arrays.asList(t1, t2, t3));

//                Title, Desc
//                Linking a project with KPI's will happen when the db is up and only in-site.
                Project p1 = new Project("", "");
                Project p2 = new Project("", "");
                Project p3 = new Project("", "");

                project_repo.saveAll(Arrays.asList(p1, p2, p3));
            }
        };
    }
}
