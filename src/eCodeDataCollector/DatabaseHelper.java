package eCodeDataCollector;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHelper {
	private ArrayList<ECodeData> data = new ArrayList<ECodeData>();
	static String url = "jdbc:sqlite:";
	Connection connection = null;

	public DatabaseHelper(String dbName) {
		url += dbName;
	}

	private Connection connect() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(url);
			} catch (SQLException e) {
				System.out.println(">>");
				System.out.println(e.getMessage());
			}
		}
		return connection;
	}

	public static void createNewDatabase() {
		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void createECodeInfoTable() {
		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS E_CODE_INFO (" + "code text PRIMARY KEY," + "name text NOT NULL,"
				+ "description text NOT NULL," + "halalStatus text NOT NULL);";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			System.out.println("Table created");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertAll(ArrayList<ECodeData> data) throws SQLException {
		connect();
		for (ECodeData eCodeData : data) {
			insert(eCodeData);
		}
		System.out.println("Inserted all data");
		connection.close();
	}

	private void insert(ECodeData eCodeData) throws SQLException {
		String sql = "INSERT INTO E_CODE_INFO(code,name,description,halalStatus) VALUES(?,?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, eCodeData.getCode());
		preparedStatement.setString(2, eCodeData.getName());
		preparedStatement.setString(3, eCodeData.getDescription());
		preparedStatement.setString(4, eCodeData.getHalalStatus());
		preparedStatement.executeUpdate();

	}
}
