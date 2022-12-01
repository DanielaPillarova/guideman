package sk.upjs.paz1c.guideman.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MysqlTourDao implements TourDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlTourDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(long id) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

}
