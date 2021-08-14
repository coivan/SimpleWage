package model.dao;

import java.util.List;

import model.entities.Gasto;

public interface GastoDao {

	void insert(Gasto obj);
	Gasto read(Integer id);
	List<Gasto> readAllByCpf(String cpfFuncionario);
	List<Gasto> readAll();
	void update(Gasto obj);
	void delete(Integer id);
}
