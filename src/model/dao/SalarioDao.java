package model.dao;

import java.util.List;

import model.entities.Salario;

public interface SalarioDao {

	void insert(Salario obj);
	Salario read(Integer idCargo);
	List<Salario> readAll(Integer idEmpresa);
	void update(Salario obj);
	void delete(Integer id);
}
