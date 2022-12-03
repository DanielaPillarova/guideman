package sk.upjs.paz1c.guideman.storage;

import java.util.List;
import java.util.NoSuchElementException;

public interface TourDao {

	Tour save(Tour tour) throws NullPointerException, EntityNotFoundException;

	boolean delete(long id) throws EntityNotFoundException;

}
