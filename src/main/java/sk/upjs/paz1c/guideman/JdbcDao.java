package sk.upjs.paz1c.guideman;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcDao {

	private static final String DATABASE_URL = "jdbc:mysql://localhost/guideman?serverTimezone=Europe/Bratislava";
	private static final String DATABASE_USERNAME = "guideman";
	private static final String DATABASE_PASSWORD = "guideman2022";
	private static final String SELECT_QUERY = "SELECT * FROM user WHERE login = ? and password = ?";

	public boolean validate(String login, String password) throws SQLException {

		try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, password);

			System.out.println(preparedStatement);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}

		} catch (SQLException e) {
			printSQLException(e);
		}
		return false;
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