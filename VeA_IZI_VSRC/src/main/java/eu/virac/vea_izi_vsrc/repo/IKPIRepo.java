package eu.virac.vea_izi_vsrc.repo;

import eu.virac.vea_izi_vsrc.model.Enums.KPIStatus;
import eu.virac.vea_izi_vsrc.model.KPI;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IKPIRepo extends CrudRepository<KPI, Long> {
    ArrayList<KPI> findKPIBycreationDateAfter(LocalDate date);

    ArrayList<KPI> findBytitleOrdescriptionContaining(String keyword, String keyword1);

    ArrayList<KPI> findBystatusIs(KPIStatus status);
}
