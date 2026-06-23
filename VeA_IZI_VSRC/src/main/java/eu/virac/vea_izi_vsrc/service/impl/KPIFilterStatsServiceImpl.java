package eu.virac.vea_izi_vsrc.service.impl;

import eu.virac.vea_izi_vsrc.model.Category;
import eu.virac.vea_izi_vsrc.model.Enums.KPIStatus;
import eu.virac.vea_izi_vsrc.model.KPI;
import eu.virac.vea_izi_vsrc.repo.ICategoryRepo;
import eu.virac.vea_izi_vsrc.repo.IKPIRepo;
import eu.virac.vea_izi_vsrc.service.IKPIFilterStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class KPIFilterStatsServiceImpl implements IKPIFilterStatsService {
    @Autowired
    private IKPIRepo kpi_repo;

    @Autowired
    private ICategoryRepo category_repo;

    @Override
    public ArrayList<KPI> filterByUploadDateAfter(LocalDate date) throws Exception {
        if(date == null){
            throw new Exception("Passed date is null...");
        }

        if(date.isAfter(LocalDate.now()) || date.isBefore(LocalDate.of(2000, 1, 1))){
            throw new Exception("Invalid date passed...");
        }

        if(kpi_repo.count() == 0){
            throw new Exception("No KPI objects exist to filter...");
        }

        ArrayList<KPI> filtered_list = kpi_repo.findByCreationDateAfter(date);

        if(filtered_list.isEmpty()){
            throw new Exception("No KPI were found that were created after the passed date...");
        }
        else{
            return filtered_list;
        }
    }



    @Override
    public ArrayList<KPI> filterByKeyword(String keyword) throws Exception {
        if(keyword.isEmpty()){
            throw new Exception("Passed keyword is empty...");
        }

        if(kpi_repo.count() == 0){
            throw new Exception("No KPI objects exist to filter...");
        }

        ArrayList<KPI> filtered_list = kpi_repo.findByTitleOrDescriptionContaining(keyword, keyword);

        if(filtered_list.isEmpty()){
            throw new Exception("No KPI titles or descriptions were found matching the passed keyword...");
        }
        else {
            return filtered_list;
        }
    }

    @Override
    public ArrayList<KPI> filterByStatus(KPIStatus status) throws Exception {
        if(status == null){
            throw new Exception("Passed status is null...");
        }

        if(!Arrays.asList(KPIStatus.values()).contains(status)){
            throw new Exception("Passed status does not match a status in the system...");
        }

        if(kpi_repo.count() == 0){
            throw new Exception("No KPI objects exist to filter...");
        }

        ArrayList<KPI> filtered_list = kpi_repo.findByStatusIs(status);

        if(filtered_list.isEmpty()){
            throw new Exception("No KPI were found with that status...");
        }
        else{
            return filtered_list;
        }
    }

    @Override
    public ArrayList<KPI> filterByAfterDeadline() throws Exception {
        if(kpi_repo.count() == 0){
            throw new Exception("No KPI objects exist to filter...");
        }

        ArrayList<KPI> filtered_list = kpi_repo.findByDeadlineBefore(LocalDate.now());
        if(filtered_list.isEmpty()){
            throw new Exception("No KPI were found that have exceeded the deadline date...");
        }
        else{
            return filtered_list;
        }
    }

    @Override
    public ArrayList<KPI> filterByCategory(Category category) throws Exception {
        if(category == null){
            throw new Exception("Passed Category is null");
        }

        if(!category_repo.existsById(category.getIdCategory())){
            throw new Exception("Passed category does not exist in the system...");
        }

        if(kpi_repo.count() == 0){
            throw new Exception("No KPIs to filter...");
        }

        ArrayList<KPI> filtered_list = kpi_repo.findByCategoryIs(category);

        if(filtered_list.isEmpty()){
            throw new Exception("No KPIs found with that category...");
        }
        else{
            return filtered_list;
        }
    }


}
