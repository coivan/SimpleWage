package model.dao;

import java.util.List;

import model.entities.Desconto;

public interface DescontoDao {
	
	void insert(Desconto obj);
	void insertIdsDescontoSalario(Integer idSalario, Integer idDesconto);
	Desconto read(Integer id);
	List<Desconto> readByIdSalario(Integer idSalario);
	List<Desconto> readAll();
	void update(Desconto obj);
	void delete(Integer id);
}
