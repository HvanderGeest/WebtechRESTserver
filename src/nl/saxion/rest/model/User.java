package nl.saxion.rest.model;

public class User {
	private String firstname;
	private String insertion;
	private String lastname;
	private String nickname;
	private String password;
	
	public User(String firstname, String insertion, String lastname, String nickname, String password) {
		super();
		this.firstname = firstname;
		this.insertion = insertion;
		this.lastname = lastname;
		this.nickname = nickname;
		this.password = password;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getInsertion() {
		return insertion;
	}
	public void setInsertion(String insertion) {
		this.insertion = insertion;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
