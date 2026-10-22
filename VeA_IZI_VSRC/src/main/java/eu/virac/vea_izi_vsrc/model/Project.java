package eu.virac.vea_izi_vsrc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@ToString
@NoArgsConstructor
@Table
@Entity
@Audited
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name = "idProject")
    private long idProject;
    
	@Column(name="created_date", nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime createdDate;
	
	@Column(name="last_modified_date")
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-Ža-ž0-9-()\\s]{1,100}$")
    @Column(name = "title")
    private String title;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-Ža-ž0-9-()\\s]{1,200}$")
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "idKPI")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KPI kpi;

    public Project(String title, String description) {
        setTitle(title);
        setDescription(description);
    }
}
