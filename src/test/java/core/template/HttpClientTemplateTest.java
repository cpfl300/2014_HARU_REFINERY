package core.template;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import core.template.HttpClientTemplate;
import core.template.HttpRequestFailureException;
import core.template.HttpResponseFailureException;
import refinery.config.RefineryConfig;
import refinery.model.NaverArticle;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RefineryConfig.class, loader=AnnotationConfigContextLoader.class)
public class HttpClientTemplateTest {
	
	private final static String URL = "/read.nhn";
	private final static String FAKE_URL = "/fake";
	private final static String FAKE_HOST = "http://fake";
	private final static String FAKE_CONTEXT = "/fake_context";
	private GsonMapper<NaverArticle> gsonMapper;
	
	@Autowired
	private HttpClientTemplate httpClientTemplate;	
	
	@Before
	public void setup() {
		gsonMapper = (gson, gsonReader) -> {
			gsonReader.beginObject();
			gsonReader.nextName();
			NaverArticle naverArticle = gson.fromJson(gsonReader, NaverArticle.class);
			gsonReader.endObject();
			
			return naverArticle;
		};
		
	}

	@Test
	public void get() {
		String officeId = "073";
		String articleId = "0002377584";
		
		NaverArticle actual = httpClientTemplate.request(
					URL,
					"officeId=?&articleId=?",
					new Object[] { officeId, articleId },
					this.gsonMapper
				);
		
		assertThat(actual.getOfficeId(), is(officeId));
		assertThat(actual.getArticleId(), is(articleId));
	}
	
	
	@Test(expected=HttpRequestFailureException.class)
	public void getError_request() {
		String officeId = "073";
		String articleId = "0002377584";
		
		httpClientTemplate.setHost(FAKE_HOST);
		
		NaverArticle actual = httpClientTemplate.request(
					URL,
					"officeId=?&articleId=?",
					new Object[] { officeId, articleId },
					this.gsonMapper
				);
		
		assertThat(actual.getOfficeId(), is(officeId));
		assertThat(actual.getArticleId(), is(articleId));
	}
	
	@Test(expected=HttpResponseFailureException.class)
	public void getError_404() {
		String officeId = "073";
		String articleId = "0002377584";
		
		NaverArticle actual = httpClientTemplate.request(
					FAKE_URL,
					"officeId=?&articleId=?",
					new Object[] { officeId, articleId },
					this.gsonMapper
				);
		
		assertThat(actual.getOfficeId(), is(officeId));
		assertThat(actual.getArticleId(), is(articleId));
	}
	
	@Test(expected=HttpResponseFailureException.class)
	public void getError_500() {
		String officeId = "073";
		String articleId = "0002377584";
		
		httpClientTemplate.setContext(FAKE_CONTEXT);
		
		NaverArticle actual = httpClientTemplate.request(
					URL,
					"officeId=?&articleId=?",
					new Object[] { officeId, articleId },
					this.gsonMapper
				);
		
		assertThat(actual.getOfficeId(), is(officeId));
		assertThat(actual.getArticleId(), is(articleId));
	}
	
}
