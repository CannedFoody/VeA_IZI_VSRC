package eu.virac.vea_izi_vsrc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Table
@Entity

public class User {
	
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
    @Pattern(regexp = "\"[A-Ž]{1}[a-ž]{2,20}([ ]{1}([A-Ž]{1}[a-ž]{2,20}))?\"")
    private String role;

    @OneToOne
    @JoinColumn(name = "idDepartment")
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

