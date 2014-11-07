package refinery.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import refinery.module.NaverAPI;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages={"refinery", "core"})
@PropertySource(value="classpath:application-properties.xml")
public class Config {
	
	@Resource
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(env.getRequiredProperty("database.driverClassName"));
		ds.setUrl(env.getRequiredProperty("database.url"));
		ds.setUsername(env.getRequiredProperty("database.username"));
		ds.setPassword(env.getRequiredProperty("database.password"));
		return ds;
	}
	
	@Bean
	public NaverAPI naverAPI() {
		NaverAPI naverAPI = new NaverAPI("http://localhost/~Dec7/haru/test/newsApiServer.php");
		return naverAPI;
		
		
	}
}