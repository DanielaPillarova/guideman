package sk.upjs.paz1c.guideman.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class MysqlEventDao implements EventDao {

	private JdbcTemplate jdbcTemplate;
	
	public MysqlEventDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
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
//				// bude nejaky cyklus pre vsetky eventy
//				List<User> allTourists = DaoFactory.INSTANCE.getUserDao().getAllTourists(??? event id);
//						
//				// potrebujem vratit vsetky eventy podla 1 tour id, kazdy event ma mat aj svoj list so svojimi tourists(users)
//				// v triede tour bude matoda, ktora vrati vsetky tour s konkretnym userom(guidemanom)
//				
//				return null;
//			}
//			
//		};
//	}
	
	
}
