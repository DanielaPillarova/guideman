package sk.upjs.paz1c.guideman;

import java.util.List;

import sk.upjs.paz1c.guideman.storage.DaoFactory;
import sk.upjs.paz1c.guideman.storage.Location;
import sk.upjs.paz1c.guideman.storage.LocationDao;
import sk.upjs.paz1c.guideman.storage.User;
import sk.upjs.paz1c.guideman.storage.UserDao;

public class App {

	public static void main(String[] args) {
		// Users
		
		UserDao userDao = DaoFactory.INSTANCE.getUserDao();
		List<User> users = userDao.getAll();
		System.out.println(users);
		System.out.println("halo");
		
		// Location
		
		LocationDao locationDao = DaoFactory.INSTANCE.getLocationDao();
		List<Location> locations = locationDao.getAll();
		System.out.println(locations);
		System.out.println("lokácie sheeeesh");
		
	}

}
