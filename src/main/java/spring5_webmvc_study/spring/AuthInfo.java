package spring5_webmvc_study.spring;

public class AuthInfo {
	private Long ld;
	private String email;
	private String name;
	
	public AuthInfo(Long ld, String email, String name) {
		this.ld = ld;
		this.email = email;
		this.name = name;
	}
	
	public Long getLd() {
		return ld;
	}
	
	public void setLd(Long ld) {
		this.ld = ld;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("AuthInfo [ld=%s, email=%s, name=%s]", ld, email, name);
	}
}
