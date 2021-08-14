package model.entities;

public class Empresa {
	//atributos
    private Integer id;
    private String cnpj;
    private String nome;
    private String telefone;
    private String endereco;
    private String email;

    //construtores
    public Empresa() {
    }
    
    public Empresa(Integer id, String nome) {
    	this.id = id;
    	this.nome = nome;
    }
    
    public Empresa(Integer id, String cnpj, String nome, String telefone, 
    		String endereco, String email) {
        this.id = id;
        this.cnpj = cnpj;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
    }

    // Métodos Getters e Setters
    public Integer getId() {
        return id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCnpj(String CNPJ) {
        this.cnpj = CNPJ;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setEmail(String email) {
        this.email = email;
    }
 
    @Override
	public String toString() {
		return "Empresa [id=" + id + ", cnpj=" + cnpj + ", nome=" + nome + ", telefone=" + telefone + ", endereco="
				+ endereco + ", email=" + email + "]";
	}
    
    
}
