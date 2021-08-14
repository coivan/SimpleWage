package model.entities;

public class Desconto {
	// Atributos
	private Integer id;
    private String descricao;
    private Double valor;

    // Construtores
    public Desconto() {
    }
    
    public Desconto(Integer id, String descricao, Double valor) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
    }

    // Metodos Getters e Setters
    public Integer getId() {
    	return id;
    }
    
    public void setId(Integer id) {
    	this.id = id;
    }
    
    public String getDescricao() {
    	return descricao;
    }
    
    public void setDescricao(String descricao) {
    	this.descricao = descricao;
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    // Outros métodos
	@Override
	public String toString() {
		return "Desconto [id=" + id + ", descricao=" + descricao + ", valor=" + valor + "]";
	}    
}

