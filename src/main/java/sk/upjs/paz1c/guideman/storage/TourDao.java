package sk.upjs.paz1c.guideman.storage;

import java.util.List;

public interface TourDao {

	Tour getById(long id) throws EntityNotFoundException;

	List<Tour> getAllToursByGuideman(Long guidemanId) throws EntityNotFoundException;

	List<Tour> getAll() throws NullPointerException;

	Tour save(Tour tour) throws NullPointerException, EntityNotFoundException;

	boolean delete(long tourId) throws EntityNotFoundException;

	List<Tour> getAllToursByLocation(Long locationId) throws EntityNotFoundException;

	List<Tour> getAllToursFromPast(Long userId) throws EntityNotFoundException;

	List<Tour> getAllToursFromFuture(Long userId) throws EntityNotFoundException;

	List<Tour> getAllToursWhereIAmGuideman(Long userId) throws EntityNotFoundException;

}
