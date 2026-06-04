package eu.virac.vea_izi_vsrc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Department")
@Entity
public class Department {
	
	@Column(name = "IdDepartment")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private long idDepartment;
	
	@Column(name = "Name", unique = true)
	@NotNull
	@NotEmpty
	@Pattern(regexp = "[A-Ž]{1}[A-Ža-ž0-9 ]{3,40}")
	private String name;
	
	@Column(name = "Description", unique = true)
	@NotNull
	@NotEmpty
	@Pattern(regexp = "[A-Ža-ž0-9 ]{3,50}")
	private String description;
	
	@Column(name = "Goals", unique = true)
	@NotNull
	@NotEmpty
	@Pattern(regexp = "[A-Ža-ž0-9 ]{3,50}")
	private String goals;


	@OneToOne
	@JoinColumn(name = "idUser")
	@ToString.Exclude
	private User user;
	
	public Department(String name, String description, String goals) {
		setName(name);
		setDescription(description);
		setGoals(goals);
	}
}
