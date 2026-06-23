package eu.virac.vea_izi_vsrc.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.virac.vea_izi_vsrc.model.Category;
import eu.virac.vea_izi_vsrc.model.KPI;
import eu.virac.vea_izi_vsrc.model.User;
import eu.virac.vea_izi_vsrc.model.Enums.KPIStatus;
import eu.virac.vea_izi_vsrc.repo.IKPIRepo;
import eu.virac.vea_izi_vsrc.service.ICRUDKPIService;

@Service
public class CRUDKPIServiceImpl implements ICRUDKPIService {

	@Autowired
	private IKPIRepo kpiRepo;
	
	@Override
	public KPI createNewKPI(KPI kpi) throws Exception {
		if (kpi == null) {
			throw new Exception("Passed KPI object is null...");
		}
		if (kpiRepo.existsById(kpi.getIdKPI())) {
			throw new Exception("Category with that ID already exists...");
		}
		if (kpi.getCreationDate() == null || kpi.getTitle().isEmpty() || kpi.getDescription().isEmpty() 
				|| kpi.getStatus() == null || kpi.getCategory() == null || kpi.getCreator() == null || kpi.getOverlooker() == null) {
			throw new Exception("One or more of the KPI fields are empty...");
		}
		
		return kpiRepo.save(kpi);
	}

	@Override
	public ArrayList<KPI> getAllKPIs() throws Exception {
		if (kpiRepo.count() == 0) {
			throw new Exception("KPI repo is empty...");
		}
		
		ArrayList<KPI> returned_list = (ArrayList<KPI>) kpiRepo.findAll();
		
		return returned_list;
	}

	@Override
	public KPI getKPIById(long idKPI) throws Exception {
		if (idKPI < 1) {
			throw new Exception("Invalid KPI ID passed...");
		}
		if (kpiRepo.count() == 0) {
			throw new Exception("KPI repo is empty...");
		}
		if (!kpiRepo.existsById(idKPI)) {
			throw new Exception("No KPI exists by that ID...");
		}
		
		return kpiRepo.findById(idKPI).get();
	}

	@Override
	public KPI updateKPI(long idKPI, LocalDate creationDate, String title, String description, KPIStatus status,
			Category category, User creator, User overlooker) throws Exception {
		if (idKPI < 1) {
			throw new Exception("Invalid KPI ID passed...");
		}
		if (!kpiRepo.existsById(idKPI)) {
			throw new Exception("No category exists by that ID...");
		}
		if (creationDate == null || title == null || description == null || status == null || category == null 
				|| creator == null || overlooker == null) {
			throw new Exception("Incorrect KPI input data...");
		}
		
		KPI kpi_to_update = kpiRepo.findById(idKPI).get();
		
		if(!kpi_to_update.getCreationDate().equals(creationDate)){
			kpi_to_update.setCreationDate(creationDate);
        }
		if (!kpi_to_update.getTitle().equals(title)) {
			kpi_to_update.setTitle(title);
		}
		if (!kpi_to_update.getDescription().equals(description)) {
			kpi_to_update.setDescription(description);
		}
		if (!kpi_to_update.getStatus().equals(status)) {
			kpi_to_update.setStatus(status);
		}
		if (!kpi_to_update.getCategory().equals(category)) {
			kpi_to_update.setCategory(category);
		}
		if (!kpi_to_update.getCreator().equals(creator)) {
			kpi_to_update.setCreator(creator);
		}
		if (!kpi_to_update.getOverlooker().equals(overlooker)) {
			kpi_to_update.setOverlooker(overlooker);
		}
		
		return kpiRepo.save(kpi_to_update);
	}

	@Override
	public void deleteKPI(long idKPI) throws Exception {
		if (idKPI < 1) {
			throw new Exception("Invalid KPI ID passed...");
		}
		
		if (kpiRepo.existsById(idKPI)) {
			kpiRepo.deleteById(idKPI);
		}
		else{
            throw new Exception("No KPI exists by that ID...");
        }
	}

}
