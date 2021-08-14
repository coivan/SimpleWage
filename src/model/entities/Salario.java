package model.entities;

import java.util.List;

import model.exceptions.DomainException;

public class Salario {
    private Integer id; 
    private Integer idCargo;
    private Double valorBase;
    private Integer idEmpresa;
   
    // Construtores
    public Salario() {
    }
    
    public Salario(Double valorBase) {
        this.valorBase = valorBase;
    }
    
    public Salario(Integer id, Integer idCargo, Double valorBase, Integer idEmpresa) {
		this.id = id;
		this.idCargo = idCargo;
		this.valorBase = valorBase;
		this.idEmpresa = idEmpresa;
	}

	// Métodos Getters e Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValorBase() {
		return valorBase;
	}

	public void setValorBase(Double valorBase) {
		this.valorBase = valorBase;
	}
	
	public Integer getIdCargo() {
		return idCargo;
	}
	
	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}
	
	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	//outros métodos
    //calcula o desconto total a partir de uma lista de descontos e armazena em descontoTotal
    public double calcularDescontoTotal(List<Desconto>  desconto) {
    	try {
	    	double descontoTotal = 0.0;
	    	for (Desconto d : desconto) {
	    		descontoTotal += d.getValor();
	    	}
	    	return descontoTotal;
    	}
    	catch(Exception e) {
    		throw new DomainException("Erro inesperado: " + e.getMessage());
    	}
    }
    
    //calcula o salario líquido a ser recebido pelo funcionário
    public double calcularSalarioLiquido(List<Desconto> desconto) {
    	try {
    		return valorBase - calcularDescontoTotal(desconto);
    	}
		catch(Exception e) {
			throw new DomainException("Erro inesperado: " + e.getMessage());
		}
    }

	@Override
	public String toString() {
		return "Salario [id=" + id + ", idCargo=" + idCargo + ", valorBase=" + valorBase + ", idEmpresa=" + idEmpresa
				+ "]";
	}
}



