package eu.virac.vea_izi_vsrc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import eu.virac.vea_izi_vsrc.model.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Table
@Entity
public class DepartmentHead {

    @Id
    @Column(name = "idDepartmentHead")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long departmentHeadId;

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
