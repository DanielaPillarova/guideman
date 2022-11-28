package sk.upjs.paz1c.guideman.storage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MysqlLocationDaoTest {

	private LocationDao locationDao;

	public MysqlLocationDaoTest() {
		DaoFactory.INSTANCE.testing();
		locationDao = DaoFactory.INSTANCE.getLocationDao();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
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
	void testGetByIdTEST() {
		Location location = locationDao.getById(1);
		fail("exception EntityNotFound not thrown");
		assertNotNull(location);
	}

	@Test
	void testGetById() {
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				locationDao.getById(-1L);
			}
		});
		Location newLocation = new Location("AMERIKA", "CALIFORNIA", "MAIN_STREET", (long) 69);
		Location savedLocation = locationDao.save(newLocation);
		Location byId = locationDao.getById(savedLocation.getId());
		assertEquals(savedLocation.getId(), byId.getId());
		assertEquals(newLocation.getCountry(), byId.getCountry());
		assertEquals(newLocation.getCity(), byId.getCity());
		assertEquals(newLocation.getStreet(), byId.getStreet());
		assertEquals(newLocation.getStreet_number(), byId.getStreet_number());
		locationDao.delete(savedLocation.getId());
	}
	
}
