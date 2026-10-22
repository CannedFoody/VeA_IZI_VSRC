package eu.virac.vea_izi_vsrc.model;

import java.time.LocalDateTime;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RevisionEntity;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Audited
@Table
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name = "idCategory")
    private Long idCategory;
    
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

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-Ža-ž0-9-()\\s]{1,50}$")
    @Column(name = "name")
    private String name;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-Ža-ž0-9-()\\s]{1,100}$")
    @Column(name = "description")
    private String description;

    public Category(String name, String description){
        setName(name);
        setDescription(description);
    }
}