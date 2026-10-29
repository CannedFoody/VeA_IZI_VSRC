package eu.virac.vea_izi_vsrc.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import eu.virac.vea_izi_vsrc.event.KpiCreatedEvent;
import eu.virac.vea_izi_vsrc.kafka.NotificationEventProducer;
import eu.virac.vea_izi_vsrc.model.Category;
import eu.virac.vea_izi_vsrc.model.DepartmentHead;
import eu.virac.vea_izi_vsrc.model.KPI;
import eu.virac.vea_izi_vsrc.model.User;
import eu.virac.vea_izi_vsrc.model.Enums.KPIStatus;
import eu.virac.vea_izi_vsrc.model.security.SecUser;
import eu.virac.vea_izi_vsrc.repo.IDepartmentHeadRepo;
import eu.virac.vea_izi_vsrc.repo.IKPIRepo;
import eu.virac.vea_izi_vsrc.repo.IUserRepo;
import eu.virac.vea_izi_vsrc.repo.security.ISecUserRepo;
import eu.virac.vea_izi_vsrc.service.ICRUDKPIService;

@Service
public class CRUDKPIServiceImpl implements ICRUDKPIService {

	@Autowired
	private IKPIRepo kpiRepo;

	@Autowired
	private IUserRepo userRepo;

	@Autowired
	private NotificationEventProducer producer;
	
	@Autowired
	private ISecUserRepo secUserRepo;
	
	@Autowired
	private IDepartmentHeadRepo departmentHeadRepo;

	@Override
	public KPI createNewKPI(KPI kpi, String username) throws Exception {
		if (kpi == null) {
			throw new Exception("Passed KPI object is null...");
		}
		
		SecUser login = secUserRepo.findByUsername(username);
		User creator = login.getUser();
		if (creator == null) {
			throw new Exception("Login account not linked to a user...");
		}
		kpi.setCreator(creator);
		
		if (creator.getDepartment() != null) {
			DepartmentHead overlooker = departmentHeadRepo.findFirstByDepartmentAndEndingDateIsNull(creator.getDepartment());
			if (overlooker == null) {
				throw new Exception("Department has no current head...");
			}
			kpi.setOverlooker(overlooker.getUser());
		}

		if(kpi.getCreationDate().isAfter(kpi.getDeadline())){
			throw new Exception("Creation date cannot be after the deadline...");
		}

		if(kpi.getCreationDate().isBefore(LocalDate.of(LocalDate.now().getYear() - 1, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()))){
			throw new Exception("Creation date cannot be younger than a year...");
		}

		if(kpi.getDeadline().isAfter(LocalDate.of(LocalDate.now().getYear() + 5, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()))){
			throw new Exception("Deadline cannot be older than 5 years...");
		}
		
		if (kpi.getCreationDate() == null || kpi.getDeadline() == null || kpi.getTitle() == null
				|| kpi.getTitle().isEmpty() || kpi.getDescription() == null || kpi.getDescription().isEmpty()
				|| kpi.getStatus() == null || kpi.getCategory() == null || kpi.getCreator() == null
				|| kpi.getOverlooker() == null) {
			throw new Exception("One or more of the KPI fields are empty...");
		}
		if (!kpi.getTitle().matches("^[A-Ža-ž0-9-()\\\\s]{1,100}$") || !kpi.getDescription().matches("^[A-Ža-ž0-9-()\\\\s]{1,200}$")) {
			throw new Exception("Invalid title or description...");
		}
		if (kpi.getDeadline().isBefore(kpi.getCreationDate())) {
			throw new Exception("Deadline cannot be before the creation date...");
		}

		// return kpiRepo.save(kpi);

		KPI saved = kpiRepo.save(kpi);

		// The overlooker from the form only has its ID, so load the full user for the email
		userRepo.findById(saved.getOverlooker().getIdUser())
				.ifPresent(overlooker -> producer.publishKpiCreated(new KpiCreatedEvent(saved.getIdKPI(),
						saved.getTitle(), saved.getDescription(), String.valueOf(saved.getDeadline()),
						overlooker.getEmail(), overlooker.getName() + " " + overlooker.getSurname())));
		return saved;
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
		if (kpiRepo.count() == 0) {
			throw new Exception("KPI repo is empty...");
		}

		if (idKPI < 1) {
			throw new Exception("Invalid KPI ID passed...");
		}

		if (!kpiRepo.existsById(idKPI)) {
			throw new Exception("No KPI exists by that ID...");
		}

		return kpiRepo.findById(idKPI).get();
	}

	@Override
	public KPI updateKPI(long idKPI, LocalDate creationDate, LocalDate deadline, String title, String description,
			KPIStatus status, Category category, User creator, User overlooker) throws Exception {
		if (kpiRepo.count() == 0) {
			throw new Exception("No KPI's to update...");
		}
		if (idKPI < 1) {
			throw new Exception("Invalid KPI ID passed...");
		}
		if (!kpiRepo.existsById(idKPI)) {
			throw new Exception("No category exists by that ID...");
		}
		if (creationDate == null || deadline == null || title == null || title.isEmpty() || description == null
				|| description.isEmpty() || status == null || category == null || creator == null || overlooker == null) {
			throw new Exception("Incorrect KPI input data...");
		}
		if (creationDate.isAfter(deadline)) {
			throw new Exception("Creation date cannot be after the deadline...");
		}

		if (creationDate.getYear() < getKPIById(idKPI).getCreationDate().getYear() - 1) {
			throw new Exception("Creation date cannot be more than a year earlier than the current date...");
		}

		if (deadline.getYear() < getKPIById(idKPI).getDeadline().getYear() + 1) {
			throw new Exception("Deadline cannot be older than a year than the current deadline...");
		}

		if (!title.matches("^[A-Ža-ž0-9-()\\\\s]{1,100}$") || !description.matches("^[A-Ža-ž0-9-()\\\\s]{1,200}$")) {
			throw new Exception("Invalid title or description...");
		}

		KPI kpi_to_update = kpiRepo.findById(idKPI).get();
		
		Authentication authentication =
		        SecurityContextHolder.getContext().getAuthentication();
		
		boolean isAdmin = authentication != null
		        && authentication.getAuthorities().stream()
		                .anyMatch(authority ->
		                        authority.getAuthority().equals("ADMIN"));
		
		if (!isAdmin
		        && "HIGH".equals(status.getHiearchy())
		        && "LOW".equals(kpi_to_update.getStatus().getHiearchy())) {
		    throw new Exception(
		            "Can't go from a lower hierarchy to a higher one.");
		}

		if (!kpi_to_update.getCreationDate().equals(creationDate)) {
			kpi_to_update.setCreationDate(creationDate);
		}

		if (!kpi_to_update.getDeadline().equals(deadline)) {
			kpi_to_update.setDeadline(deadline);
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
		if(kpiRepo.count() == 0){
			throw new Exception("No KPI's to delete...");
		}

		if (idKPI < 1) {
			throw new Exception("Invalid KPI ID passed...");
		}

		if (kpiRepo.existsById(idKPI)) {
			kpiRepo.deleteById(idKPI);
		} else {
			throw new Exception("No KPI exists by that ID...");
		}
	}

}
