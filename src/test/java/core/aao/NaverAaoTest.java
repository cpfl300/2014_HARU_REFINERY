package core.aao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import core.aao.NaverAao;
import refinery.config.RefineryConfig;
import refinery.model.NaverArticle;
import refinery.model.NaverArticleList;
import refinery.model.NaverHotissue;
import refinery.model.NaverHotissueList;

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
		NaverHotissueList actual = naverAao.getHotissueList();
		
		List<NaverHotissue> hotissues = actual.getHotissues();
		for (NaverHotissue hotissue : hotissues) {
			assertThat(hotissue.getComponentId(), notNullValue());
		}
		
	}
	
	@Test
	public void getArticleListPer10Min() {
		String datehour = "20140124103";
		
		NaverArticleList actual = naverAao.getArticleList(datehour);
		
		assertThat(actual.getDatehour(), is(datehour));
		
		Iterator<NaverArticle> ir = actual.getArticles().iterator();
		while(ir.hasNext()) {
			NaverArticle naverArticle = ir.next();
			String actualDatehour = naverArticle.getServiceDate() + naverArticle.getServiceTime().substring(0, 3);
			
			assertThat(actualDatehour, is(datehour));
		}
		
	}
	
	
	@Test
	public void getArticleListOfHotissue() {
		String componentId = "887553";
		
		NaverArticleList actual = naverAao.getArticleListOfHotissue(componentId);
		
		assertThat(actual.getComponentId(), is(componentId));		
	}
	
	
//	@Test
//	public void getArticleListAtPopularDay() {
//		String datehour="20141129";
//		String sectionId = "100";
//		
//		NaverArticleList actual = naverAao.getArticleListAtPopularDay(datehour, sectionId);
//		
//		assertThat(actual.getSectionId(), is(sectionId));
//		assertThat(actual.getDatehour(), is(datehour));
//		
//		
//	}

	
	

}