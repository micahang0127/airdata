package github.hangming.airdata.model;

public class UserDto {

	private Long seq;
	private String email;
	private String password;
	
	public UserDto(Long seq, String email, String password) {
		this.seq = seq;
		this.email = email;
		this.password = password;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
