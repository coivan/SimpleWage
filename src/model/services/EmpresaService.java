package model.services;

import java.util.List;

import model.dao.CargoDao;
import model.dao.DaoFactory;
import model.dao.EmpresaDao;
import model.entities.Cargo;
import model.entities.Empresa;

public class EmpresaService {
	
	private EmpresaDao empresaDao = DaoFactory.createEmpresaDao();
	private CargoDao cargoDao = DaoFactory.createCargoDao();
	
	public Empresa buscaEmpresa(Integer id){
		return empresaDao.read(id); 
	}
	
	public void insertOuUpdateEmpresa(Empresa empresa) {
		if(empresa.getId() == null) {
			empresaDao.insert(empresa);
		}
		else {
			empresaDao.update(empresa);
		}
	}
	
	public void removeEmpresa(Empresa empresa) {
		empresaDao.delete(empresa.getId());
	}
	
	public List<Cargo> buscarCargosDaEmpresa(Integer idEmpresa) {
		return cargoDao.readByEmpresa(idEmpresa);
	}
}
