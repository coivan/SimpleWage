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
import model.dao.DescontoDao;
import model.entities.Desconto;

public class DescontoDaoJDBC implements DescontoDao {

	private Connection connection;

	public DescontoDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	//insere um desconto no banco de dados
	@Override
	public void insert(Desconto obj) {

		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(
					"INSERT INTO desconto " 
					+ "(id, descricao, valor) " 
					+ "VALUES " 
			        + "(Default, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getDescricao());
			st.setDouble(2, obj.getValor());

			int numLinhasAfetadas = st.executeUpdate();

			if (numLinhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DBConnection.closeResultSet(rs);
			} else {
				throw new DBException("Erro! Nenhuma linha afetada!");
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DBConnection.closeStatement(st);
		}
	}
	
	//insere os ids do salário e do desconto na tabela salario_desconto
	@Override
	public void insertIdsDescontoSalario(Integer idSalario, Integer idDesconto) {
		
		PreparedStatement st = null;		
		try {
			st = connection.prepareStatement(
					"INSERT INTO salario_desconto " 
					+ "(idSalario, idDesconto) " 
					+ "VALUES " 
			        + "(?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, idSalario);
			st.setInt(2, idDesconto);

			int numLinhasAfetadas = st.executeUpdate();

			if (numLinhasAfetadas > 0) {
				System.out.println("Linhas afetadas: " + numLinhasAfetadas);
			} else {
				throw new DBException("Erro! Nenhuma linha afetada!");
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DBConnection.closeStatement(st);
		}
	}

	//returna um desconto, conforme o id, do banco de dados
	@Override
	public Desconto read(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT * FROM desconto WHERE id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Desconto desconto = new Desconto();
				desconto.setId(rs.getInt("id"));
				desconto.setDescricao(rs.getString("descricao"));
				desconto.setValor(rs.getDouble("valor"));
				return desconto;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DBConnection.closeStatement(st);
			DBConnection.closeResultSet(rs);
		}
	}

	//retorna todos os descontos armazenados no banco de dados
	@Override
	public List<Desconto> readAll(){
		
		PreparedStatement st = null;
		ResultSet rs = null;		
		try {
			st = connection.prepareStatement(
					"SELECT * FROM desconto ORDER BY descricao");
			rs = st.executeQuery();
			
			List<Desconto> list = new ArrayList<>();
			
			while(rs.next()) {
				Desconto desconto = new Desconto();
				desconto.setId(rs.getInt("id"));
				desconto.setDescricao(rs.getString("descricao"));
				desconto.setValor(rs.getDouble("valor"));
				list.add(desconto);
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
	
	//retorna todos os descontos de um salario
		@Override
		public List<Desconto> readByIdSalario(Integer idSalario){			
			PreparedStatement st = null;
			ResultSet rs = null;		
			try {
				st = connection.prepareStatement(
						"SELECT d.descricao, d.valor FROM desconto d "
						+ "join salario_desconto sd on d.id = sd.idDesconto "
						+ "where sd.idSalario = ?");
				st.setInt(1, idSalario);
				rs = st.executeQuery();
				
				List<Desconto> list = new ArrayList<>();
				
				while(rs.next()) {
					Desconto desconto = new Desconto();
					desconto.setDescricao(rs.getString("descricao"));
					desconto.setValor(rs.getDouble("valor"));
					list.add(desconto);
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

	//atualiza um desconto no banco de dados
	@Override
	public void update(Desconto obj) {

		PreparedStatement st = null;		
		try {
			st = connection.prepareStatement(
					"UPDATE desconto "
					+ "SET descricao = ?, valor = ? "
					+ "WHERE id = ?");
			st.setString(1, obj.getDescricao());
			st.setDouble(2, obj.getValor());
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

	//deleta um desconto no banco de dados
	@Override
	public void delete(Integer id) {
		
		PreparedStatement st = null;		
		try {
			st = connection.prepareStatement("DELETE FROM desconto WHERE id = ?");
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

















