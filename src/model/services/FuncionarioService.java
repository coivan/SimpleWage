package model.services;

import java.util.List;

import model.dao.CargoDao;
import model.dao.DaoFactory;
import model.dao.DescontoDao;
import model.dao.FuncionarioDao;
import model.dao.GastoDao;
import model.dao.HoraExtraDao;
import model.dao.SalarioDao;
import model.entities.Cargo;
import model.entities.Desconto;
import model.entities.Funcionario;
import model.entities.Gasto;
import model.entities.HoraExtra;
import model.entities.Salario;

public class FuncionarioService {
	
	private FuncionarioDao funcionarioDao = DaoFactory.createFuncionarioDao();
	private CargoDao cargoDao = DaoFactory.createCargoDao();
	private SalarioDao salarioDao = DaoFactory.createSalarioDao();
	private DescontoDao descontoDao = DaoFactory.createDescontoDao();
	private HoraExtraDao horaExtraDao = DaoFactory.createHoraExtraDao();
	private GastoDao gastoDao = DaoFactory.createGastoDao();
	
	public List<Funcionario> buscarFuncionariosDaEmpresa(Integer idEmpresa){
		return funcionarioDao.readAllById(idEmpresa);
	}
	
	public Funcionario buscarFuncionario(String cpf) {
		return funcionarioDao.read(cpf);
	}
	
	public void insertOuUpdateFuncionario(Funcionario funcionario) {
		if(funcionario.getCpf() == null) {
			funcionarioDao.insert(funcionario);
		}
		else {
			funcionarioDao.update(funcionario);
		}
	}
	
	public void removeFuncionario(Funcionario funcionario) {
		funcionarioDao.delete(funcionario.getCpf());
	}
	
	public Cargo buscarCargo(Integer idCargo) {
		return cargoDao.read(idCargo);
	}
	
	public Salario buscarSalario(Integer idCargo) {
		return salarioDao.read(idCargo);
	}
	
	public List<Desconto> buscarDescontos(Integer idSalario){
		return descontoDao.readByIdSalario(idSalario);
	}
	
	public List<HoraExtra> buscarHorasExtras(String cpf){
		return horaExtraDao.readAll(cpf);
	}
	
	public List<Gasto> buscarGastosDoFuncionario(String cpfFuncionario){
		return gastoDao.readAllByCpf(cpfFuncionario);
	}
	
	public List<Gasto> buscarTodosOsGastos(){
		return gastoDao.readAll();
	}
}
