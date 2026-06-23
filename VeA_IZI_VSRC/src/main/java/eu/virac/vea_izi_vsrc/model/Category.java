package eu.virac.vea_izi_vsrc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name = "idCategory")
    private Long idCategory;

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
