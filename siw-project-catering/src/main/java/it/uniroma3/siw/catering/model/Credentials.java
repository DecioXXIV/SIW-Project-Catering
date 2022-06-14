package it.uniroma3.siw.catering.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Credentials {
	
	public static final String DEFAULT_ROLE = "DEFAULT";
	public static final String ADMIN_ROLE = "ADMIN";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(nullable = false, unique = true)
	private String username;

	@NotBlank
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String role;
	
	/* OSSERVAZIONE: 
	 * Il motivo del "CascadeType.ALL" è legato all'Operazione di Registrazione.
	 * La Registrazione avviene aggiungendo le nuove Credenziali, associate al nuovo Utente, nel Database.
	 * Grazie a "CascadeType.ALL", quando viene fatto il "persist" delle nuove Credenziali, questo viene propagato anche al nuovo Utente.
	*/
	@OneToOne(cascade = CascadeType.ALL)
	private User user;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

