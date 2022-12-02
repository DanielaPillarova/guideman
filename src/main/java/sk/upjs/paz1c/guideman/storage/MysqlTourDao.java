package sk.upjs.paz1c.guideman.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MysqlTourDao implements TourDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlTourDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Tour> getAll() {
		return jdbcTemplate.query("SELECT id, title, bio, maxSlots, image , guideman FROM tour", new TourRowMapper());

	}

	@Override
	public Tour getById(long id) throws NoSuchElementException {
		String sql = "SELECT id, title, bio, maxSlots, image , guideman FROM tour " + "WHERE id = " + id;
		try {
			return jdbcTemplate.queryForObject(sql, new TourRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Tour with id " + id + " not found");
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
//		if (tour.getMaxSlots == null) {
//			throw new NullPointerException("Max slots cannot be null");
//		}
		if (tour.getGuideman() == null) {
			throw new NullPointerException("Guideman cannot be null");
		}

		if (tour.getId() == null) { // INSERT
			SimpleJdbcInsert sjdbInsert = new SimpleJdbcInsert(jdbcTemplate);
			sjdbInsert.withTableName("tour");
			sjdbInsert.usingGeneratedKeyColumns("id");

			sjdbInsert.usingColumns("title", "bio", "maxSlots", "image", "guidemanId");

			Map<String, Object> values = new HashMap<>();
			values.put("title", tour.getTitle());
			values.put("bio", tour.getBio());
			values.put("maxSlots", tour.getMaxSlots());
			values.put("image", tour.getImage());
			values.put("guideman", tour.getGuideman());

			long id = sjdbInsert.executeAndReturnKey(values).longValue();
			return new Tour(id, tour.getTitle(), tour.getBio(), tour.getMaxSlots(), tour.getImage(),
					tour.getGuideman());

		} else { // UPDATE
			String sql = "UPDATE tour SET title=?, bio=?, maxSlots=?, image=?, guidemanId=? WHERE id = " + tour.getId();
			int changed = jdbcTemplate.update(sql, tour.getTitle(), tour.getBio(), tour.getMaxSlots(), tour.getImage(),
					tour.getGuideman());
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
			tour.setMaxSlots(rs.getInt("maxSlots"));
			tour.setImage(rs.getBlob("image"));
			tour.setGuideman(rs.getLong("guideman"));
			return tour;
		}
	}

}
