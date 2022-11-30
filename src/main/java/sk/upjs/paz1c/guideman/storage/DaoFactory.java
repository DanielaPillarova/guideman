package sk.upjs.paz1c.guideman.storage;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {

	INSTANCE;

	private JdbcTemplate jdbcTemplate;
	private UserDao userDao;
	private LocationDao locationDao;
	private EventDao eventdao;
	private boolean testing = false;

	public void testing() {
		testing = true;
	}

	public UserDao getUserDao() {
		if (userDao == null) {
			userDao = new MysqlUserDao(getJdbcTemplate());
		}
		return userDao;
	}

	public LocationDao getLocationDao() {
		if (locationDao == null) {
			locationDao = new MysqlLocationDao(getJdbcTemplate());
		}
		return locationDao;
	}
	
	public EventDao getEventDao() {
		if (eventdao == null) {
			eventdao = new MysqlEventDao(getJdbcTemplate());
		}
		return eventdao;
	}

	private JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("guideman");
			dataSource.setPassword("guideman2022");
			if (testing) {
				dataSource.setUrl("jdbc:mysql://localhost/guideman_test?serverTimezone=Europe/Bratislava");
			} else {
				dataSource.setUrl("jdbc:mysql://localhost/guideman?serverTimezone=Europe/Bratislava");
			}
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}

}
