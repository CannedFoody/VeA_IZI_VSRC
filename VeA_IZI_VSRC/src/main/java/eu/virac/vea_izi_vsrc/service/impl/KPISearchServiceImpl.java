package eu.virac.vea_izi_vsrc.service.impl;

import eu.virac.vea_izi_vsrc.model.KPI;
import eu.virac.vea_izi_vsrc.model.KPISearch;
import eu.virac.vea_izi_vsrc.repo.IKPIRepo;
import eu.virac.vea_izi_vsrc.service.IKPISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class KPISearchServiceImpl implements IKPISearchService {
    @Autowired
    private IKPIRepo kpi_repo;

    @Override
    public ArrayList<KPI> search(KPISearch search_params) throws Exception {
        if(kpi_repo.count() == 0){
            throw new Exception("No KPI's exist...");
        }

        if(search_params == null){
            throw new Exception("No search parameters were passed...");
        }

        ArrayList<KPI> search_result = kpi_repo.search(search_params.getKeyword(), search_params.getCreator(), search_params.getOverlooker(), search_params.getCategory(), search_params.getCreation_date(), search_params.getDeadline());

        if(search_result.isEmpty()){
            throw new Exception("No KPI's were found with these search parameters...");
        }
        else{
            return search_result;
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> ABirzniece_develop
