package refinery.model;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import refinery.model.convertible.NaverConvertFailureException;
import elixir.model.Section;
import elixir.model.SectionTest;

public class NaverSectionTest {
	
	private List<NaverSection> naverSections;
	
	private NaverSection naverSection1;
	private NaverSection naverSection2;
	private NaverSection naverSection3;
	private Section section;
	
	@Before
	public void setup() {
		section = new Section("222", "sectionName2");
		
		naverSection1 = new NaverSection("111", "sectionName1");
		naverSection2 = new NaverSection("222", "sectionName2");
		naverSection3 = new NaverSection("333", "sectionName3");
	}


	@Test
	public void covert_count1() {
		// prepare
		prepareNaverSections(new NaverSection[] {
				naverSection2
		});
		
		Section actualSection = NaverSection.convert(naverSections);
		SectionTest.ASSERT(actualSection, section);
	}

	@Test
	public void covert_count2() {
		// prepare
		prepareNaverSections(new NaverSection[] {
				naverSection1, naverSection2
		});
		
		Section actualSection = NaverSection.convert(naverSections);
		SectionTest.ASSERT(actualSection, section);
	}
	
	@Test
	public void covert_count3() {
		// prepare
		prepareNaverSections(new NaverSection[] {
				naverSection1, naverSection2, naverSection3
		});
		
		Section actualSection = NaverSection.convert(naverSections);
		SectionTest.ASSERT(actualSection, section);
	}
	
	
	
	@Test(expected=NaverConvertFailureException.class)
	public void covert_lackOfSize() {
		// prepare
		prepareNaverSections(new NaverSection[] {
				new NaverSection("111", "sectionName1"),
		});
		
		Section actualSection = NaverSection.convert(naverSections);
		SectionTest.ASSERT(actualSection, section);
	}
	
	
	
	
	
	

	private void prepareNaverSections(NaverSection[] naverSectionArr) {
		naverSections = Arrays.asList(naverSectionArr); 
	}
}
