package eu.virac.vea_izi_vsrc.model;

import eu.virac.vea_izi_vsrc.model.Enums.KPIStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table
public class KPI {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idKPI")
    @Setter(AccessLevel.NONE)
    private long IdKPI;

    @Column(name = "creationDate")
    @NotNull
    private LocalDate creationDate;

    @Column(name = "title")
    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[A-Ža-ž0-9-()\\s]{1,100}$")
    private String title;

    @Column(name = "description")
    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[A-Ža-ž0-9-()\\s]{1,200}$")
    private String description;

    @Column(name = "status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private KPIStatus status;

    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "creatorId")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "overlookerId")
    private User overlooker;

    public KPI(LocalDate creationDate, String title, String description, Category category, User creator, User overlooker) {
        setCreationDate(creationDate);
        setTitle(title);
        setDescription(description);
        setCategory(category);
        setCreator(creator);
        setOverlooker(overlooker);
    }
}
