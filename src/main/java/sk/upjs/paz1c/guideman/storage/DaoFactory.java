package sk.upjs.paz1c.guideman.storage;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {

	INSTANCE;

	private JdbcTemplate jdbcTemplate;
	private UserDao userDao;
	
	public UserDao getUserDao() {
		if (userDao == null) {
			userDao = new MysqlUserDao(getJdbcTemplate());
		}
		return userDao;
		
	}

	private JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("guideman");
			dataSource.setPassword("guideman2022");
			dataSource.setUrl("jdbc:mysql://localhost/guideman?serverTimezone=Europe/Bratislava");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}

}
