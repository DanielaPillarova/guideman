package sk.upjs.paz1c.guideman;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcSignupDao {

	private static final String DATABASE_URL = "jdbc:mysql://localhost/guideman?serverTimezone=Europe/Bratislava";
	private static final String DATABASE_USERNAME = "guideman";
	private static final String DATABASE_PASSWORD = "guideman2022";
	private static final String INSERT_QUERY = "INSERT INTO user (name, surname, email, tel_number, birthdate, login, password) VALUES (?, ?, ?, ?, ?, ? , ?)";

	public void insertRecord(String name, String surname, String email, String tel_number, String birthdate,
			String username, String password) throws SQLException {

		// Step 1: Establishing a Connection and
		// try-with-resource statement will auto close the connection.
		try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, surname);
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, tel_number);
			preparedStatement.setString(5, birthdate);
			preparedStatement.setString(6, username);
			preparedStatement.setString(7, password);

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// print SQL exception information
			printSQLException(e);
		}
	}

	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
