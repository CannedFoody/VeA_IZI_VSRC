package eu.virac.vea_izi_vsrc.model.security;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "UserTable")
@Entity
public class SecUser {
	
	@Id
	@Column(name = "Idu")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private long idu;
	
	@Column(name = "Username")
	@NotNull
	@NotEmpty
	private String username;
	
	@Column(name = "Password")
	@NotNull
	@NotEmpty
	private String password;
	
	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private Collection<SecAuthority> authorities = new ArrayList<SecAuthority>();
	
	public void addAuthority(SecAuthority authority) {
		if (!authorities.contains(authority)) {
			authorities.add(authority);
		}
	}
	
	public void removeAuthority(SecAuthority authority) {
		if (authorities.contains(authority)) {
			authorities.remove(authority);
		}
	}
	
	public SecUser(String username, String password, SecAuthority ... inputAuthorities) {
		setUsername(username);
		setPassword(password);
		for(SecAuthority tempA : inputAuthorities) {
			addAuthority(tempA);
		}
	}
}
