package eu.virac.vea_izi_vsrc.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import eu.virac.vea_izi_vsrc.model.Enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table
@Entity
@Audited
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name = "idTask")
    private long idTask;
    
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

    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[A-Ža-ž0-9-()\\s]{1,75}$")
    @Column(name = "title")
    private String title;

    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[A-Ža-ž0-9-()\\s]{1,100}$")
    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "idKPI")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KPI kpi;

    @ManyToOne
    @JoinColumn(name = "idSubcategory")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubCategory subCategory;

    public Task(String title, String description, TaskStatus status , KPI kpi, SubCategory subCategory) {
        setTitle(title);
        setDescription(description);
        setStatus(status);
        setKpi(kpi);
        setSubCategory(subCategory);
    }
}
