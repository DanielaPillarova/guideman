package sk.upjs.paz1c.guideman.storage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MysqlLocationDaoTest {

	private LocationDao locationDao;
	private Location location;
	private Location savedLocation;
	private int size;

	public MysqlLocationDaoTest() {
		DaoFactory.INSTANCE.testing();
		locationDao = DaoFactory.INSTANCE.getLocationDao();
	}

	@BeforeEach
	void setUp() throws Exception {
		location = new Location();
		location.setCountry("California");
		location.setCity("Los Angeles");
		location.setStreet("Main street");
		location.setStreet_number((long) 1);

		size = locationDao.getAll().size(); // pocet userov pred pridanim noveho
		savedLocation = locationDao.save(location);
	}

	@AfterEach
	void tearDown() throws Exception {
		locationDao.delete(savedLocation.getId());
	}

	@Test
	void testGetAll() {
		List<Location> locations = locationDao.getAll();
		assertTrue(locations.size() > 0);
		assertNotNull(locations.get(0).getId());
		assertNotNull(locations.get(0).getCountry());
		assertNotNull(locations.get(0).getCity());
		assertNotNull(locations.get(0).getStreet());
		assertNotNull(locations.get(0).getStreet_number());
	}

	@Test
	void testGetById() {
		Location fromDB = locationDao.getById(savedLocation.getId());
		assertEquals(savedLocation.getId(), fromDB.getId());
		assertThrows(EntityNotFoundException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				locationDao.getById(-1L);
			}
		});
	}

	// TODO insert, update a delete

	@Test
	void insertTest() throws NullPointerException {
		assertThrows(NullPointerException.class, () -> locationDao.save(null), "Cannot save null");

		Location newLocation = locationDao.getById(savedLocation.getId());
		assertEquals(size + 1, locationDao.getAll().size());
		assertEquals(savedLocation.getCountry(), newLocation.getCountry());

		// ???
//		assertThrows(NullPointerException.class,
//				() -> locationDao.save(new Location("California", "Prakovce", "Hlavna", (long) 1)),
//				"Location has already been created");
		// ???

		assertThrows(NullPointerException.class, () -> locationDao.save(new Location(null, "City", "Street", (long) 2)),
				"Country cannot be null");

		assertThrows(NullPointerException.class,
				() -> locationDao.save(new Location("Country", null, "Street", (long) 2)), "City cannot be null");

		assertThrows(NullPointerException.class,
				() -> locationDao.save(new Location("Country", "City", null, (long) 2)), "Street cannot be null");
	}

	@Test
	void updateTest() {
		Location updated = new Location(savedLocation.getId(), "Changed Country", "city", "street", (long) 1);
		int sizeUpdate = locationDao.getAll().size();
		assertEquals(sizeUpdate, locationDao.getAll().size());
		Location fromDB = locationDao.getById(updated.getId());
		assertEquals(updated.getId(), fromDB.getId());
		assertThrows(EntityNotFoundException.class,
				() -> locationDao.save(new Location(-1L, "Changed Country", "city", "street", (long) 1)));
	}

	@Test
	void deleteTest() {
		Location locationToDelete = new Location();
		locationToDelete.setCountry("Delete Country");
		locationToDelete.setCity("Delete City");
		locationToDelete.setStreet("Delete Street");
		locationToDelete.setStreet_number((long) 1);

		Location saved = locationDao.save(locationToDelete);
		int sizeDelete = locationDao.getAll().size();
		locationDao.delete(saved.getId());
		assertEquals(sizeDelete - 1, locationDao.getAll().size());
	}

}
