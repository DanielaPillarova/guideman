package sk.upjs.paz1c.guideman.storage;

import java.util.List;
import java.util.NoSuchElementException;

public interface UserDao {

	List<User> getAll();

	User getById(long id) throws NoSuchElementException;

	// okomentovanie metody
	User save(User user) throws NullPointerException, EntityNotFoundException;

	boolean delete(long id) throws EntityNotFoundException;

	List<User> getAllTourists(long eventId);

	List<User> getAllFavouriteGuidemans(long userId);

}
