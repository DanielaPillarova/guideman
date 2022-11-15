package sk.upjs.paz1c.guideman.storage;

import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class MysqlUserDao implements UserDao {

	private JdbcTemplate jdbcTemplate;
	
	public MysqlUserDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<User> getAll() {
		String sql = "SELECT id, name, surname, email, tel_number, birthdate, login, password, image FROM user";
		return jdbcTemplate.query(sql, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String email = rs.getString("email");
				String telNumber = rs.getString("tel_number"); // moze byt null
				Date birthdate = rs.getDate("birthdate");
				String login = rs.getString("login");
				String password = rs.getString("password");
				// https://stackoverflow.com/questions/14610011/reading-a-blob-from-mysql-with-java
				Blob image = rs.getBlob("image"); // moze byt null
				
				return new User(id, name, surname, email, telNumber, birthdate, login, password, image);
			}
			
		});
	}

}
