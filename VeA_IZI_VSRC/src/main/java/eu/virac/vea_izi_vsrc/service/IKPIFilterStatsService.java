package eu.virac.vea_izi_vsrc.service;

import eu.virac.vea_izi_vsrc.model.Category;
import eu.virac.vea_izi_vsrc.model.Enums.KPIStatus;
import eu.virac.vea_izi_vsrc.model.KPI;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IKPIFilterStatsService{
    public abstract ArrayList<KPI> filterByUploadDateAfter(LocalDate date) throws Exception;

    public abstract ArrayList<KPI> filterByKeyword(String keyword) throws Exception;

    public abstract ArrayList<KPI> filterByStatus(KPIStatus status) throws Exception;

    public abstract ArrayList<KPI> filterByAfterDeadline() throws Exception;

    public abstract ArrayList<KPI> filterByCategory(Category category) throws Exception;

    public abstract ArrayList<KPI> filterByCreatorId(long creator_id) throws Exception;
    
}
