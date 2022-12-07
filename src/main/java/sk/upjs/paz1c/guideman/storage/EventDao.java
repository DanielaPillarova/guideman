package sk.upjs.paz1c.guideman.storage;

import java.util.List;
import java.util.NoSuchElementException;

public interface EventDao {

	List<Integer> getRatings(Long eventId);

	List<String> getReviews(Long eventId);
	
	List<Event> getAllByTour(Long tourId);

	Event save(Event event) throws NullPointerException, NegativeNumberException, NoSuchElementException;

	boolean delete(Long eventId) throws EntityNotFoundException;

}
