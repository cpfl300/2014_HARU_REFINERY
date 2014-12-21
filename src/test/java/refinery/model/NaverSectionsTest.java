package refinery.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import elixir.model.Section;

public class NaverSectionsTest {
	
	List<List<NaverSection>> naverSectionsList;
	private List<NaverSection> naverSections;
	
	@Before
	public void setup() {
		naverSections = NaverSectionTest.preparedList();
	}
	
	@Test
	public void covert_case1() {
		// prepare
		naverSections = NaverSectionsTest.preparedList1();
		naverSectionsList = NaverSections.separate(naverSections);
		
		// convert
		List<Section> actuals = NaverSections.convert(naverSections);
		
		// assert
		NaverSectionsTest.ASSERTS(actuals, naverSectionsList);
	}
	


	@Test
	public void covert_case2() {
		// prepare
		naverSections = NaverSectionsTest.preparedList2();
		naverSectionsList = NaverSections.separate(naverSections);
		
		// convert
		List<Section> actuals = NaverSections.convert(naverSections);
		
		// assert
		NaverSectionsTest.ASSERTS(actuals, naverSectionsList);
	}
	
	@Test
	public void covert_case3() {
		// prepare
		naverSections = NaverSectionsTest.preparedList3();
		naverSectionsList = NaverSections.separate(naverSections);
		
		// convert
		List<Section> actuals = NaverSections.convert(naverSections);
		
		// assert
		NaverSectionsTest.ASSERTS(actuals, naverSectionsList);
	}
	
	@Test
	public void spearate_case_1개_1단() {
		naverSections = prepareNaverSections(
				NaverSectionTest.create("101", "경제"));
		
		List<List<NaverSection>> naverSectionsList = NaverSections.separate(naverSections);
		assertThat(naverSectionsList.size(), is(1));
	}
	
	@Test
	public void spearate_case_1개_2단() {
		naverSections = prepareNaverSections(
				NaverSectionTest.create("101", "경제"),
				NaverSectionTest.create("261", "산업/재계"));
		
		List<List<NaverSection>> naverSectionsList = NaverSections.separate(naverSections);
		assertThat(naverSectionsList.size(), is(1));
	}
	
	@Test
	public void spearate_case_1개_2단_불규칙() {
		naverSections = prepareNaverSections(
				NaverSectionTest.create("106", "연예"),
				NaverSectionTest.create("309", "해외 연예"));
		
		List<List<NaverSection>> naverSectionsList = NaverSections.separate(naverSections);
		assertThat(naverSectionsList.size(), is(1));
	}
	
	@Test
	public void spearate_case_1개_3단_불규칙1() {
		naverSections = NaverSectionsTest.preparedList1();
		
		List<List<NaverSection>> naverSectionsList = NaverSections.separate(naverSections);
		assertThat(naverSectionsList.size(), is(1));
	}
	
	@Test
	public void spearate_case_1개_3단_불규칙2() {
		naverSections = prepareNaverSections(
				NaverSectionTest.create("107", "스포츠"),
				NaverSectionTest.create("214", "축구(구)"),
				NaverSectionTest.create("421", "한국대표팀"));
		
		List<List<NaverSection>> naverSectionsList = NaverSections.separate(naverSections);
		assertThat(naverSectionsList.size(), is(1));
	}
	
	@Test
	public void spearate_case_2개_1단() {
		naverSections = prepareNaverSections(
				NaverSectionTest.create("100", "정치"),
				NaverSectionTest.create("101", "경제"));
		
		List<List<NaverSection>> naverSectionsList = NaverSections.separate(naverSections);
		assertThat(naverSectionsList.size(), is(2));
	}
	
	@Test
	public void spearate_case_2개_2단() {
		naverSections = prepareNaverSections(
				NaverSectionTest.create("101", "경제"),
				NaverSectionTest.create("261", "산업/재계"),
				NaverSectionTest.create("102", "사회"),
				NaverSectionTest.create("251", "노동"));
		
		List<List<NaverSection>> naverSectionsList = NaverSections.separate(naverSections);
		assertThat(naverSectionsList.size(), is(2));
	}
	
	@Test
	public void spearate_case_2개_3단_불규칙() {
		naverSections = NaverSectionsTest.preparedList2();
		
		List<List<NaverSection>> naverSectionsList = NaverSections.separate(naverSections);
		assertThat(naverSectionsList.size(), is(2));
	}
	
	@Test
	public void spearate_case_3개_불규칙1() {
		naverSections = prepareNaverSections(
				NaverSectionTest.create("159", "채널A"),
				NaverSectionTest.create("748", "뉴스10"),
				NaverSectionTest.create("115", "TV"),
				NaverSectionTest.create("293", "정치"),
				NaverSectionTest.create("100", "정치"),
				NaverSectionTest.create("269", "정치 일반"));
		
		List<List<NaverSection>> naverSectionsList = NaverSections.separate(naverSections);
		assertThat(naverSectionsList.size(), is(3));
	}
	
	@Test
	public void spearate_case_3개_불규칙2() {
		naverSections = NaverSectionsTest.preparedList3();
		
		List<List<NaverSection>> naverSectionsList = NaverSections.separate(naverSections);
		assertThat(naverSectionsList.size(), is(3));
	}
	

	private List<NaverSection> prepareNaverSections(NaverSection... nsArr) {
		return Arrays.asList(nsArr);
	}
	
	
	// asserts
	private static void ASSERTS(List<Section> actuals, List<List<NaverSection>> naverSectionsList) {
		int expectedSize = naverSectionsList.size();
		assertThat(actuals.size(), is(expectedSize));
		
		for (int i=0; i<expectedSize; i++) {
			List<NaverSection> tmp = naverSectionsList.get(i);
			String expectedId = tmp.get(tmp.size()-1).getSectionId(); 
			String actualId = actuals.get(i).getSectionId();
			assertThat(actualId, is(expectedId));
		}
	}
	
	
	// preparedList
	public static List<NaverSection> preparedList1() {
		
		return Arrays.asList(
				new NaverSection[] {
						NaverSectionTest.create("101", "경제"),
						NaverSectionTest.create("258", "증권"),
						NaverSectionTest.create("401", "시황/전망")
				});
		
	}
	
	public static List<NaverSection> preparedList2() {
		return Arrays.asList(
				new NaverSection[] {
						NaverSectionTest.create("100", "정치"),
						NaverSectionTest.create("267", "국방/외교"),
						NaverSectionTest.create("107", "스포츠"),
						NaverSectionTest.create("214", "축구(구)"),
						NaverSectionTest.create("421", "한국대표팀")
				});
		
	}
	
	public static List<NaverSection> preparedList3() {
		return Arrays.asList(
				new NaverSection[] {
						NaverSectionTest.create("104", "세계"),
						NaverSectionTest.create("115", "TV"),
						NaverSectionTest.create("289", "세계"),
						NaverSectionTest.create("129", "YTN TV"),
						NaverSectionTest.create("5ae", "TYN 뉴스")
				});
		
	}
	
}
