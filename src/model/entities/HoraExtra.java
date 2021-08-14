package model.entities;

import java.util.Date;

import model.exceptions.DomainException;

public class HoraExtra {
	//atributos
    private Integer id;
    private Double tempoHoraExtra;
    private String cpfFuncionario; 
    private Date data;
    private Double valorBase;


    // Construtores
    public HoraExtra() {
    }
    
    public HoraExtra(Integer id, Double tempoHoraExtra, String cpfFuncionario, 
    		Date data, Double valorBase) {
		this.id = id;
		this.tempoHoraExtra = tempoHoraExtra;
		this.cpfFuncionario = cpfFuncionario;
		this.data = data;
		this.valorBase = valorBase;
	}



	// Métodos Getters e Setters
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getTempoHoraExtra() {
		return tempoHoraExtra;
	}

	public void setTempoHoraExtra(Double tempoHoraExtra) {
		this.tempoHoraExtra = tempoHoraExtra;
	}

	public String getCpfFuncionario() {
		return cpfFuncionario;
	}

	public void setCpfFuncionario(String cpfFuncionario) {
		this.cpfFuncionario = cpfFuncionario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getValorBase() {
		return valorBase;
	}

	public void setValorBase(Double valorBase) {
		this.valorBase = valorBase;
	}
    
    //outros metodos
    public String simularHorasExtras(double quantidade, int numeroDias, double salario) {
    	try {
	    	if(quantidade > 2.0 && numeroDias < 2) {
	    		return "Numero de horas diarias excedido";
	    	}
	    	else if(quantidade > 10.0 && numeroDias < 10) {
	    		return "Numero de horas semanais excedido";
	    	}
	    	else if(quantidade > 44.0 && numeroDias < 22) {
	    		return "Numero de horas mensais excedido";
	    	}
	    	else {
	    		double valorBaseHoraExtra = salario / 176.00;
	        	valorBaseHoraExtra += 0.5 * valorBaseHoraExtra;
	        	return String.format("%.2f", quantidade * numeroDias * valorBaseHoraExtra);
	    	}
    	}
    	catch(Exception e) {
    		throw new DomainException("Erro inesperado: " + e.getMessage());
		}
    }

	@Override
	public String toString() {
		return "HoraExtra [id=" + id + ", tempoHoraExtra=" + tempoHoraExtra + ", cpfFuncionario=" + cpfFuncionario
				+ ", data=" + data + ", valorBase=" + valorBase + "]";
	}   
}

