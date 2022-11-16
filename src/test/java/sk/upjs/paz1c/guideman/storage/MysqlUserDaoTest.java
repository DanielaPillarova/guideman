package sk.upjs.paz1c.guideman.storage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MysqlUserDaoTest {

	private UserDao userDao;
	private User savedUser;

	public MysqlUserDaoTest() {
		DaoFactory.INSTANCE.testing();
		userDao = DaoFactory.INSTANCE.getUserDao();
	}

	@BeforeEach
	void setUp() throws Exception {
		User user = new User();
		user.setName("Test User");
		user.setSurname("Test User");
		user.setEmail("Test User");
		// naschval bez tel number
		// user.setBirthdate("2022-02-02");

		// todo : dokoncit setup a teardown aby sa mohla otestovat metoda save

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void getAllTest() {
		List<User> users = userDao.getAll();
		assertTrue(users.size() > 0);
		assertNotNull(users.get(0).getId());
		assertNotNull(users.get(0).getName());
		assertNotNull(users.get(0).getSurname());
	}

	@Test
	void getByIdTest() {
		User fromDB = userDao.getById(savedUser.getId());
		assertEquals(savedUser.getId(), fromDB.getId());
		assertThrows(EntityNotFoundException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				userDao.getById(-1L);
			}
		});

	}

}
