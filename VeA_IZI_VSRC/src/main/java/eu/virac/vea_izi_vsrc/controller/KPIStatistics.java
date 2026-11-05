package eu.virac.vea_izi_vsrc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/kpi")
public class KPIStatistics {

    @GetMapping("/test")
    public List<Integer> get_years(){
        return List.of(2026, 2027, 2028);
    }
}
