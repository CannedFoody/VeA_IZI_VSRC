package eu.virac.vea_izi_vsrc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import eu.virac.vea_izi_vsrc.model.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table
@Entity
@Audited
public class DepartmentHead {

    @Id
    @Column(name = "idDepartmentHead")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long departmentHeadId;
    
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

    @Column(name = "startingDate")
    @NotNull
    private LocalDate startingDate;

    @Column(name = "endingDate")
    private LocalDate endingDate;

    @ManyToOne
    @JoinColumn(name = "idDepartment")
    @ToString.Exclude
    private Department department;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idUser")
    @ToString.Exclude
    private User user;

    public DepartmentHead(Department department, User user, LocalDate startingDate) {
        setDepartment(department);
        setUser(user);
        setStartingDate(startingDate);
    }
}
