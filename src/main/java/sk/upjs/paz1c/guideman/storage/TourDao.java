package sk.upjs.paz1c.guideman.storage;

import java.util.List;
import java.util.NoSuchElementException;

public interface TourDao {

	List<Tour> getAll();

	Tour getById(long id) throws NoSuchElementException;

	Tour save(Tour tour) throws NullPointerException, EntityNotFoundException;

	boolean delete(long id) throws EntityNotFoundException;

}
