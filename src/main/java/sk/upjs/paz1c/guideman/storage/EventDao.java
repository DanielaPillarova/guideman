package sk.upjs.paz1c.guideman.storage;

import java.util.List;

public interface EventDao {

	List<Integer> getRatings(Long eventId);

	List<String> getReviews(Long eventId);

//	List<Event> getAllByTour(long tourId);

}
