package model.dao;

import model.entities.Empresa;

public interface EmpresaDao {
	
	void insert(Empresa obj);
	Empresa read(Integer id);
	void update(Empresa obj);
	void delete(Integer id);
	
}
