package sk.upjs.paz1c.guideman.storage;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MysqlEventDaoTest {

	private EventDao eventDao;
	private Event savedEvent;
	private int size;

	public MysqlEventDaoTest() {
		DaoFactory.INSTANCE.testing();
		eventDao = DaoFactory.INSTANCE.getEventDao();
	}

	@BeforeEach
	void setUp() throws Exception {
		Event event = new Event();
		event.setDateOfTour(LocalDateTime.parse("2024-02-03T10:00:00"));
		event.setDuration(LocalTime.parse("04:45:00"));
		event.setPrice(30.50);
		event.setTourId(1l);
		size = eventDao.getAllByTour(1l).size();
		savedEvent = eventDao.save(event);
	}

	@AfterEach
	void tearDown() throws Exception {
		eventDao.delete(savedEvent.getId());
	}

	@Test
	void getRatingsTest() {
		int size = eventDao.getRatings(1l).size();
		assertTrue(size == 2);
	}

	@Test
	void getReviewsTest() {
		int size = eventDao.getReviews(1l).size();
		assertTrue(size == 3);
	}

	@Test
	void getAllByTourTest() {
		List<Event> allEvents = eventDao.getAllByTour(1l);
		assertEquals(allEvents.size(), 3);
	}

	@Test
	void insertTest() {
		assertThrows(NullPointerException.class, () -> eventDao.save(null), "Cannot save null");

		assertEquals(size + 1, eventDao.getAllByTour(1l).size());
		assertNotNull(savedEvent.getId());

		assertThrows(NullPointerException.class,
				() -> eventDao.save(new Event(null, LocalTime.parse("08:00:00"), 10.00, 1l)),
				"Cannot save event without date and time of tour");
		assertThrows(NullPointerException.class,
				() -> eventDao.save(new Event(LocalDateTime.parse("2000-02-02T19:00:00"), null, 10.00, 1l)),
				"Cannot save event without duration of tour");
		assertThrows(NullPointerException.class,
				() -> eventDao.save(
						new Event(LocalDateTime.parse("2000-02-02T19:00:00"), LocalTime.parse("08:00:00"), null, 1l)),
				"Cannot save event without price of tour");
		assertThrows(NullPointerException.class, () -> eventDao
				.save(new Event(LocalDateTime.parse("2000-02-02T19:00:00"), LocalTime.parse("08:00:00"), 100.00, null)),
				"Cannot save event without id of tour");

	}

	@Test
	void updateTest() {
		Event updatedEvent = new Event(savedEvent.getId(), LocalDateTime.parse("2022-12-14T09:30:30"),
				LocalTime.parse("06:50:00"), 79.00, 1l);
		Event savedUpdatedEvent = eventDao.save(updatedEvent);
		int sizeUpdate = eventDao.getAllByTour(1l).size();
		assertEquals(savedEvent.getId(), savedUpdatedEvent.getId());
		assertEquals(sizeUpdate, size + 1);
		assertThrows(NoSuchElementException.class, () -> eventDao.save(
				new Event(-1l, LocalDateTime.parse("2000-02-02T19:00:00"), LocalTime.parse("08:00:00"), 100.00, 1l)));
	}

	@Test
	void deleteTest() {
		Event eventToDelete = new Event();
		eventToDelete.setDateOfTour(LocalDateTime.parse("2020-03-27T12:20:21"));
		eventToDelete.setDuration(LocalTime.parse("02:00:00"));
		eventToDelete.setPrice(30.00);
		eventToDelete.setTourId(1l);
		Event savedEventToDelete = eventDao.save(eventToDelete);
		int size = eventDao.getAllByTour(1l).size();
		eventDao.delete(savedEventToDelete.getId());
		assertEquals(size - 1, eventDao.getAllByTour(1l).size());
	}

}
