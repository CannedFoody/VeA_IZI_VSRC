package eu.virac.vea_izi_vsrc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "SubCategoryTable")
@Entity
public class SubCategory {
	
	@Column(name = "IdSubcategory")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)	
	private long idSubcategory;
	
	@Column(name = "Title", unique = true)	//subcategory names/titles are unique
	@NotNull
	@NotEmpty
	@Pattern(regexp = "[A-Ž]{1}[A-Ža-ž0-9 ]{3,40}")
	private String title;
	
	@Column(name = "Description")
	@NotNull
	@NotEmpty
	private String description;
	
	//Category 
	
	public SubCategory(String title, String description) {
		setTitle(title);
		setDescription(description);
	}
	
}
