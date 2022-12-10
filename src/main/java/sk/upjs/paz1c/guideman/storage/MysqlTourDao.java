package sk.upjs.paz1c.guideman.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
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
	public List<Tour> getAll() throws NullPointerException {
		return jdbcTemplate.query("SELECT id, title, bio, max_slots, location_id, user_id, image FROM tour",
				new TourRowMapper());
	}

	@Override
	public List<Tour> getAllToursByGuideman(Long guidemanId) throws EntityNotFoundException {
		String sql = "SELECT id, title, bio, max_slots, location_id, user_id, image FROM tour " + "WHERE user_id = "
				+ guidemanId;
		try {
			return jdbcTemplate.query(sql, new TourRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("User with id " + guidemanId + " not found");
		}
	}

	@Override
	public List<Tour> getAllToursByLocation(Long locationId) throws EntityNotFoundException {
		String sql = "SELECT id, title, bio, max_slots, location_id, user_id, image FROM tour " + "WHERE location_id = "
				+ locationId;
		try {
			return jdbcTemplate.query(sql, new TourRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Location with id " + locationId + " not found");
		}
	}
	
	@Override
	public Tour getById(long id) throws EntityNotFoundException {
		String sql = "SELECT id, title, bio, max_slots, location_id, user_id, image FROM tour " + "WHERE id = " + id;
		try {
			return jdbcTemplate.queryForObject(sql, new TourRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Location with id " + id + " not found");
		}
	}
	
	// mozu sa v liste opakovat pretoze jedna tour moze mat viac eventov
	// a my potom budeme chciet vediet vypisat vsetky toury aj s eventami
	// az v aplikacii sa bude priradzovat tour z listu ku eventu z listu
	@Override
	public List<Tour> getAllToursFromPast(Long userId) throws EntityNotFoundException{
		String sql = "SELECT t.id, t.title, t.bio, t.max_slots, t.location_id, t.user_id, t.image FROM tour t "
				+ "JOIN event e ON e.tour_id = t.id "
				+ "JOIN user_has_event uhe ON e.id = uhe.event_id "
				+ "WHERE uhe.user_id = " + userId + " AND e.date_of_tour < CURRENT_DATE() "
				+ "ORDER BY t.id";
		try {
			return jdbcTemplate.query(sql, new TourRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("User with id " + userId + " not found");
		}
	}
	
	// mozu sa v liste opakovat pretoze jedna tour moze mat viac eventov
	// a my potom budeme chciet vediet vypisat vsetky toury aj s eventami
	// az v aplikacii sa bude priradzovat tour z listu ku eventu z listu
	
	// zobrazuje aj tours ktore sa uskutocnia dnes
	@Override
	public List<Tour> getAllToursFromFuture(Long userId) throws EntityNotFoundException{
		String sql = "SELECT t.id, t.title, t.bio, t.max_slots, t.location_id, t.user_id, t.image FROM tour t "
				+ "JOIN event e ON e.tour_id = t.id "
				+ "JOIN user_has_event uhe ON e.id = uhe.event_id "
				+ "WHERE uhe.user_id = " + userId + " AND e.date_of_tour > CURRENT_DATE() "
				+ "ORDER BY t.id";
		try {
			return jdbcTemplate.query(sql, new TourRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("User with id " + userId + " not found");
		}
	}
	
	@Override
	public List<Tour> getAllToursWhereIAmGuideman(Long userId) throws EntityNotFoundException{
		String sql = "SELECT t.id, t.title, t.bio, t.max_slots, t.location_id, t.user_id, t.image FROM tour t "
				+ "JOIN event e ON e.tour_id = t.id "
				+ "WHERE t.user_id = " + userId
				+ " ORDER BY t.id";
		try {
			return jdbcTemplate.query(sql, new TourRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("User with id " + userId + " not found");
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
		if (tour.getLocationId() == null) {
			throw new NullPointerException("Location cannot be null");
		}
		if (tour.getGuidemanId() == null) {
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
			values.put("location_id", tour.getLocationId());
			values.put("user_id", tour.getGuidemanId());
			values.put("image", tour.getImage());

			long id = sjdbInsert.executeAndReturnKey(values).longValue();
			return new Tour(id, tour.getTitle(), tour.getBio(), tour.getMaxSlots(), tour.getLocationId(),
					tour.getGuidemanId(), tour.getImage());

		} else { // UPDATE
			String sql = "UPDATE tour SET title=?, bio=?, max_slots=?, location_id=?, user_id=?, image=? WHERE id = "
					+ tour.getId();
			int changed = jdbcTemplate.update(sql, tour.getTitle(), tour.getBio(), tour.getMaxSlots(),
					tour.getLocationId(), tour.getGuidemanId(), tour.getImage());
			if (changed == 1) {
				return tour;
			}
			throw new EntityNotFoundException("Tour with id " + tour.getId() + " not found");
		}
	}
	

//	@Override
//	public boolean delete(long tourId) {
//		List<Event> allEvents = DaoFactory.INSTANCE.getEventDao().getAllByTour(tourId);
//		for (Event e : allEvents) {
//			int changed1 = jdbcTemplate.update("DELETE FROM user_has_event uhe WHERE uhe.event_id = " + e.getId());
//			int changed2 = jdbcTemplate.update("DELETE FROM event WHERE id = " + e.getId());
//			// bude to 1 ak v uhe sa moze ale aj nemusi nic vymazat?
//			// surroundnut by try catch
//			if (changed1 == 1 && changed2 == 1) {
//				continue;
//			} else {
//				return false;
//			}
//		}
//		String sql = "DELETE FROM tour WHERE id = " + tourId;
//		int changed = jdbcTemplate.update(sql);
//		return changed == 1;
//	}

	@Override
	public boolean delete(long tourId) throws EntityNotFoundException {
		int changed = 0;
		try {
			changed = jdbcTemplate.update("DELETE FROM tour WHERE id = " + tourId);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Tour with id " + tourId + " not in DB");
		}
		return changed == 1;
	}
	
	private class TourRowMapper implements RowMapper<Tour> {

		@Override
		public Tour mapRow(ResultSet rs, int rowNum) throws SQLException {
			Tour tour = new Tour();
			tour.setId(rs.getLong("id"));
			tour.setTitle(rs.getString("title"));
			tour.setBio(rs.getString("bio"));
			tour.setLocationId(rs.getLong("location_id"));
			tour.setGuidemanId(rs.getLong("user_id"));
			tour.setImage(rs.getBlob("image"));
			return tour;
		}
	}
}
