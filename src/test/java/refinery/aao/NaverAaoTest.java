package refinery.aao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import refinery.aao.NaverAao;
import refinery.config.RefineryConfig;
import refinery.model.NaverArticle;
import refinery.model.NaverArticleCount;
import refinery.model.NaverHotissue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RefineryConfig.class, loader=AnnotationConfigContextLoader.class)
public class NaverAaoTest {
	
	@Autowired
	private NaverAao naverAao;
	
	@Test
	public void getArticle() {
		String officeId = "073";
		String articleId = "0002377584";
		
		NaverArticle actual = naverAao.getArticle(officeId, articleId);
		
		assertThat(actual.getOfficeId(), is(officeId));
		assertThat(actual.getArticleId(), is(articleId));
	}
	
	@Test
	public void getHotissueList() {
		List<NaverHotissue> actuals = naverAao.getHotissueList();
		
		for (NaverHotissue actual : actuals) {
			assertThat(actual.getComponentId(), notNullValue());
		}
		
	}
	
	@Test
	public void getArticleList() {
		String datehour = "20140124103";
		
		List<NaverArticle> actuals = naverAao.getArticleList(datehour);
		
		Iterator<NaverArticle> ir = actuals.iterator();
		while(ir.hasNext()) {
			NaverArticle actual = ir.next();
			
			String actualDatehour = actual.getServiceDate() + actual.getServiceTime().substring(0, 3);
			
			assertThat(actualDatehour, is(datehour));
		}
		
	}
	
//	
	@Test
	public void getArticleListByHotissueId() {
		String componentId = "887553";
		
		List<NaverArticle> actuals = naverAao.getArticleListByHotissueId(componentId);
		
		assertThat(actuals.size(), greaterThanOrEqualTo(1));	
	}
	

	@Test
	public void getArticleCountListInRealtime() {
		String sectionId = "100";
		
		List<NaverArticleCount> actuals = naverAao.getArticleCountListInRealtime(sectionId);
		assertThat(actuals.size(), greaterThanOrEqualTo(1));
	}
	
	@Test
	public void getArticleCountListAtDay() {
		String date = "20141104";
		String sectionId = "101";
		
		List<NaverArticle> actuals = naverAao.getArticleCountList(sectionId, date, null).getArticles();
		
		assertThat(actuals.size(), greaterThanOrEqualTo(1));
	}
	
	@Test
	public void getArticleCountListAtHour() {
		String date = "20141219";
		String hour = "17";
		String sectionId = "101";
		
		List<NaverArticle> actuals = naverAao.getArticleCountList(sectionId, date, hour).getArticles();
		
		assertThat(actuals.size(), greaterThanOrEqualTo(1));
	}
	

}