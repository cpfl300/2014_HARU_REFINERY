package refinery.module;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import refinery.config.Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class, loader=AnnotationConfigContextLoader.class)
public class NaverAPITest {
	
	@Autowired
	private NaverAPI naverApi;

	@Test
	public void getRoot() {
		Root actualRoot = naverApi.get("/", Root.class);
		Root expectedRoot = new Root("root");
		
		assertSameData(actualRoot, expectedRoot);
	}

	private void assertSameData(Root actual, Root expected) {
		assertThat(actual.getName(), is(expected.getName()));
	}

}