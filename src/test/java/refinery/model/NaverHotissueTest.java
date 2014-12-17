package refinery.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import elixir.model.Hotissue;
import elixir.model.HotissueTest;
import elixir.model.Section;
import elixir.model.SectionTest;
import elixir.utility.ElixirUtilsTest;

public class NaverHotissueTest {
	
	private List<NaverHotissue> naverHotissues;
	private List<Hotissue> hotissues;
	
	@Before
	public void setup() {
		List<Date> dates = ElixirUtilsTest.preparedList();
		List<Section> sections = SectionTest.preparedList();
		
		naverHotissues = NaverHotissueTest.preparedList();
		hotissues = HotissueTest.preparedList(dates, sections);
	}

	@Test
	public void convert() {
		for (int i=0; i<hotissues.size(); i++) {
			Hotissue actual = naverHotissues.get(i).convert();
			HotissueTest.ASSERT(actual, hotissues.get(i));
		}
	}
	
	@Test
	public void convertAsList() {
		List<Hotissue> actuals = NaverHotissue.convert(naverHotissues);
		HotissueTest.ASSERTS(actuals, hotissues);
	}
	
	
	
	// creator
	public static NaverHotissue CREATE(String panelId, String componentId, String title, String url) {
		NaverHotissue naverHotissue = new NaverHotissue();

		naverHotissue.setPanelId(panelId);
		naverHotissue.setComponentId(componentId);
		naverHotissue.setTitle(title);
		naverHotissue.setUrl(url);

		return naverHotissue;
	}

	public static List<NaverHotissue> preparedList() {

		return Arrays.asList(new NaverHotissue[] {
				NaverHotissueTest.CREATE("mbs.875.101", "887522", "연애지침서",
						"http://m.news.naver.com/issueGroup.nhn?sid1=103&pid=mbs.875.103&cid=887522&type=issue"),
				NaverHotissueTest.CREATE("mbs.875.102", "893847", "화제의 판결",
						"http://m.news.naver.com/issueGroup.nhn?sid1=102&pid=mbs.875.102&cid=893847&type=issue"),
				NaverHotissueTest.CREATE("mbs.875.103", "887553", "따뜻한 세상",
						"http://m.news.naver.com/issueGroup.nhn?sid1=102&pid=mbs.875.102&cid=893670&type=issue") });
	}

}
