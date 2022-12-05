package sk.upjs.paz1c.guideman.storage;

import java.util.List;
import java.util.NoSuchElementException;

public interface TourDao {

	Tour getById(long id) throws NullPointerException;

	List<Tour> getAllToursByGuideman(Integer guideman) throws NullPointerException;

	List<Tour> getAll() throws NullPointerException;

	Tour save(Tour tour) throws NullPointerException, EntityNotFoundException;

	boolean delete(long id) throws EntityNotFoundException;

}
