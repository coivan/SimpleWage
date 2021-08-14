package model.dao;

import java.util.List;

import model.entities.HoraExtra;

public interface HoraExtraDao {

	void insert(HoraExtra obj);
	HoraExtra read(Integer id);
	List<HoraExtra> readAll(String cpf);
	void update(HoraExtra obj);
	void delete(Integer id);
}
