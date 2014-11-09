package refinery.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import core.naver.api.NaverAPI;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages={"refinery", "core"})
@PropertySource(value="classpath:application-properties.xml")
public class Config {
	
	@Resource
	private Environment env;
	
	@Bean
	public DataSource refineryDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(env.getRequiredProperty("database.driverClassName"));
		ds.setUrl(env.getRequiredProperty("refinery.database.url"));
		ds.setUsername(env.getRequiredProperty("refinery.database.username"));
		ds.setPassword(env.getRequiredProperty("refinery.database.password"));
		return ds;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		
		return new JdbcTemplate(refineryDataSource());
	}

	@Bean
	public NaverAPI naverAPI() {
		NaverAPI naverAPI = new NaverAPI(env.getRequiredProperty("naver.news.api"));
		
		return naverAPI;

	}
}