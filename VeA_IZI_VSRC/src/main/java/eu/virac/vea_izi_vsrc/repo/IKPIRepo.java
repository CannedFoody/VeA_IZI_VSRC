package eu.virac.vea_izi_vsrc.repo;

import eu.virac.vea_izi_vsrc.model.Category;
import eu.virac.vea_izi_vsrc.model.Enums.KPIStatus;
import eu.virac.vea_izi_vsrc.model.KPI;
import eu.virac.vea_izi_vsrc.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IKPIRepo extends CrudRepository<KPI, Long> {
    ArrayList<KPI> findByCreationDateAfter(LocalDate date);

    ArrayList<KPI> findByTitleOrDescriptionContaining(String keyword, String keyword1);

    ArrayList<KPI> findByStatusIs(KPIStatus status);

    ArrayList<KPI> findByDeadlineBefore(LocalDate now);

    ArrayList<KPI> findByCategoryIs(Category category);

    ArrayList<KPI> findByCreator(User user);

    @Query("""
        SELECT k FROM KPI k
        WHERE (:keyword IS NULL
               OR LOWER(k.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(k.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
          AND (:creator IS NULL OR k.creator = :creator)
          AND (:overlooker IS NULL OR k.overlooker = :overlooker)
          AND (:category IS NULL OR k.category = :category)
          AND (:creationDate IS NULL OR k.creationDate >= :creationDate)
          AND (:deadline IS NULL OR k.deadline <= :deadline)
    """)
    ArrayList<KPI> search(@Param("keyword") String keyword,
                          @Param("creator") User creator,
                          @Param("overlooker") User overlooker,
                          @Param("category") Category category,
                          @Param("creationDate") LocalDate creationDate,
                          @Param("deadline") LocalDate deadline);
}
