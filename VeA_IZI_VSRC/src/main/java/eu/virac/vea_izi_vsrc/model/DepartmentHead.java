package eu.virac.vea_izi_vsrc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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
    private LocalDate starting_date;

    @Column(name = "endingDate")
    private LocalDate ending_date;

    @ManyToOne
    @JoinColumn(name = "idDepartment")
    @ToString.Exclude
    private Department department;

    @ManyToOne
    @JoinColumn(name = "idUser")
    @ToString.Exclude
    private User user;

    public DepartmentHead(Department department, User user, LocalDate starting_date) {
        setDepartment(department);
        setUser(user);
        setStarting_date(starting_date);
    }
}
