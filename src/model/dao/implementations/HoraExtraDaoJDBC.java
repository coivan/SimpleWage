package model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbConnection.DBConnection;
import dbConnection.DBException;
import model.dao.HoraExtraDao;
import model.entities.HoraExtra;

public class HoraExtraDaoJDBC implements HoraExtraDao {
	
	private Connection connection;
	
	public HoraExtraDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	//insere hora extra no banco de dados
	@Override
	public void insert(HoraExtra obj) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(
					"INSERT INTO hora_extra "
					+ "(id, tempoHoraExtra, cpfFuncionario, data, valorBase) "
					+ "VALUES "
					+ "(DEFAULT, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setDouble(1, obj.getTempoHoraExtra());
			st.setString(2, obj.getCpfFuncionario());
			st.setDate(3, new java.sql.Date(obj.getData().getTime()));
			st.setDouble(4, obj.getValorBase());
			
			int numeroLinhasAfetadas = st.executeUpdate();
			
			if(numeroLinhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DBConnection.closeResultSet(rs);
			}
			else {
				throw new DBException("Erro! Nenhuma linha afetada!");
			}
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(st);
		}
	}

	//lê uma hora extra do banco de dados, conforme o id
	@Override
	public HoraExtra read(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT * FROM hora_extra WHERE id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				HoraExtra horaExtra = new HoraExtra();
				horaExtra.setId(rs.getInt("id"));
				horaExtra.setTempoHoraExtra(rs.getDouble("tempoHoraExtra"));
				horaExtra.setCpfFuncionario(rs.getString("cpfFuncionario"));
				horaExtra.setData(rs.getDate("data"));
				horaExtra.setValorBase(rs.getDouble("valorBase"));
				return horaExtra;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(st);
			DBConnection.closeResultSet(rs);
		}
	}

	//lê todas as horas extras de um funcionario
	@Override
	public List<HoraExtra> readAll(String cpf) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT * FROM hora_extra WHERE cpfFuncionario = ?");
			st.setString(1, cpf);
			rs = st.executeQuery();
			
			List<HoraExtra> list = new ArrayList<>();
			
			while(rs.next()) {
				HoraExtra horaExtra = new HoraExtra();
				horaExtra.setId(rs.getInt("id"));
				horaExtra.setTempoHoraExtra(rs.getDouble("tempoHoraExtra"));
				horaExtra.setCpfFuncionario(rs.getString("cpfFuncionario"));
				horaExtra.setData(rs.getDate("data"));
				horaExtra.setValorBase(rs.getDouble("valorBase"));
				list.add(horaExtra);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(st);
			DBConnection.closeResultSet(rs);
		}
	}

	//atualiza os dados de uma hora extra no banco de dados
	@Override
	public void update(HoraExtra obj) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(
					"UPDATE hora_extra "
					+ "SET tempoHoraExtra = ?, cpfFuncionario = ?, data = ?, valorBase = ? "
					+ "WHERE id = ?");
			st.setDouble(1, obj.getTempoHoraExtra());
			st.setString(2, obj.getCpfFuncionario());
			st.setDate(3, new java.sql.Date(obj.getData().getTime()));
			st.setDouble(4, obj.getValorBase());
			st.setInt(5, obj.getId());
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(st);
		}
	}

	//deleta uma hora extra do banco de dados
	@Override
	public void delete(Integer id) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement("DELETE FROM hora_extra WHERE id = ?");
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(st);
		}
	}
}
