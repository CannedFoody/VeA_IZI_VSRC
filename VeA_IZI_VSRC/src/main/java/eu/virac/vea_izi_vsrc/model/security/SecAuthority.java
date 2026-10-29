package eu.virac.vea_izi_vsrc.model.security;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "AuthorityTable")
@Entity
public class SecAuthority {
	
	@Id
	@Column(name = "Ida")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private long ida;
	
	@Column(name = "Title", unique = true)
	@NotNull
	@NotEmpty
	@Pattern(regexp = "[A-Z_]{3,10}")
	private String title;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "AuthUserTable", 
	inverseJoinColumns = @JoinColumn(name = "Idu"), 
	joinColumns = @JoinColumn(name = "Ida"))
	@ToString.Exclude
	private Collection<SecUser> users = new ArrayList<SecUser>();
	
	public void addUser(SecUser user) {
		if (!users.contains(user)) {
			users.add(user);
		}
	}
	
	public void removeUser(SecUser user) {
		if (users.contains(user)) {
			users.remove(user);
		}
	}
	
	public SecAuthority(String title) {
		setTitle(title);
	}
}
