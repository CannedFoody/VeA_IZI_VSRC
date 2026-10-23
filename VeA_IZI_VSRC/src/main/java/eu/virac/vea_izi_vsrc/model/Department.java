package eu.virac.vea_izi_vsrc.model;

import java.time.LocalDateTime;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Department")
@Entity
@Audited
public class Department {
	
	@Column(name = "IdDepartment")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private long idDepartment;
	
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    
    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String lastModifiedBy;
	
	@Column(name="created_date", nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime createdDate;
	
	@Column(name="last_modified_date")
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	
	@Column(name = "Name", unique = true)
	@NotNull
	@NotEmpty
	@Pattern(regexp = "[A-Ž]{1}[A-Ža-ž0-9 ]{3,40}")
	private String name;
	
	@Column(name = "Description", unique = true)
	@NotNull
	@NotEmpty
	@Pattern(regexp = "[A-Ža-ž0-9 ]{3,50}")
	private String description;
	
	@Column(name = "Goals", unique = true)
	@NotNull
	@NotEmpty
	@Pattern(regexp = "[A-Ža-ž0-9]{3,50}")
	private String goals;
	
	public Department(String name, String description, String goals) {
		setName(name);
		setDescription(description);
		setGoals(goals);
	}
}
