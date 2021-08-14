package model.entities;

import java.util.Date;

import model.dao.CargoDao;
import model.dao.DaoFactory;
import model.dao.DescontoDao;
import model.dao.EmpresaDao;
import model.dao.FuncionarioDao;
import model.dao.SalarioDao;
import model.exceptions.DomainException;

public class Gerente extends Funcionario {
	
	private FuncionarioDao funcionarioDao = DaoFactory.createFuncionarioDao();
	private CargoDao cargoDao = DaoFactory.createCargoDao();
	private EmpresaDao empresaDao = DaoFactory.createEmpresaDao();
	private SalarioDao salarioDao = DaoFactory.createSalarioDao();
	private DescontoDao descontoDao = DaoFactory.createDescontoDao();
	
	public Gerente(String cpf, String nome, Date nascimento, String sexo, 
			String senha, Integer idEmpresa, Integer idCargo, 
			Boolean ativo, Boolean gerenciamento) {
		super(cpf, nome, nascimento, sexo, senha, idEmpresa, idCargo, ativo, gerenciamento);
	}
	
	//insere um funcionário no banco de dados
	public void inserirFuncionario(Funcionario funcionario) {
		try {			
			funcionarioDao.insert(funcionario);
			System.out.println("Inserido! Id do novo funcionário = " + funcionario.getCpf());
		} catch (Exception e) {
			throw new DomainException("Erro ao inser funcionário!");
		}
	}
	
	//desativa uma funcionario no banco de dados
	public void desativarFuncionario(String cpf) {
		try {
			Funcionario funcionarioUpdate = funcionarioDao.read(cpf);
			funcionarioUpdate.setAtivo(false);
			funcionarioDao.update(funcionarioUpdate);
			System.out.println("Funcionario desativado!");
		}
		catch(Exception e) {
			throw new DomainException("Erro ao desativar funcionário!");
		}
	}
	
	//atualiza os dados de um cargo no banco de dados
	public void atualizarDadosCargo(Cargo cargo) {
		try {
			cargoDao.update(cargo);
			System.out.println("Dados do cargo atualizados!");
		}
		catch(Exception e) {
			throw new DomainException("Erro ao atualizar cargo!");
		}
	}
	
	//atualiza os dados de uma empresa no banco de dados
	public void atualizarDadosEmpresa(Empresa empresa) {
		try {
			empresaDao.update(empresa);
			System.out.println("Dados da empresa atualizados!");
		}
		catch(Exception e) {
			throw new DomainException("Erro ao atualizar empresa!");
		}
	}
	
	//atualiza um salário no banco de dados
	public void atualizarDadosSalario(Salario salario) {
		try {
			salarioDao.update(salario);
			System.out.println("Dados do salario atualizados!");
		}
		catch(Exception e) {
			throw new DomainException("Erro ao atualizar salário!");
		}
	}
	
	//atualiza os dados de um funcionário no banco de dados
	public void atualizarDadosFuncionario(Funcionario funcionario) {
		try {
			funcionarioDao.update(funcionario);
			System.out.println("Dados do funcionario atualizados!");
		}
		catch(Exception e) {
			throw new DomainException("Erro ao atualizar funcionário!");
		}
	}
	
	//atualiza os dados de um desconto no banco de dados
	public void atualizarDadosDesconto(Desconto desconto) {
		try {
			descontoDao.update(desconto);
			System.out.println("Dados do desconto atualizados!");
		}
		catch(Exception e) {
			throw new DomainException("Erro ao atualizar funcionário!");
		}
	}
	
	//calcula as horas extras do gerente
	@Override
	public double calcularHorasExtras(double quantidade, double salario) {
		try {
	    	double valorHoraExtra = salario/176.0;
	    	valorHoraExtra += 0.5 * valorHoraExtra;
	    	double bonusHorasExtras = 0.05 * salario;
	    	return quantidade * valorHoraExtra + bonusHorasExtras;
		}
		catch (Exception e) {
			throw new DomainException("Erro inesperado: " + e.getMessage());
		}
    }
}
