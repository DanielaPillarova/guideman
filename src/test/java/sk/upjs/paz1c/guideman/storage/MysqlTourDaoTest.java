package sk.upjs.paz1c.guideman.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MysqlTourDaoTest {

	private TourDao tourDao;
	private Tour tour;
	private Tour savedTour;
	private int size;

	public MysqlTourDaoTest() {
		DaoFactory.INSTANCE.testing();
		tourDao = DaoFactory.INSTANCE.getTourDao();
	}

	@BeforeEach
	void setUp() throws Exception {
		tour = new Tour();
		tour.setTitle("Nejaky title");
		tour.setBio("Nejake bio");
		tour.setMaxSlots(20L);
		tour.setLocationId(1L);
		tour.setGuidemanId(1L);
		tour.setImage(null);

		savedTour = tourDao.save(tour);
		size = tourDao.getAll().size();
	}

	@AfterEach
	void tearDown() throws Exception {
		tourDao.delete(savedTour.getId());
	}

	@Test
	void testGetAll() {
		List<Tour> tours = tourDao.getAll();
		assertEquals(tours.size(), 4);
	}

	@Test
	void getAllToursByGuideman() {
		List<Tour> toursByGuideman = tourDao.getAllToursByGuideman(2L);
		assertTrue(toursByGuideman.size() == 1);
	}

	// checknut db
	@Test
	void getAllToursFromPastTest() {
		List<Tour> tours = tourDao.getAllToursFromPast(3l);
		assertTrue(tours.size() == 2);
	}
	
	// checknut db
	@Test
	void getAllToursFromFutureTest() {
		List<Tour> tours = tourDao.getAllToursFromFuture(3l);
		assertTrue(tours.size() == 4);
	}
	
	// checknut db
	@Test
	void getAllToursWhereIAmGuidemanTest() {
		List<Tour> tours = tourDao.getAllToursWhereIAmGuideman(1l);
		assertTrue(tours.size() == 5);
	}
	
	@Test
	void insertTest() throws NullPointerException {
		assertThrows(NullPointerException.class, () -> tourDao.save(null), "Cannot save null");

		Tour newLocation = tourDao.getById(savedTour.getId());
		assertEquals(size, tourDao.getAll().size());
		assertEquals(savedTour.getTitle(), newLocation.getTitle());

		assertThrows(NullPointerException.class, () -> tourDao.save(new Tour("Title", "bio", 50L, null, 1L, null)),
				"Location_id cannot be null");

		assertThrows(NullPointerException.class, () -> tourDao.save(new Tour("Title", "bio", 50L, 1L, null, null)),
				"Guideman (user_id) cannot be null");

		assertThrows(NullPointerException.class, () -> tourDao.save(new Tour(null, "bio", 50L, 1L, 1L, null)),
				"Title cannot be null");

		assertThrows(NullPointerException.class, () -> tourDao.save(new Tour("Title", "bio", null, 1L, 1L, null)),
				"Max slots cannot be null");

	}

	@Test
	void updateTest() throws NullPointerException {
		Tour updated = new Tour(savedTour.getId(), "Changed title", "bio", 2L, 1L, 1L, null);
		int sizeUpdate = tourDao.getAll().size();
		assertEquals(sizeUpdate, tourDao.getAll().size());
		Tour fromDB = tourDao.getById(updated.getId());
		assertEquals(updated.getId(), fromDB.getId());
		assertThrows(EntityNotFoundException.class,
				() -> tourDao.save(new Tour(-1L, "Changed title", "bio", 2L, 1L, 1L, null)));

	}

	@Test
	void deleteTest() {
		Tour tourToDelete = new Tour();
		tourToDelete.setTitle("title");
		tourToDelete.setBio("bio");
		tourToDelete.setMaxSlots(3L);
		tourToDelete.setLocationId(1L);
		tourToDelete.setGuidemanId(2L);
		tourToDelete.setImage(null);

		Tour saved = tourDao.save(tourToDelete);
		int sizeDelete = tourDao.getAll().size();
		tourDao.delete(saved.getId());
		assertEquals(sizeDelete - 1, tourDao.getAll().size());
	}

}
