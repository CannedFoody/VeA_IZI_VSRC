package eu.virac.vea_izi_vsrc.model;

import eu.virac.vea_izi_vsrc.model.Enums.KPIStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(name = "deadline")
    @NotNull
    private LocalDate deadline;

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
    @Enumerated(EnumType.STRING)
    private KPIStatus status;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "idCategory")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JoinColumn(name = "creatorId")
    private User creator;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JoinColumn(name = "overlookerId")
    private User overlooker;

    public KPI(LocalDate creationDate, LocalDate deadline, String title, String description, Category category, User creator, User overlooker) {
        setCreationDate(creationDate);
        setDeadline(deadline);
        setTitle(title);
        setDescription(description);
        setCategory(category);
        setCreator(creator);
        setOverlooker(overlooker);
    }
}
