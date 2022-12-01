package sk.upjs.paz1c.guideman.storage;

public class MysqlTourDaoTest {

	private TourDao tourDao;
	private Tour tour;
	private Tour savedTour;
	private int size;

	public MysqlTourDaoTest() {
		DaoFactory.INSTANCE.testing();
		tourDao = DaoFactory.INSTANCE.getTourDao();
	}

}
