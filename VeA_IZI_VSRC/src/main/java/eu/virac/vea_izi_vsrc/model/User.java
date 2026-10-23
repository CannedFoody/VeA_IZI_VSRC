package eu.virac.vea_izi_vsrc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)	
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table
@Entity
@Audited
public class User {
	
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
	
    @Column(name="idUser")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value=AccessLevel.NONE) 
    private long idUser;

    @Column(name="Name")
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[A-Ž]{1}[a-ž]{2,20}([ ]{1}([A-Ž]{1}[a-ž]{2,20}))?")
    private String name;

    @Column(name="Surname")
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[A-Ž]{1}[a-ž]{2,20}([ ]{1}([A-Ž]{1}[a-ž]{2,20}))?")
    private String surname;

    @Column(name="Email")
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$")
    private String email;

    @Column(name="Role")
    @NotNull
    @NotEmpty
//    @Pattern(regexp = "[A-Ž]{1}[a-ž]{2,20}([ ]{1}([A-Ž]{1}[a-ž]{2,20}))?")
    @Pattern(regexp = "^(Worker|DepartmentHead|Admin)$")
    private String role;


    @ManyToOne
    @JoinColumn(name = "idDepartment")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Department department;


    public User(String name, String surname, String email, String role, Department department){
        setName(name);
        setSurname(surname);
        setEmail(email);
        setRole(role);
        setDepartment(department);
    }
}

