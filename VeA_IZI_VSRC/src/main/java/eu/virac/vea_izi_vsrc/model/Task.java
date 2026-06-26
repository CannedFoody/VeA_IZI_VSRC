package eu.virac.vea_izi_vsrc.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import eu.virac.vea_izi_vsrc.model.Enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Table
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name = "idTask")
    private long idTask;

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

    @OneToOne
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
