package eu.virac.vea_izi_vsrc.service;

import java.time.LocalDate;
import java.util.ArrayList;

import eu.virac.vea_izi_vsrc.model.Category;
import eu.virac.vea_izi_vsrc.model.KPI;
import eu.virac.vea_izi_vsrc.model.User;
import eu.virac.vea_izi_vsrc.model.Enums.KPIStatus;

public interface ICRUDKPIService {

	public abstract KPI createNewKPI(KPI kpi) throws Exception;
	
	public abstract ArrayList<KPI> getAllKPIs() throws Exception;
	
	public abstract KPI getKPIById(long idKPI) throws Exception;
	
	public abstract KPI updateKPI(long idKPI, LocalDate creationDate, LocalDate deadline, String title, String description,
			KPIStatus status, Category category, User creator, User overlooker) throws Exception;
	
	public abstract void deleteKPI(long idKPI) throws Exception;
}
