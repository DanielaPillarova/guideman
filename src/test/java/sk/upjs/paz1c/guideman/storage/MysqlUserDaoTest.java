package sk.upjs.paz1c.guideman.storage;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

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

	// nejako vela userov sa vytvara, neviem to inak poriesit v tom setUp a tearDown
	// nech sa mi neopakuje kod?

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
		userDao.delete(savedUser.getId());
	}

	@Test
	void insertTest() {
		assertThrows(NullPointerException.class, () -> userDao.save(null), "Cannot save null");
		User newUser = new User();
		newUser.setName("New User");
		newUser.setSurname("Test Testing");
		newUser.setEmail("Test Testing");
		newUser.setTelNumber(null);
		newUser.setBirthdate(LocalDate.parse("2022-02-02"));
		newUser.setLogin("testTesting");
		newUser.setPassword("testtesting");
		newUser.setImage(null);
		int size = userDao.getAll().size();
		User saved = userDao.save(newUser);
		assertEquals(size + 1, userDao.getAll().size());
		assertNotNull(saved.getId());
		assertEquals(newUser.getName(), saved.getName());

		// tu chcem poriesit DuplicitKeyException, ale pisalo mi ze ma osetrovat
		// NullPointerException, tak neviem
		assertThrows(NullPointerException.class, () -> userDao.save(new User("testTesting", "password")),
				"This username is already used");

		userDao.delete(saved.getId());
		assertNotEquals(size + 1, userDao.getAll().size());

		// blob image treba nejako otestovat, vlastne ako to bude vyzerat, aj ten
		// LocalDate ako to bude clovek tukat do db
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
		int size = userDao.getAll().size();
		assertEquals(size, userDao.getAll().size());
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
		userToDelete.setPassword("testdelete");
		userToDelete.setImage(null);
		User saved = userDao.save(userToDelete);
		int size = userDao.getAll().size();
		userDao.delete(saved.getId());
		assertEquals(size - 1, userDao.getAll().size());
	}

}
