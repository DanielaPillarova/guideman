package sk.upjs.paz1c.guideman.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MysqlEventDao implements EventDao {

	private JdbcTemplate jdbcTemplate;
	
	public MysqlEventDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Integer> getRatings(Event event) {
		String sql = "SELECT rating FROM user_has_event WHERE event_id = " + event.getId();
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Integer>>() {

			@Override
			public List<Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Integer> ratings = new ArrayList<>();
				while (rs.next()) {
					Integer rat = rs.getInt("rating");
					if (rat != null) {
						if (rat >= 0 && rat <= 5) {
							ratings.add(rat);
						}
					}
				}
				return ratings;
			}
		});
	}
	
	@Override
	public List<String> getReviews(Event event) {
		String sql = "SELECT review FROM user_has_event WHERE event_id = " + event.getId();
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<String>>() {

			@Override
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> reviews = new ArrayList<>();
				while (rs.next()) {
					String rev = rs.getString("review");
					if (rev != null) {
						reviews.add(rev);
					}
				}
				return reviews;
			}
		});
	}
	
	@Override
	public List<Event> getAllByTour(Long tourId) {
		String sql = "SELECT id, date_of_tour, duration, price, tour_id FROM event "
				+ "LEFT JOIN user_has_event uhe ON event.id = uhe.event_id "
				+ "WHERE tour_id = " + tourId;
		
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Event>>() {

			@Override
			public List<Event> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Event> events = new ArrayList<>();
				
				Event lastEvent = null;
				while(rs.next()) {
					long id = rs.getLong("id");
					if (lastEvent == null || lastEvent.getId() != id) {
						lastEvent = new Event();
						lastEvent.setId(id);
						lastEvent.setDateOfTour(rs.getTimestamp("date_of_tour").toLocalDateTime());
						lastEvent.setDuration(rs.getTimestamp("duration").toLocalDateTime().toLocalTime());
						lastEvent.setPrice(rs.getDouble("price"));
						lastEvent.setTourId(rs.getLong("tour_id"));
						// musi mat tento event aj parametre z tabulky uhe?
						lastEvent.setTourists(DaoFactory.INSTANCE.getUserDao().getAllTourists(id));
						// nie je to blbost?
						lastEvent.setRatings(getRatings(lastEvent));
						lastEvent.setReviews(getReviews(lastEvent));
						//
						events.add(lastEvent);
					}
				}
				return events;
			}
			
		});
	}
	
	@Override
	public Event save(Event event) throws NullPointerException, NegativeNumberException, NoSuchElementException {
		if (event == null) {
			throw new NullPointerException("Cannot save null");
		}
		if (event.getDateOfTour() == null) {
			throw new NullPointerException("Cannot save event without date and time of the tour");
		}
		if (event.getDuration() == null) {
			throw new NullPointerException("Cannot save event without duration");
		}
		// joj nefungujeeee
//		if (event.getPrice() == null) {	
//		}
		if (event.getPrice() < 0) {
			throw new NegativeNumberException("Cannot save negative price");
		}
		if (event.getTourId() == null) {
			throw new NullPointerException("Cannot save event without a tour");
		}
		
		if (event.getId() == null) { // INSERT
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("event");
			simpleJdbcInsert.usingColumns("date_of_tour", "duration", "price", "tour_id");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");
			
			Map<String, Object> values = new HashMap<>();
			values.put("date_of_tour", event.getDateOfTour());
			values.put("duration", event.getDuration());
			values.put("price", event.getPrice());
			values.put("tour_id", event.getTourId());
			
			long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
			Event event2 = new Event(id, event.getDateOfTour(), event.getDuration(), event.getPrice(), event.getTourId());
			
			saveTourists(event2);
			return event2;
			
		} else { // UPDATE
			String sql = "UPDATE event SET date_of_tour=?, duration=?, price=?, tour_id=? " + "WHERE id=?";
			int changed = jdbcTemplate.update(sql, event.getDateOfTour(), event.getDuration(), 
					event.getPrice(), event.getTourId(), event.getId());
			if (changed == 1) {
				String sqlDelete = "DELETE FROM user_has_event " + "WHERE event_id= " + event.getId();
				jdbcTemplate.update(sqlDelete);
				saveTourists(event);
				return event;
			} else {
				throw new NoSuchElementException("No event with id " + event.getId() + " in DB");
			}
			
		}
	}
	
	private void saveTourists(Event event) {
		if (event.getTourists().isEmpty()) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO user_has_event (user_id, event_id) VALUES ");
		for (User u : event.getTourists()) {
			if (u == null || u.getId() == null) {
				throw new NullPointerException("Event has null users or user with null id " + event.getTourists());
			}
			sb.append("(").append(u.getId());
			sb.append(", ").append(event.getId());
			sb.append("), ");
		}
		String sql = sb.substring(0, sb.length() - 1);
		jdbcTemplate.update(sql);
	}
	
	@Override
	public boolean delete(Long eventId) throws EntityNotFoundException {
		String sqlUhe = "DELETE FROM user_has_event uhe WHERE uhe.event_id = " + eventId;
		String sqlE = "DELETE FROM event WHERE event.id = " + eventId;

		int changedUhe;
		int changedE;
		try {
			changedUhe = jdbcTemplate.update(sqlUhe);
			changedE = jdbcTemplate.update(sqlE);
		} catch (DataIntegrityViolationException e) {
			throw new EntityNotFoundException("Event with id: " + eventId + " not found in DB");
		}
		return (changedUhe == 1 && changedE == 1);
	}
	
	// TODO
	// metoda na savovanie ratingu a reviewu, mozno bude v userovi?
	
	
}
