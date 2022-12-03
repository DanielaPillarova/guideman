package sk.upjs.paz1c.guideman.storage;

import java.util.List;
import java.util.NoSuchElementException;

public interface TourDao {

	Tour getById(long id);

	List<Tour> getAllToursByGuideman(Integer guideman) throws NullPointerException;

	List<Tour> getAll();

	Tour save(Tour tour) throws NullPointerException, EntityNotFoundException;

	boolean delete(long id) throws EntityNotFoundException;

}
