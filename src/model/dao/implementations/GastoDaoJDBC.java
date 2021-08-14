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
import model.dao.GastoDao;
import model.entities.Gasto;

public class GastoDaoJDBC implements GastoDao {

	private Connection connection;
	
	public GastoDaoJDBC(Connection connection) {
		this.connection = connection;		
	}

	//insere um gasto do funcionario no banco de dados
	@Override
	public void insert(Gasto obj) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(
					"INSERT INTO gasto "
					+ "(id, nome, cpfFuncionario) "
					+ "VALUES "
					+ "(DEFAULT, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getCpfFuncionario());
			
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

	//lê um gasto do banco de dados pelo id
	@Override
	public Gasto read(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT * FROM gasto WHERE id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Gasto gasto = new Gasto();
				gasto.setId(rs.getInt("id"));
				gasto.setNome(rs.getString("nome"));
				gasto.setCpfFuncionario(rs.getString("cpfFuncionario"));
				return gasto;
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

	//retorna todos os gastos registrados no banco de dados
	@Override
	public List<Gasto> readAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT * FROM gasto");
			rs = st.executeQuery();
			
			List<Gasto> list = new ArrayList<>();
			
			while(rs.next()) {
				Gasto gasto = new Gasto();
				gasto.setId(rs.getInt("id"));
				gasto.setNome(rs.getString("nome"));
				gasto.setCpfFuncionario(rs.getString("cpfFuncionario"));
				list.add(gasto);
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
	
	//retorna todos os gastos de um funcionario registrados no banco de dados
	@Override
	public List<Gasto> readAllByCpf(String cpfFuncionario) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT * FROM gasto WHERE cpfFuncionario = ?");
			st.setString(1, cpfFuncionario);
			rs = st.executeQuery();
			
			List<Gasto> list = new ArrayList<>();
			
			while(rs.next()) {
				Gasto gasto = new Gasto();
				gasto.setId(rs.getInt("id"));
				gasto.setNome(rs.getString("nome"));
				gasto.setCpfFuncionario(rs.getString("cpfFuncionario"));
				list.add(gasto);
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

	//atualiza um gasto no banco de dados
	@Override
	public void update(Gasto obj) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(
					"UPDATE gasto "
					+ "SET nome = ?, cpfFuncionario = ? "
					+ "WHERE id = ?");
			st.setString(1, obj.getNome());
			st.setString(2, obj.getCpfFuncionario());
			st.setInt(3, obj.getId());
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(st);
		}
	}

	//deleta um gasto no banco de dados
	@Override
	public void delete(Integer id) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement("DELETE FROM gasto WHERE id = ?");
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








