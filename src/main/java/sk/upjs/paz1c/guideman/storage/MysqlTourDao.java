package sk.upjs.paz1c.guideman.storage;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MysqlTourDao implements TourDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlTourDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Tour> getAllToursByGuideman(User guideman) {
		String sql = "SELECT id, title, bio, max_slots, location_id, user_id, image FROM tour "
				+ "WHERE user_id = " + guideman.getId();
		
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Tour>>() {

			@Override
			public List<Tour> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Tour> allTours = new ArrayList<>();
				Tour tour = null;
				while (rs.next()) {
					tour = new Tour();
					tour.setId(rs.getLong("id"));
					tour.setTitle(rs.getString("title"));
					tour.setBio(rs.getString("bio"));
					tour.setMaxSlots(rs.getInt("max_slots"));
//					tour.setLocation();
				}
				
				
				return null;
			}
			
		});

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

			sjdbInsert.usingColumns("title", "bio", "maxSlots", "image", "user_id", "location_id");

			Map<String, Object> values = new HashMap<>();
			values.put("title", tour.getTitle());
			values.put("bio", tour.getBio());
			values.put("maxSlots", tour.getMaxSlots());
			values.put("image", tour.getImage());
			values.put("user_id", tour.getGuideman());
			values.put("location_id", tour.getLocation());

			long id = sjdbInsert.executeAndReturnKey(values).longValue();
			return new Tour(id, tour.getTitle(), tour.getBio(), tour.getMaxSlots(), tour.getImage(), tour.getGuideman(),
					tour.getLocation());

		} else { // UPDATE
			String sql = "UPDATE tour SET title=?, bio=?, maxSlots=?, image=?, user_id=?, location_id=? WHERE id = "
					+ tour.getId();
			int changed = jdbcTemplate.update(sql, tour.getTitle(), tour.getBio(), tour.getMaxSlots(), tour.getImage(),
					tour.getGuideman(), tour.getLocation());
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

}
