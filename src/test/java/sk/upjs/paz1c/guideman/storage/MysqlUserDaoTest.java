package sk.upjs.paz1c.guideman.storage;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.security.crypto.bcrypt.BCrypt;

class MysqlUserDaoTest {

	private UserDao userDao;
//	private User user;
	private User savedUser;
	private int size;

	public MysqlUserDaoTest() {
		DaoFactory.INSTANCE.testing();
		userDao = DaoFactory.INSTANCE.getUserDao();
	}

	@BeforeEach
	void setUp() throws Exception {
		User user = new User();
		user.setName("Test Testing");
		user.setSurname("Test Testing");
		user.setEmail("Test Testing");
		user.setTelNumber(null);
		user.setBirthdate(LocalDate.parse("2022-02-02"));
		user.setLogin("test.testing");
		user.setPassword("testtesting");
		user.setImage(null);
		size = userDao.getAll().size(); // pocet userov pred pridanim noveho
		savedUser = userDao.save(user);
	}

	@AfterEach
	void tearDown() throws Exception {
		userDao.delete(savedUser.getId());
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
	
	// spravne otestovat, hodnoty sa mohli zmenit
	@Test
	void getAllTouristsTest() {
		assertEquals(userDao.getAllTourists(1).size(), 2);
		//assertEquals(userDao.getAllTourists(10).size(), 2);
	}
	
	// spravne otestovat
	@Test
	void getAllFavouriteGuidemansTest() {
		assertEquals(userDao.getAllFavouriteGuidemans(1).size(), 2);
		assertEquals(userDao.getAllFavouriteGuidemans(2).size(), 1);
	}

	@Test
	void insertTest() {
		assertThrows(NullPointerException.class, () -> userDao.save(null), "Cannot save null");

		User saved2 = userDao.getById(savedUser.getId());
		assertTrue(BCrypt.checkpw("testtesting", saved2.getPassword()));

		assertEquals(size + 1, userDao.getAll().size());
		assertNotNull(savedUser.getId());
		assertEquals(savedUser.getName(), saved2.getName());

		// DuplicitKeyException
		assertThrows(NullPointerException.class, () -> userDao.save(new User("testTesting", "password")),
				"This username is already used");

		// blob sa bude nejako nacitavat ako poly bytov, cez nejaky File.InputStream
		// alebo take srandy
		// blob image treba nejako otestovat, vlastne ako to bude vyzerat, aj ten
		// LocalDate ako to bude clovek tukat do db
		// treba otestovat aj ze heslo je zahashovane?
		assertThrows(NullPointerException.class,
				() -> userDao
						.save(new User(null, "surname", "email", "telnumber", LocalDate.parse("2022-02-02"), null)),
				"Name cannot be null");
		assertThrows(NullPointerException.class,
				() -> userDao.save(new User("name", null, "email", null, LocalDate.parse("2022-02-02"), null)),
				"Surname cannot be null");
		assertThrows(NullPointerException.class,
				() -> userDao.save(new User("name", "surname", null, null, LocalDate.parse("2022-02-02"), null)),
				"Email address cannot be null");
		assertThrows(NullPointerException.class,
				() -> userDao.save(new User("name", "surname", "email", null, null, null)), "Birthdate cannot be null");
		assertThrows(NullPointerException.class, () -> userDao.save(
				new User("name", "surname", "email", null, LocalDate.parse("2022-02-02"), null, "password", null)),
				"Login cannot be null");
		assertThrows(NullPointerException.class,
				() -> userDao.save(
						new User("name", "surname", "email", null, LocalDate.parse("2022-02-02"), "login", null, null)),
				"Password cannot be null");
	}

	@Test
	void updateTest() {
		User updated = new User(savedUser.getId(), "Changed Name", "surname", "email", "telnumber",
				LocalDate.parse("2022-02-02"), null);
		int sizeUpdate = userDao.getAll().size();
		assertEquals(sizeUpdate, userDao.getAll().size());
		User fromDB = userDao.getById(updated.getId());
		assertEquals(updated.getId(), fromDB.getId());
		assertThrows(NullPointerException.class, () -> userDao.save(
				new User(-1L, "Changed Name", "surname", "email", "telnumber", LocalDate.parse("2022-02-02"), null)));

	}

	@Test
	void deleteTest() {
		User userToDelete = new User();
		userToDelete.setName("Test Delete");
		userToDelete.setSurname("Delete");
		userToDelete.setEmail("testdelete");
		userToDelete.setTelNumber("0987654321");
		userToDelete.setBirthdate(LocalDate.parse("2022-10-19"));
		userToDelete.setLogin("test.delete");
		userToDelete.setPassword("testingdelete");
		String salt = BCrypt.gensalt();
		userToDelete.setPassword(BCrypt.hashpw("testDelete", salt));
		userToDelete.setImage(null);
		User saved = userDao.save(userToDelete);
		int sizeDelete = userDao.getAll().size();
		userDao.delete(saved.getId());
		assertEquals(sizeDelete - 1, userDao.getAll().size());
	}

}
