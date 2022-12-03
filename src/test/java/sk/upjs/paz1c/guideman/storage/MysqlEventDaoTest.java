package sk.upjs.paz1c.guideman.storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MysqlEventDaoTest {

	private EventDao eventDao;
	private UserDao userDao;
	private LocationDao locationDao;
	private TourDao tourDao;
	
	private Event savedEvent;
	private User savedUser;
	private Location savedLocation;
	private Tour savedTour;
	
	public MysqlEventDaoTest() {
		DaoFactory.INSTANCE.testing();
		eventDao = DaoFactory.INSTANCE.getEventDao();
		userDao = DaoFactory.INSTANCE.getUserDao();
		locationDao = DaoFactory.INSTANCE.getLocationDao();
		tourDao = DaoFactory.INSTANCE.getTourDao();
	}
	

	@BeforeEach
	void setUp() throws Exception {
		User guideman = new User();
		guideman.setName("Testovaci guideman");
		guideman.setSurname("Priezvisko");
		guideman.setEmail("idk@gmail.com");
//		guideman.setTelNumber(null);
		guideman.setBirthdate(LocalDate.parse("2000-05-10"));
		guideman.setLogin("test.event");
		guideman.setPassword("testEvent");
//		guideman.setImage(null);
		
		Location location = new Location();
		location.setCountry("Slovensko");
		location.setCity("Bratiska");
		location.setStreet("Hviezdoslavovo namestie");
//		location.setStreet_number(null);
		
		Tour tour = new Tour();
		tour.setTitle("Testovacia Tour");
		tour.setBio("testujem tour aj s eventom");
		tour.setMaxSlots(15);
		// cakam na romana
//		tour.setLocation(null);
//		tour.setGuideman(null);
		tour.setImage(null);
		
		Event event = new Event();
		event.setDateOfTour(LocalDateTime.parse("2022-12-24 09:00:00"));
		event.setDuration(LocalTime.parse("03:00:00"));
		event.setPrice(10.00);
		event.setTour(null);
		
		savedUser = userDao.save(guideman);
		savedLocation = locationDao.save(location);
//		savedTour = tourDao.save(tour);
		savedEvent = eventDao.save(event);
		
	}

	@AfterEach
	void tearDown() throws Exception {
		locationDao.delete(savedLocation.getId());
		userDao.delete(savedUser.getId());
//		tourDao.delete(savedTour.getId());
		eventDao.delete(savedEvent.getId());
		
	}

	// TODO
	// testy, ked roman opravi instancne premenne v Tour
	
	@Test
	void getRatingsTest() {

	}
	
	@Test
	void getReviewsTest() {

	}
	
	@Test
	void getAllByTourTest() {

	}
	
	@Test
	void insertTest() {

	}
	
	@Test
	void updateTest() {

	}
	
	@Test
	void deleteTest() {

	}
	
	

}
