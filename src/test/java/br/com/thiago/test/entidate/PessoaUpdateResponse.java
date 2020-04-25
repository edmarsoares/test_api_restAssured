package br.com.thiago.test.entidate;

public class PessoaUpdateResponse {

	private String nome;
	private String job;
	private String id;
	private String updatedAt;
	
	public PessoaUpdateResponse(String nome, String job, String id, String updatedAt) {
		super();
		this.nome = nome;
		this.job = job;
		this.id = id;
		this.updatedAt = updatedAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public PessoaUpdateResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	
}
