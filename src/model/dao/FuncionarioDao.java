package model.dao;

import java.util.List;

import model.entities.Funcionario;

public interface FuncionarioDao {

	void insert(Funcionario obj);
	Funcionario read(String cpf);
	List<Funcionario> readAllById(Integer idEmpresa);
	List<Funcionario> readAll();
	void update(Funcionario obj);
	void delete(String cpf);
}
