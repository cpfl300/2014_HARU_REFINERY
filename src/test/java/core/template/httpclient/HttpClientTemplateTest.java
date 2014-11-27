package core.template.httpclient;

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

import refinery.config.Config;
import core.naver.model.Response;
import core.naver.model.ResponseArticle;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class, loader=AnnotationConfigContextLoader.class)
public class HttpClientTemplateTest {
	
	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
	private final String host = "http://localhost/~Dec7/haru/test/newNewsApiServer.php";
	private final String fakeHost = "http://localhost/~Dec7/fake";
	private final String uri = "/article";
	private final String fakeUri = "/fake";

	@Test
	public void get() {
		Response actualResponse = httpClientTemplate.get(host, uri, Response.class);
		
		List<ResponseArticle> articles = actualResponse.getResponseArticles();
		Iterator<ResponseArticle> ir = articles.iterator();
		while(ir.hasNext()) {
			ResponseArticle a = ir.next();
			assertThat(a, is(ResponseArticle.class));
		}
	}
	
	@Test(expected=HttpRequestFailureException.class)
	public void notGetRequestFailure() {
		Response actualResponse = httpClientTemplate.get(fakeHost, uri, Response.class);
		
		List<ResponseArticle> articles = actualResponse.getResponseArticles();
		Iterator<ResponseArticle> ir = articles.iterator();
		while(ir.hasNext()) {
			ResponseArticle a = ir.next();
			assertThat(a, is(ResponseArticle.class));
		}
		
	}
	
	@Test(expected=NullPointerException.class)
	public void notGet() {
		Response actualResponse = httpClientTemplate.get(host, fakeUri, Response.class);
		
		List<ResponseArticle> articles = actualResponse.getResponseArticles();
		Iterator<ResponseArticle> ir = articles.iterator();
		while(ir.hasNext()) {
			ResponseArticle a = ir.next();
			assertThat(a, is(ResponseArticle.class));
		}
		
	}

}
