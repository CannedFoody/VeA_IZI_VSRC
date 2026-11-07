package eu.virac.vea_izi_vsrc.controller;

import eu.virac.vea_izi_vsrc.model.KPI;
import eu.virac.vea_izi_vsrc.service.impl.CRUDKPIServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/kpi")
public class KPIStatisticsController {

    @Autowired
    private CRUDKPIServiceImpl kpi_service;

    @GetMapping("/test")
    public ArrayList<Integer> get_years(){

        ArrayList<Integer> year_list = new ArrayList<>();
        ArrayList<KPI> kpi_list = new ArrayList<>();

        try{
            kpi_list = kpi_service.getAllKPIs();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        for(KPI kpi : kpi_list){
            year_list.add(kpi.getCreationDate().getYear());
        }

        return year_list;
    }
}
