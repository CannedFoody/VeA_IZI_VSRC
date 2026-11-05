package eu.virac.statistics.controller;

import eu.virac.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Dictionary;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired private StatisticsService stat_service;

    @GetMapping("/kpi-per-year")
    public Dictionary<Integer, Integer> kpi_per_year(){
        return stat_service.kpis_per_year();
    }
}
