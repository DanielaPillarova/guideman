package sk.upjs.paz1c.guideman.storage;

import java.util.List;
import java.util.NoSuchElementException;

public interface UserDao {

	List<User> getAll();

	User getById(long id) throws NoSuchElementException;

	User save(User user) throws NullPointerException, EntityNotFoundException;

	boolean delete(long id) throws EntityNotFoundException;

	List<User> getAllTourists(long eventId);

	List<User> getAllFavouriteGuidemans(long userId);

	void saveRating(Long userId, Long eventId, Integer rating) throws EntityNotFoundException;

	void saveReview(Long userId, Long eventId, String review) throws EntityNotFoundException;

	void deleteRating(Long userId, Long eventId) throws EntityNotFoundException;

	void deleteReview(Long userId, Long eventId) throws EntityNotFoundException;

	User getUserByUsername(String username) throws EntityNotFoundException;

}
