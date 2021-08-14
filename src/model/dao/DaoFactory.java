package model.dao;

import dbConnection.DBConnection;
import model.dao.implementations.CargoDaoJDBC;
import model.dao.implementations.DescontoDaoJDBC;
import model.dao.implementations.EmpresaDaoJDBC;
import model.dao.implementations.FuncionarioDaoJDBC;
import model.dao.implementations.GastoDaoJDBC;
import model.dao.implementations.HoraExtraDaoJDBC;
import model.dao.implementations.SalarioDaoJDBC;

public class DaoFactory {

	public static CargoDao createCargoDao() {
		return new CargoDaoJDBC(DBConnection.getConnection());
	}
	
	public static DescontoDao createDescontoDao() {
		return new DescontoDaoJDBC(DBConnection.getConnection());
	}
	
	public static EmpresaDao createEmpresaDao() {
		return new EmpresaDaoJDBC(DBConnection.getConnection());
	}
	
	public static FuncionarioDao createFuncionarioDao() {
		return new FuncionarioDaoJDBC(DBConnection.getConnection());
	}
	
	public static GastoDao createGastoDao() {
		return new GastoDaoJDBC(DBConnection.getConnection());
	}
	
	public static HoraExtraDao createHoraExtraDao() {
		return new HoraExtraDaoJDBC(DBConnection.getConnection());
	}
	
	public static SalarioDao createSalarioDao() {
		return new SalarioDaoJDBC(DBConnection.getConnection());
	}
}
