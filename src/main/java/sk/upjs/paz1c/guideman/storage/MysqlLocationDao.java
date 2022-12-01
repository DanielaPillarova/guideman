package sk.upjs.paz1c.guideman.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MysqlLocationDao implements LocationDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlLocationDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Location> getAll() {
		return jdbcTemplate.query("SELECT id, country, city, street, street_number FROM location",
				new LocationRowMapper());

	}

	@Override
	public Location getById(long id) throws EntityNotFoundException {
		String sql = "SELECT id, country, city, street, street_number FROM location " + "WHERE id = " + id;
		try {
			return jdbcTemplate.queryForObject(sql, new LocationRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Location with id " + id + " not found");
		}
	}

	@Override
	public Location save(Location location) {
		if (location == null) { // INSERT
			throw new NullPointerException("Cannot save null");
		}
		if (location.getCountry() == null) {
			throw new NullPointerException("Country cannot be null");
		}
		if (location.getCity() == null) {
			throw new NullPointerException("City cannot be null");
		}
		if (location.getStreet() == null) {
			throw new NullPointerException("Street address cannot be null");
		}

		if (location.getId() == null) { // INSERT
			SimpleJdbcInsert sjdbInsert = new SimpleJdbcInsert(jdbcTemplate);
			sjdbInsert.withTableName("location");
			sjdbInsert.usingGeneratedKeyColumns("id");

			sjdbInsert.usingColumns("country", "city", "street", "street_number");

			Map<String, Object> values = new HashMap<>();
			values.put("country", location.getCountry());
			values.put("city", location.getCity());
			values.put("street", location.getStreet());
			values.put("street_number", location.getStreet_number());

			long id = sjdbInsert.executeAndReturnKey(values).longValue();
			return new Location(id, location.getCountry(), location.getCity(), location.getStreet(),
					location.getStreet_number());

		} else { // UPDATE
			String sql = "UPDATE location SET country=?, city=?, street=?, street_number=? WHERE id = "
					+ location.getId();
			int changed = jdbcTemplate.update(sql, location.getCountry(), location.getCity(), location.getStreet(),
					location.getStreet_number());
			if (changed == 1) {
				return location;
			}
			throw new EntityNotFoundException("Location with id " + location.getId() + " not found");
		}
	}

	@Override
	public boolean delete(long id) throws EntityNotFoundException {
		String sql = "DELETE FROM location WHERE id = " + id;
		int changed = jdbcTemplate.update(sql);
		return changed == 1;
	}

	private class LocationRowMapper implements RowMapper<Location> {

		@Override
		public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
			Location location = new Location();
			location.setId(rs.getLong("id"));
			location.setCountry(rs.getString("country"));
			location.setCity(rs.getString("city"));
			location.setStreet(rs.getString("street"));
			location.setStreet_number(rs.getLong("street_number"));

			return location;
		}
	}

	@Override
	public List<Location> getAllByCountry(String searchedCountry) throws EntityNotFoundException {
		String sql = "SELECT id, country, city, street, street_number FROM location WHERE country like " + "'" + searchedCountry + "'";
		return jdbcTemplate.query(sql,
				new LocationRowMapper());
	}

}
