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
import model.dao.FuncionarioDao;
import model.entities.Funcionario;

public class FuncionarioDaoJDBC implements FuncionarioDao {

	private Connection connection;
	
	public FuncionarioDaoJDBC(Connection connection) {
		this.connection = connection;
	}
	
	//insere um funcionario no banco de dados
	@Override
	public void insert(Funcionario obj) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(
					"INSERT INTO funcionario "
					+ "(cpf, nome, nascimento, sexo, senha, idEmpresa, idCargo, ativo, gerenciamento) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getCpf());
			st.setString(2, obj.getNome());
			st.setDate(3, new java.sql.Date(obj.getNascimento().getTime()));
			st.setString(4, obj.getSexo());
			st.setString(5, obj.getSenha());
			st.setInt(6, obj.getIdEmpresa());
			st.setInt(7, obj.getIdCargo());
			st.setBoolean(8, obj.getAtivo());
			st.setBoolean(9, obj.getGerenciamento());
			
			int numeroLinhasAfetadas = st.executeUpdate();
			
			if(numeroLinhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					String cpf = rs.getString(1);
					obj.setCpf(cpf);
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
	
	//lê um funcionario do banco de dados
	@Override
	public Funcionario read(String cpf) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT * FROM funcionario WHERE cpf = ?");
			st.setString(1, cpf);
			rs = st.executeQuery();
			if(rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setNome(rs.getString("nome"));
				funcionario.setNascimento(rs.getDate("nascimento"));
				funcionario.setSexo(rs.getString("sexo"));
				funcionario.setSenha(rs.getString("senha"));
				funcionario.setIdEmpresa(rs.getInt("idEmpresa"));
				funcionario.setIdCargo(rs.getInt("idCargo"));
				funcionario.setAtivo(rs.getBoolean("ativo"));
				funcionario.setGerenciamento(rs.getBoolean("gerenciamento"));
				return funcionario;
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
	
	//lê os funcionario de uma empresa do banco de dados
	@Override
	public List<Funcionario> readAllById(Integer idEmpresa){		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT * FROM funcionario WHERE idEmpresa = ?");
			st.setInt(1, idEmpresa);
			rs = st.executeQuery();
			
			List<Funcionario> list = new ArrayList<>();
			
			while(rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setNome(rs.getString("nome"));
				funcionario.setNascimento(rs.getDate("nascimento"));
				funcionario.setSexo(rs.getString("sexo"));
				funcionario.setSenha(rs.getString("senha"));
				funcionario.setIdEmpresa(rs.getInt("idEmpresa"));
				funcionario.setIdCargo(rs.getInt("idCargo"));
				funcionario.setAtivo(rs.getBoolean("ativo"));
				funcionario.setGerenciamento(rs.getBoolean("gerenciamento"));
				list.add(funcionario);
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
	
	//lê todos os funcionarios do banco de dados
	@Override
	public List<Funcionario> readAll(){
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT * FROM funcionario");
			rs = st.executeQuery();
			
			List<Funcionario> list = new ArrayList<>();
			
			while(rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setNome(rs.getString("nome"));
				funcionario.setNascimento(rs.getDate("nascimento"));
				funcionario.setSexo(rs.getString("sexo"));
				funcionario.setSenha(rs.getString("senha"));
				funcionario.setIdEmpresa(rs.getInt("idEmpresa"));
				funcionario.setIdCargo(rs.getInt("idCargo"));
				funcionario.setAtivo(rs.getBoolean("ativo"));
				funcionario.setGerenciamento(rs.getBoolean("gerenciamento"));
				list.add(funcionario);
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
	
	//atualiza os dados de um funcionario no banco de dados
	@Override
	public void update(Funcionario obj) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(
					"UPDATE funcionario "
					+ "SET nome = ?, nascimento = ?, sexo = ?, senha = ?, " 
					+ "idEmpresa = ?, idCargo = ?, ativo = ?, gerenciamento = ? "
					+ "WHERE cpf = ?");
			st.setString(1, obj.getNome());
			st.setDate(2, new java.sql.Date(obj.getNascimento().getTime()));
			st.setString(3, obj.getSexo());
			st.setString(4, obj.getSenha());
			st.setInt(5, obj.getIdEmpresa());
			st.setInt(6, obj.getIdCargo());
			st.setBoolean(7, obj.getAtivo());
			st.setBoolean(8, obj.getGerenciamento());
			st.setString(9, obj.getCpf());
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DBConnection.closeStatement(st);
		}
	}
	
	//delete um funcionario no banco de dados, conforme o id
	@Override
	public void delete(String cpf) {
		
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(
					"DELETE FROM funcionario WHERE cpf = ?");
			st.setString(1, cpf);
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








