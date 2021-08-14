package model.entities;

import java.security.MessageDigest;
import java.util.Date;

import model.dao.DaoFactory;
import model.dao.GastoDao;
import model.exceptions.DomainException;

public class Funcionario {
	//atributos
    private String cpf;
    private String nome;    
    private Date nascimento;
    private String sexo;
    private String senha;
    private Integer idEmpresa;
    private Integer idCargo;
    private Boolean ativo;
	private Boolean gerenciamento;
	
	GastoDao gastoDao = DaoFactory.createGastoDao();

    // Construtores
    public Funcionario() {
    }
    
    public Funcionario(String cpf, String nome, Date nascimento, String sexo, 
    		String senha, Integer idEmpresa, Integer idCargo, 
    		Boolean ativo, Boolean gerenciamento) {
        this.cpf = cpf;
        this.nome = nome;        
        this.nascimento = nascimento;
        this.sexo = sexo;
        this.senha = senha;
        this.idEmpresa = idEmpresa;
        this.idCargo = idCargo;
        this.ativo = ativo;
		this.gerenciamento = gerenciamento;
    }

    // Métodos Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getSexo() {
        return sexo;
    }

    public String getSenha() {
        return senha;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSexo(String sexo) {   
        this.sexo = sexo;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }
    
    public void setIdEmpresa(Integer idEmpresa) {
    	this.idEmpresa = idEmpresa;
    }
    
    public void setIdCargo(Integer idCargo) {
    	this.idCargo = idCargo;
    }
    
    public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public Boolean getGerenciamento() {
		return gerenciamento;
	}

	public void setGerenciamento(Boolean gerenciamento) {
		this.gerenciamento = gerenciamento;
	}

	// Outros métodos
	//calcula as horas extras de um funcionário
    public double calcularHorasExtras(double quantidade, double salario) {
    	try {
	    	double valorHoraExtra = salario/176.0;
	    	valorHoraExtra += 0.5 * valorHoraExtra;
	    	return quantidade * valorHoraExtra;
    	}
    	catch (Exception e) {
    		throw new DomainException("Erro inesperado: " + e.getMessage());
    	}
    }
    
    //simula o valor de gastos
    public double simularGastos(double[] gastos) {
    	try {
	    	double valorGastos = 0.0;
	    	for(Double d : gastos) {
	    		valorGastos += d.doubleValue();
	    	}
	    	return valorGastos;
    	}
    	catch (Exception e) {
    		throw new DomainException("Erro inesperado: " + e.getMessage());
    	}
    }
    
    //registra no banco de dados um novo gasto
    public void adcionarGasto(Gasto gasto) {
    	try {
    		gastoDao.insert(gasto);
    		System.out.println("Inserido! Id do novo gasto = " + gasto.getId());
    	}
    	catch(Exception e) {
    		throw new DomainException("Erro ao inserir novo gasto!");
    	}
    }
    
    public String gerarSHA256(String senha) {
    	try {
    		MessageDigest md = MessageDigest.getInstance("SHA-256");
    		byte senhaDigested[] = md.digest(senha.getBytes("UTF-8"));
    		StringBuilder sb = new StringBuilder();
    		for(byte b : senhaDigested) {
    			sb.append(String.format("%02X", 0xFF & b));
    		}
    		String senhaSha256 = sb.toString();
    		return senhaSha256;
    	}
    	catch(Exception e) {
    		throw new DomainException("Erro ao gerar hash da senha!");
    	}
    }

	@Override
	public String toString() {
		return "Funcionario [cpf=" + cpf + ", nome=" + nome + ", nascimento=" + nascimento + ", sexo=" + sexo
				+ ", senha=" + senha + ", idEmpresa=" + idEmpresa + ", idCargo=" + idCargo + ", ativo=" + ativo
				+ ", gerenciamento=" + gerenciamento + "]";
	}	
}

