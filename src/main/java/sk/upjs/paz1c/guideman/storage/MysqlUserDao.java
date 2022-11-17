package sk.upjs.paz1c.guideman.storage;

import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

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
				LocalDate birthdate = rs.getTimestamp("birthdate").toLocalDateTime().toLocalDate();
				// treba nam pamatat si login a heslo tuto? nestaci to riesit pri prihlasovani?
				String login = rs.getString("login");
				String password = rs.getString("password");
				// https://stackoverflow.com/questions/14610011/reading-a-blob-from-mysql-with-java
				Blob image = rs.getBlob("image"); // moze byt null

				return new User(id, name, surname, email, telNumber, birthdate, login, password, image);
			}

		});
	}

	@Override
	public User getById(long id) throws EntityNotFoundException {
		String sql = "SELECT id, name, surname, email, tel_number, birthdate, login, password, image FROM user WHERE id = "
				+ id;
		try {
			return jdbcTemplate.queryForObject(sql, new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("User with id " + id + " not found");
		}
	}

	@Override
	public User save(User user) throws NullPointerException, EntityNotFoundException {
		if (user == null) {
			throw new NullPointerException("Cannot save null");
		}
		if (user.getName() == null) {
			throw new NullPointerException("Name cannot be null");
		}
		if (user.getSurname() == null) {
			throw new NullPointerException("Surname cannot be null");
		}
		if (user.getEmail() == null) {
			throw new NullPointerException("Email address cannot be null");
		}
		if (user.getBirthdate() == null) {
			throw new NullPointerException("Birthdate cannot be null");
		}
		// tu si potrebujeme pamatat login a password pretoze toto vyuzijeme pri
		// registracii
		if (user.getLogin() == null) {
			throw new NullPointerException("Login cannot be null");
		}
		if (user.getPassword() == null) {
			throw new NullPointerException("Password cannot be null");
		}

		if (user.getId() == null) { // INSERT
			SimpleJdbcInsert sjdbInsert = new SimpleJdbcInsert(jdbcTemplate);
			sjdbInsert.withTableName("user");
			sjdbInsert.usingGeneratedKeyColumns("id");
			// tel number a image mozu byt null
			sjdbInsert.usingColumns("name", "surname", "email", "tel_number", "birthdate", "login", "password",
					"image");

			Map<String, Object> values = new HashMap<>();
			values.put("name", user.getName());
			values.put("surname", user.getSurname());
			values.put("email", user.getEmail());
			values.put("name", user.getName());
			if (user.getTelNumber() != null) {
				values.put("tel_number", user.getTelNumber());
			}
			values.put("birthdate", user.getBirthdate());
			values.put("login", user.getLogin());
			values.put("password", user.getPassword());
			if (user.getImage() != null) {
				values.put("image", user.getImage());
			}

			long id = sjdbInsert.executeAndReturnKey(values).longValue();
			return new User(id, user.getName(), user.getSurname(), user.getEmail(), user.getTelNumber(),
					user.getBirthdate(), user.getLogin(), user.getPassword(), user.getImage());

		} else { // UPDATE
			String sql = "UPDATE user SET name=?, surname=?, email=?, tel_number=?, birthdate=?, login=?, password=?, image=? WHERE id = "
					+ user.getId();
			int changed = jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getEmail(),
					user.getTelNumber(), user.getBirthdate(), user.getLogin(), user.getPassword(), user.getImage());
			if (changed == 1) {
				return user;
			}
			throw new EntityNotFoundException("User with id " + user.getId() + " not found");

		}
	}

	@Override
	public boolean delete(long id) throws EntityNotFoundException {
		int changed = jdbcTemplate.update("DELETE FROM user WHERE id = " + id);
		return changed == 1;
	}

	private class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getLong("id"));
			user.setName(rs.getString("name"));
			user.setSurname(rs.getString("surname"));
			user.setEmail(rs.getString("email"));
			user.setTelNumber(rs.getString("tel_number"));
			user.setBirthdate(rs.getTimestamp("birthdate").toLocalDateTime().toLocalDate());
			// asi netreba login a password, neviem som confusion
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			user.setImage(rs.getBlob("image"));

			return user;
		}
	}
}
