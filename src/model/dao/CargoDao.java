package model.dao;

import java.util.List;

import model.entities.Cargo;

public interface CargoDao {

	void insert(Cargo obj); //create
	Cargo read(Integer id); //read
	List<Cargo> readAll(); //read
	List<Cargo> readByEmpresa(Integer idEmpresa);
	void update(Cargo obj); //update
	void delete(Integer id); //delete
}
