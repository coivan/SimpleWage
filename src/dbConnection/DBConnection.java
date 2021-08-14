package dbConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {

	public static Connection connection = null;
	
	//estabelece a conexão com banco de dados
	public static Connection getConnection() {
		if(connection == null) {
			try {
				Properties properties = loadProperties();
				String dburl = properties.getProperty("dburl");
				connection = DriverManager.getConnection(dburl, properties);
			}
			catch(SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
		return connection;
	}

	private static Properties loadProperties() {
		try (FileInputStream fis = new FileInputStream("db.properties")) {
			Properties properties = new Properties();
			properties.load(fis);
			return properties;
		}
		catch(IOException e) {
			throw new DBException(e.getMessage());
		}		
	}
	
	//fecha a conexão com banco de dados
	public static void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
			}
			catch(SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
	//fecha o recurso que armazena os dados recuperados do banco de dados
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}
			catch(SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
	//fecha o recurso que armazena os comandos de CRUD no banco de dados
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			}
			catch(SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
}
