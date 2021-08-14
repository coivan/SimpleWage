package model.entities;

public class Cargo {

    private Integer id;
    private String nome;
    private Integer idEmpresa;
    private Empresa empresa;

    // Construtores
    public Cargo() {
    }

	public Cargo(String nome, Empresa empresa) {
		this.nome = nome;
		this.empresa = empresa;
	}
	
    public Cargo(Integer id, String nome, Integer idEmpresa) {
		this.id = id;
		this.nome = nome;
		this.idEmpresa = idEmpresa;
	}
    
    

	public Cargo(Integer id, String nome, Integer idEmpresa, Empresa empresa) {
		this.id = id;
		this.nome = nome;
		this.idEmpresa = idEmpresa;
		this.empresa = empresa;
	}

	// Métodos Getters e Setters
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

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cargo other = (Cargo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cargo [id=" + id + ", nome=" + nome + ", idEmpresa=" + idEmpresa + ", empresa=" + empresa + "]";
	}
}

