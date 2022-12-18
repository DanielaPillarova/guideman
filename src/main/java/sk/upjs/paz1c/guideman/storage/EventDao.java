package sk.upjs.paz1c.guideman.storage;

import java.util.List;
import java.util.NoSuchElementException;

public interface EventDao {

	List<Integer> getRating(Long userId, Long eventId);

	List<String> getReview(Long userId, Long eventId);

	List<Integer> getRatings(Long eventId);

	List<String> getReviews(Long eventId);

	List<Event> getAllByTour(Long tourId);

	Event save(Event event) throws NullPointerException, NegativeNumberException, NoSuchElementException;

	boolean delete(Long eventId) throws EntityNotFoundException;

	List<Event> getAllEventsFromPast(Long userId) throws EntityNotFoundException;

	List<Event> getAllEventsFromFuture(Long userId) throws EntityNotFoundException;

	List<Event> getAllEventsWhereIAmGuideman(Long userId) throws EntityNotFoundException;

	List<Event> getAllLetsGoEvents(Long userId) throws EntityNotFoundException;

	Event getById(Long eventId) throws EntityNotFoundException;

	boolean deleteFromUHE(Long eventId) throws EntityNotFoundException;

	List<Event> getAllByMonth(int month);

	List<Event> getAllEventsWithPriceLowerThan(int price);

	List<Event> getAllEventsByCountry(String country);

	List<Event> getAllEventsByGuideman(String name, String surname);

	List<Event> getAll();

}
