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

public class MysqlTourDao implements TourDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlTourDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// getAll

	@Override
	public List<Tour> getAll() throws NullPointerException {
		return jdbcTemplate.query("SELECT id, title, bio, max_slots, location_id, user_id, image FROM tour",
				new TourRowMapper());
	}

	// bude metoda aj pre getAllTours()? ked sa bude chciet vyhladavat bez filtrov v
	// appke
	public List<Tour> getAllToursByGuideman(Integer guideman) {
		String sql = "SELECT id, title, bio, max_slots, location_id, user_id, image FROM tour " + "WHERE user_id = "
				+ guideman;
		return jdbcTemplate.query(sql, new TourRowMapper());
	}

	@Override
	public Tour getById(long id) throws NullPointerException {
		String sql = "SELECT id, title, bio, max_slots, location_id, user_id, image FROM tour " + "WHERE id = " + id;
		try {
			return jdbcTemplate.queryForObject(sql, new TourRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Location with id " + id + " not found");
		}
	}

	@Override
	public Tour save(Tour tour) throws NullPointerException, EntityNotFoundException {
		if (tour == null) { // INSERT
			throw new NullPointerException("Cannot save null");
		}
		if (tour.getTitle() == null) {
			throw new NullPointerException("Title cannot be null");
		}
		if (tour.getMaxSlots() == null) {
			throw new NullPointerException("Max slots cannot be null");
		}
		if (tour.getLocation() == null) {
			throw new NullPointerException("Location cannot be null");
		}
		if (tour.getGuideman() == null) {
			throw new NullPointerException("Guideman cannot be null");
		}

		if (tour.getId() == null) { // INSERT
			SimpleJdbcInsert sjdbInsert = new SimpleJdbcInsert(jdbcTemplate);
			sjdbInsert.withTableName("tour");
			sjdbInsert.usingGeneratedKeyColumns("id");

			sjdbInsert.usingColumns("title", "bio", "max_slots", "location_id", "user_id", "image");

			Map<String, Object> values = new HashMap<>();
			values.put("title", tour.getTitle());
			values.put("bio", tour.getBio());
			values.put("max_slots", tour.getMaxSlots());
			values.put("location_id", tour.getLocation());
			values.put("user_id", tour.getGuideman());
			values.put("image", tour.getImage());

			long id = sjdbInsert.executeAndReturnKey(values).longValue();
			return new Tour(id, tour.getTitle(), tour.getBio(), tour.getMaxSlots(), tour.getLocation(),
					tour.getGuideman(), tour.getImage());

		} else { // UPDATE
			String sql = "UPDATE tour SET title=?, bio=?, max_slots=?, location_id=?, user_id=?, image=? WHERE id = "
					+ tour.getId();
			int changed = jdbcTemplate.update(sql, tour.getTitle(), tour.getBio(), tour.getMaxSlots(),
					tour.getLocation(), tour.getGuideman(), tour.getImage());
			if (changed == 1) {
				return tour;
			}
			throw new EntityNotFoundException("Tour with id " + tour.getId() + " not found");
		}
	}

	@Override
	public boolean delete(long id) {
		String sql = "DELETE FROM tour WHERE id = " + id;
		int changed = jdbcTemplate.update(sql);
		return changed == 1;
	}

	private class TourRowMapper implements RowMapper<Tour> {

		@Override
		public Tour mapRow(ResultSet rs, int rowNum) throws SQLException {
			Tour tour = new Tour();
			tour.setId(rs.getLong("id"));
			tour.setTitle(rs.getString("title"));
			tour.setBio(rs.getString("bio"));
			tour.setLocation(rs.getInt("location_id"));
			tour.setGuideman(rs.getInt("user_id"));
			tour.setImage(rs.getBlob("image"));
			return tour;
		}
	}
}
