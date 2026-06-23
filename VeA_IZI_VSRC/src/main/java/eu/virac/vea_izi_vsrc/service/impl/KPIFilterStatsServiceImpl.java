package eu.virac.vea_izi_vsrc.service.impl;

import eu.virac.vea_izi_vsrc.model.Enums.KPIStatus;
import eu.virac.vea_izi_vsrc.model.KPI;
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

    @Override
    public ArrayList<KPI> filterByDateAfter(LocalDate date) throws Exception {
        if(date == null){
            throw new Exception("Passed date is null...");
        }

        if(date.isAfter(LocalDate.now()) || date.isBefore(LocalDate.of(2000, 1, 1))){
            throw new Exception("Invalid date passed...");
        }

        if(kpi_repo.count() == 0){
            throw new Exception("No KPI objects exist to filter...");
        }

        ArrayList<KPI> filtered_list = kpi_repo.findKPIBycreationDateAfter(date);

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

        ArrayList<KPI> filtered_list = kpi_repo.findBytitleOrdescriptionContaining(keyword, keyword);

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

        ArrayList<KPI> filtered_list = kpi_repo.findBystatusIs(status);

        if(filtered_list.isEmpty()){
            throw new Exception("No KPI were found with that status...");
        }
        else{
            return filtered_list;
        }
    }


}
