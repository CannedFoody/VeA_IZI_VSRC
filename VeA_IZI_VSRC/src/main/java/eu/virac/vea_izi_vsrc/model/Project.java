package eu.virac.vea_izi_vsrc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Table
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name = "idProject")
    private long idProject;

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
