package model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbConnection.DBConnection;
import dbConnection.DBException;
import model.dao.EmpresaDao;
import model.entities.Empresa;

public class EmpresaDaoJDBC implements EmpresaDao {
	
	private Connection connection;
	
	public EmpresaDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	//insere uma empresa no banco de dados
	@Override
	public void insert(Empresa obj) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(
					"INSERT INTO empresa "
					+ "(id, cnpj, nome, telefone, endereco, email) "
					+ "VALUES "
					+ "(DEFAULT, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getCnpj());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getTelefone());
			st.setString(4, obj.getEndereco());
			st.setString(5, obj.getEmail());
			
			int numLinhasAfetadas = st.executeUpdate();
			
			if(numLinhasAfetadas > 0) {
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
	
	//lê uma empresa do banco de dados pelo id
	@Override
	public Empresa read(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT * FROM empresa WHERE id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Empresa empresa = new Empresa();
				empresa.setId(rs.getInt("id"));
				empresa.setCnpj(rs.getString("cnpj"));
				empresa.setNome(rs.getString("nome"));
				empresa.setTelefone(rs.getString("telefone"));
				empresa.setEndereco(rs.getString("endereco"));
				empresa.setEmail(rs.getString("email"));
				return empresa;
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
	
	//atualiza os dados de uma empresa no banco de dados
	@Override
	public void update(Empresa obj) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(
					"UPDATE empresa "
				    + "SET cnpj = ?, nome = ?, telefone = ?, endereco = ?, email = ? "
					+ "WHERE id = ?");
			st.setString(1, obj.getCnpj());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getTelefone());
			st.setString(4, obj.getEndereco());
			st.setString(5, obj.getEmail());
			st.setInt(6, obj.getId());
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(st);
		}
	}
	
	//deleta uma empresa do banco de dados
	@Override
	public void delete(Integer id) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement("DELETE FROM empresa WHERE id = ?");
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











