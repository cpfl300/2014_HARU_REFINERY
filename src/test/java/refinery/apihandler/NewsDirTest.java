package refinery.apihandler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import refinery.apihandler.NaverNewsAPI;
import refinery.config.RefineryConfig;
import refinery.model.Response;
import refinery.model.ResponseArticle;
import refinery.template.FileIOTemplate;
import elixir.service.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RefineryConfig.class, loader=AnnotationConfigContextLoader.class)
public class NewsDirTest {
	
	private static final Logger log = LoggerFactory.getLogger(NewsDirTest.class);
	
	private final String host = "/Users/Dec7/Google Drive/공유/NEXT/14-03/haru/자료/sql/haru/v1.1.2/journal data";
	
	@Autowired
	private NaverNewsAPI naverNewsApi;
	
	@Autowired
	private FileIOTemplate fileIOTemplate;
	
	@Autowired
	private ArticleService articleService;
	
	@Before
	public void setup() {
		naverNewsApi.setHost(host);
		naverNewsApi.setTemplate(fileIOTemplate);
	}

	@Test
	public void getArticles() {
		Response actualResponse = naverNewsApi.get("*", Response.class);
		
		List<ResponseArticle> articles = actualResponse.getResponseArticles();
		Iterator<ResponseArticle> ir = articles.iterator();
		while(ir.hasNext()) {
			ResponseArticle a = ir.next();
			log.debug(a.toString());
			assertThat(a, is(ResponseArticle.class));
		}
	}
	
	@Test
	public void setArticles() {
		Response actualResponse = naverNewsApi.get("*", Response.class);
		
		List<ResponseArticle> rarticles = actualResponse.getResponseArticles();
		Iterator<ResponseArticle> ir = rarticles.iterator();
		while(ir.hasNext()) {
			ResponseArticle a = ir.next();
			articleService.add(a.toArticle());
		}
		
	}
	
}