package sk.upjs.paz1c.guideman.storage;

import java.util.List;
import java.util.NoSuchElementException;

public interface LocationDao {

	List<Location> getAll();
	
	Location getById(long id) throws NoSuchElementException;
	
	Location save(Location location) throws NullPointerException, EntityNotFoundException;
	
	Location delete(Long id) throws EntityNotFoundException;
	
}
