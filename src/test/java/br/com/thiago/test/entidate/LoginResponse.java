package br.com.thiago.test.entidate;

public class LoginResponse {

	private Integer id;
	private String token;

	public LoginResponse(Integer id, String token) {
		super();
		this.id = id;
		this.token = token;
	}
	
	public LoginResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
