package eu.virac.vea_izi_vsrc.repo;

import eu.virac.vea_izi_vsrc.model.Category;
import eu.virac.vea_izi_vsrc.model.Enums.KPIStatus;
import eu.virac.vea_izi_vsrc.model.KPI;
import eu.virac.vea_izi_vsrc.model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IKPIRepo extends CrudRepository<KPI, Long> {
    ArrayList<KPI> findByCreationDateAfter(LocalDate date);

    ArrayList<KPI> findByTitleOrDescriptionContaining(String keyword, String keyword1);

    ArrayList<KPI> findByStatusIs(KPIStatus status);

    ArrayList<KPI> findByDeadlineBefore(LocalDate now);

    ArrayList<KPI> findByCategoryIs(Category category);

    ArrayList<KPI> findByCreator(User user);
}
