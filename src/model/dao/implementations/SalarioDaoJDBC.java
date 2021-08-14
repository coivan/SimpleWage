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
import model.dao.SalarioDao;
import model.entities.Salario;

public class SalarioDaoJDBC implements SalarioDao {
	
	private Connection connection;
	
	public SalarioDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	//insere um salario no banco de dados
	@Override
	public void insert(Salario obj) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(
					"INSERT INTO salario "
					+ "(id, idCargo, valorBase, idEmpresa) "
					+ "VALUES "
					+ "(DEFAULT, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, obj.getIdCargo());
			st.setDouble(2, obj.getValorBase());
			st.setInt(3, obj.getIdEmpresa());
			
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

	//lê um salario do banco de dados com o id
	@Override
	public Salario read(Integer idCargo) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT * FROM salario WHERE idCargo = ?");
			st.setInt(1, idCargo);
			rs = st.executeQuery();
			if(rs.next()) {
				Salario salario = new Salario();
				salario.setId(rs.getInt("id"));
				salario.setIdCargo(rs.getInt("idCargo"));
				salario.setValorBase(rs.getDouble("valorBase"));
				salario.setIdEmpresa(rs.getInt("idEmpresa"));
				return salario;
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

	//lê todos os salarios de uma empresa
	@Override
	public List<Salario> readAll(Integer idEmpresa) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT * FROM salario WHERE idEmpresa = ?");
			st.setInt(1, idEmpresa);
			rs = st.executeQuery();
			
			List<Salario> list = new ArrayList<>();
			
			while(rs.next()) {
				Salario salario = new Salario();
				salario.setId(rs.getInt("id"));
				salario.setIdCargo(rs.getInt("idCargo"));
				salario.setValorBase(rs.getDouble("valorBase"));
				salario.setIdEmpresa(rs.getInt("idEmpresa"));
				list.add(salario);
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

	//atualiza um salario no banco de dados
	@Override
	public void update(Salario obj) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(
					"UPDATE salario "
					+ "SET idCargo = ?, valorBase = ?, idEmpresa = ? "
					+ "WHERE id = ?");
			st.setInt(1, obj.getIdCargo());
			st.setDouble(2, obj.getValorBase());
			st.setInt(3, obj.getIdEmpresa());
			st.setInt(4, obj.getId());
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(st);
		}		
	}

	//deleta um salario do banco de dados
	@Override
	public void delete(Integer id) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement("DELETE FROM salario WHERE id = ?");
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
