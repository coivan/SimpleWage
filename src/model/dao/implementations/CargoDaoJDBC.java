package model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbConnection.DBConnection;
import dbConnection.DBException;
import model.dao.CargoDao;
import model.entities.Cargo;
import model.entities.Empresa;

public class CargoDaoJDBC implements CargoDao {

	private Connection connection;
	
	public CargoDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	//insere um cargo no banco de dados
	@Override
	public void insert(Cargo obj) {
		
		PreparedStatement st = null;	
		try {
			st = connection.prepareStatement(
					"INSERT INTO cargo " +
					"(nome, idEmpresa) " +
					"VALUES " +
					"(?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setInt(2, 1);
			
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
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(st);
		}	
	}

	//recupera um cargo do banco de dados conforme o id
	@Override
	public Cargo read(Integer id) {	
		PreparedStatement st = null;
		ResultSet rs = null;		
		try {
			st = connection.prepareStatement(
					"SELECT c.nome "
					+ "FROM cargo c "
					+ "WHERE c.id = ?");		
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Cargo cargo = new Cargo();
				cargo.setNome(rs.getString("nome"));
				return cargo;
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

	private Empresa instanciaEmpresa(ResultSet rs) throws SQLException {
		Empresa empresa = new Empresa();
		empresa.setNome(rs.getString("Empresa"));
		return empresa;
	}

	private Cargo instanciaCargo(ResultSet rs, Empresa empresa) throws SQLException {
		Cargo cargo = new Cargo();
		cargo.setId(rs.getInt("id"));
		cargo.setNome(rs.getString("nome"));
		cargo.setIdEmpresa(rs.getInt("idEmpresa"));
		cargo.setEmpresa(empresa);
		return cargo;
	}

	//recupera todos os cargos de uma empresa do banco de dados
	@Override
	public List<Cargo> readByEmpresa(Integer idEmpresa){
		
		PreparedStatement st = null;
		ResultSet rs = null;	
		try {
			st = connection.prepareStatement(
					"SELECT * FROM cargo c "
					+ "WHERE c.idEmpresa = ?");
			st.setInt(1, idEmpresa);			
			rs = st.executeQuery();
			
			List<Cargo> list = new ArrayList<>();
			
			while(rs.next()) {
				Cargo cargo = new Cargo();
				cargo.setId(rs.getInt("id"));
				cargo.setNome(rs.getString("nome"));
				cargo.setIdEmpresa(rs.getInt("idEmpresa"));
				list.add(cargo);
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
	
	//recupera todos os cargos e as empresas a qual eles pertencem do banco de dados
	@Override
	public List<Cargo> readAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;		
		try {
			st = connection.prepareStatement(
					"SELECT c.*, e.nome AS 'Empresa' "
					+ "FROM cargo c JOIN empresa e "
					+ "ON e.id = c.idEmpresa "
					+ "ORDER BY c.nome");
			
			rs = st.executeQuery();
			
			List<Cargo> list = new ArrayList<>();
			Map<Integer, Empresa> map = new HashMap<>();
			
			while(rs.next()) {
				Empresa empresa = map.get(rs.getInt("idEmpresa"));				
				if(empresa == null) {
					empresa = instanciaEmpresa(rs);
					map.put(rs.getInt("idEmpresa"), empresa);
				}
				Cargo cargo = instanciaCargo(rs, empresa);
				list.add(cargo);
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

	//atualiza um cargo no banco de dados
	@Override
	public void update(Cargo obj) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(
					"UPDATE cargo "
					+ "SET nome = ?, idEmpresa = ? "
					+ "WHERE id = ?");
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getIdEmpresa());
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

	//deleta um cargo pelo id no banco de dados
	@Override
	public void delete(Integer id) {
		
		PreparedStatement st = null;		
		try {
			st = connection.prepareStatement("DELETE FROM cargo WHERE id = ?");
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




















