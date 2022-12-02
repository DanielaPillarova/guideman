package sk.upjs.paz1c.guideman.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MysqlEventDao implements EventDao {

	private JdbcTemplate jdbcTemplate;
	
	public MysqlEventDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Integer> getRatings(Long eventId) {
		String sql = "SELECT rating FROM user_has_event WHERE event_id = " + eventId;
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
	public List<String> getReviews(Long eventId) {
		String sql = "SELECT review FROM user_has_event WHERE event_id = " + eventId;
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
	
	
	
//	@Override
//	public List<Event> getAllByTour(long tourId) {
//		String sql = "SELECT id, date_of_tour, duration, price, rating, review FROM event "
//				+ "LEFT JOIN user_has_event uhe ON event.id = uhe.event_id "
//				+ "WHERE tour_id = ";
//				//+ tour.getId();
//		
//		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Event>>() {
//
//			@Override
//			public List<Event> extractData(ResultSet rs) throws SQLException, DataAccessException {
//				List<Event> events = new ArrayList<>();
//				
//				Event lastEvent = null;
//				while(rs.next()) {
//					long id = rs.getLong("id");
//					if (lastEvent == null || lastEvent.getId() != id) {
//						lastEvent = new Event();
//						lastEvent.setId(id);
//						lastEvent.setDateOfTour(rs.getTimestamp("date_of_tour").toLocalDateTime());
//						lastEvent.setDuration(rs.getTimestamp("duration").toLocalDateTime().toLocalTime());
//						lastEvent.setPrice(rs.getFloat("price"));
//						lastEvent
//					}
//				}
//				
//				//List<User> allTourists = DaoFactory.INSTANCE.getUserDao().getAllTourists(??? event id);
//						
//				// potrebujem vratit vsetky eventy podla 1 tour id, kazdy event ma mat aj svoj list so svojimi tourists(users)
//				// v triede tour bude matoda, ktora vrati vsetky tour s konkretnym userom(guidemanom)
//				
//				return null;
//			}
//			
//		};
//	}
	
	
	public Event save(Event event) throws NullPointerException, NegativeNumberException {
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
//		if (event.getTour() == null || event.getTour().getId() == null) {
//			throw new NullPointerException("Cannot save event without a tour");
//		}
		
		if (event.getId() == null) { // INSERT
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("event");
			simpleJdbcInsert.usingColumns("date_of_tour", "duration", "price", "tour_id");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");
			
			Map<String, Object> values = new HashMap<>();
			values.put("date_of_tour", event.getDateOfTour());
			values.put("duration", event.getDuration());
			values.put("price", event.getPrice());
//			values.put("tour_id", event.getTour().getId());
			
			long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
			Event event2 = new Event(id, event.getDateOfTour(), event.getDuration(), event.getPrice(), event.getTour());
			
			// saveTourists
			
			return event2;
			
		} else { // UPDATE
			// TODO
			
		}
		
		return event;	
	}
	
	// TODO
	private void saveTourists(Event event) {
		
	}
	
	
}
