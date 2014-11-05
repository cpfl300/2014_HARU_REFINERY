package refinery.module;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import refinery.config.Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class, loader=AnnotationConfigContextLoader.class)
public class PumpTest {
	
	@Autowired
	private Pump pump;

	@Test
	public void get() {
		int statusCode = pump.get("http://localhost/~Dec7/haru/test/newsApiServer.php");
		assertThat(statusCode, is(200));
	}
	
	@Test(expected=HttpRequestErrorException.class)
	public void getNone() {
		int statusCode = pump.get("http://localhost/~Dec7/haru/test/nonExistentPage.php");
		assertThat(statusCode, is(404));
	}

}