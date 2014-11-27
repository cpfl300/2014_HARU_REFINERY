package core.template.fileio;

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

import refinery.config.RefineryConfig;
import core.naver.model.Response;
import core.naver.model.ResponseArticle;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RefineryConfig.class, loader=AnnotationConfigContextLoader.class)
public class FileIOTemplateTest {

	@Autowired
	private FileIOTemplate fileIOTemplate;
	
	private final String host = "/Users/Dec7/Google Drive/공유/NEXT/14-03/haru/자료/sql/haru/v1.1.2/journal data";
	private final String fakeHost = "http://localhost/~Dec7/fake";
	private final String uri = "*";
	private final String fakeUri = "/fake";

	@Test
	public void get() {
		Response actualResponse = fileIOTemplate.get(host, uri, Response.class);
		
		List<ResponseArticle> articles = actualResponse.getResponseArticles();
		Iterator<ResponseArticle> ir = articles.iterator();
		while(ir.hasNext()) {
			ResponseArticle a = ir.next();
			assertThat(a, is(ResponseArticle.class));
		}
	}

}
