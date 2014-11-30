package refinery.apihandler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import refinery.apihandler.NaverNewsAPI;
import refinery.config.RefineryConfig;
import refinery.model.Response;
import refinery.model.ResponseArticle;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RefineryConfig.class, loader=AnnotationConfigContextLoader.class)
public class NaverNewsAPITest {
	
	@Autowired
	private NaverNewsAPI naverNewsApi;

	@Test
	public void getArticles() {
		Response actualResponse = naverNewsApi.get("/article", Response.class);
		
		List<ResponseArticle> articles = actualResponse.getResponseArticles();
		Iterator<ResponseArticle> ir = articles.iterator();
		while(ir.hasNext()) {
			ResponseArticle a = ir.next();
			assertThat(a, is(ResponseArticle.class));
		}
	}
}