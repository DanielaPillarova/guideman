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
		tour.setMaxSlots(20);
		tour.setLocation(1);
		tour.setGuideman(1);
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
		assertTrue(tours.size() == 3);
	}

	@Test
	void getAllToursByGuideman() {
		List<Tour> toursByGuideman = DaoFactory.INSTANCE.getTourDao().getAllToursByGuideman(2);
		assertTrue(toursByGuideman.size() == 1);
	}

	@Test
	void insertTest() throws NullPointerException {
		assertThrows(NullPointerException.class, () -> tourDao.save(null), "Cannot save null");

		Tour newLocation = tourDao.getById(savedTour.getId());
		assertEquals(size, tourDao.getAll().size());
		assertEquals(savedTour.getTitle(), newLocation.getTitle());

		assertThrows(NullPointerException.class, () -> tourDao.save(new Tour("Title", "bio", 50, null, 1, null)),
				"Location_id cannot be null");

		assertThrows(NullPointerException.class, () -> tourDao.save(new Tour("Title", "bio", 50, 1, null, null)),
				"Guideman (user_id) cannot be null");

		assertThrows(NullPointerException.class, () -> tourDao.save(new Tour(null, "bio", 50, 1, 1, null)),
				"Title cannot be null");

		assertThrows(NullPointerException.class, () -> tourDao.save(new Tour("Title", "bio", null, 1, 1, null)),
				"Max slots cannot be null");

	}

	@Test
	void updateTest() throws NullPointerException {
		Tour updated = new Tour(savedTour.getId(), "Changed title", "bio", 2, 1, 1, null);
		int sizeUpdate = tourDao.getAll().size();
		assertEquals(sizeUpdate, tourDao.getAll().size());
		Tour fromDB = tourDao.getById(updated.getId());
		assertEquals(updated.getId(), fromDB.getId());
		assertThrows(EntityNotFoundException.class,
				() -> tourDao.save(new Tour(-1L, "Changed title", "bio", 2, 1, 1, null)));

	}

	@Test
	void deleteTest() {
		Tour tourToDelete = new Tour();
		tourToDelete.setTitle("title");
		tourToDelete.setBio("bio");
		tourToDelete.setMaxSlots(3);
		tourToDelete.setLocation(1);
		tourToDelete.setGuideman(2);
		tourToDelete.setImage(null);

		Tour saved = tourDao.save(tourToDelete);
		int sizeDelete = tourDao.getAll().size();
		tourDao.delete(saved.getId());
		assertEquals(sizeDelete - 1, tourDao.getAll().size());
	}

}
