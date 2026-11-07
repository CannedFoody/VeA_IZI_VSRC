package eu.virac.statistics.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

@Service
public class StatisticsService {
    private final RestClient client = RestClient.create();

    public Dictionary<Integer, Integer> kpis_per_year() {
        Integer[] years = client.get().uri("http://localhost:8080/api/kpi/test").retrieve().body(Integer[].class);

        ArrayList<Integer> year_entries = new ArrayList<>();

        for(int year : years){
            if(!year_entries.contains(year)){
                year_entries.add(year);
            }
        }

//      https://www.geeksforgeeks.org/java/java-util-dictionary-class-java/
        Dictionary<Integer, Integer> kpi_year_count = new Hashtable<>();
        for(int key : year_entries){
            int counter = 0;
            for(int value : years){
                if(value == key){
                    counter += 1;
                }
            }

            kpi_year_count.put(key, counter);
        }

        return kpi_year_count;
    }
}
