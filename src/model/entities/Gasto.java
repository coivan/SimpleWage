package model.entities;

public class Gasto {
	
	private Integer id;
	private String nome;
	private String cpfFuncionario;
	
	public Gasto() {
	}

	public Gasto(Integer id, String nome, String cpfFuncionario) {
		this.id = id;
		this.nome = nome;
		this.cpfFuncionario = cpfFuncionario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpfFuncionario() {
		return cpfFuncionario;
	}

	public void setCpfFuncionario(String cpfFuncionario) {
		this.cpfFuncionario = cpfFuncionario;
	}

	@Override
	public String toString() {
		return "Gasto [id=" + id + ", nome=" + nome + ", cpfFuncionario=" + cpfFuncionario + "]";
	}	
}
