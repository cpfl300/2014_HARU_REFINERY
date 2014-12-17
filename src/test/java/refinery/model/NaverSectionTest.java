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
	private Section section;
	
	@Before
	public void setup() {
		section = new Section("222", "sectionName2");
		naverSections = NaverSectionTest.preparedList();
	}

	@Test
	public void covert_count1() {
		// prepare
		prepareNaverSections(naverSections.get(1));
		
		Section actualSection = NaverSection.convert(naverSections);
		SectionTest.ASSERT(actualSection, section);
	}

	@Test
	public void covert_count2() {
		// prepare
		prepareNaverSections(naverSections.get(0), naverSections.get(1));
		
		Section actualSection = NaverSection.convert(naverSections);
		SectionTest.ASSERT(actualSection, section);
	}
	
	@Test
	public void covert_count3() {
		// prepare
		prepareNaverSections(naverSections.get(0), naverSections.get(1), naverSections.get(2));
		
		Section actualSection = NaverSection.convert(naverSections);
		SectionTest.ASSERT(actualSection, section);
	}
	
	
	
	@Test(expected=NaverConvertFailureException.class)
	public void covert_lackOfSize() {
		// prepare
		prepareNaverSections(naverSections.get(0));
		
		Section actualSection = NaverSection.convert(naverSections);
		SectionTest.ASSERT(actualSection, section);
	}
	
	
	// prepare
	private void prepareNaverSections(NaverSection... naverSectionArr) {
		naverSections = Arrays.asList(naverSectionArr); 
	}

	
	// creator
	public static NaverSection CREATE(String sectionId, String sectionName) {
		NaverSection naverSection = new NaverSection();
		
		naverSection.setSectionId(sectionId);
		naverSection.setSectionName(sectionName);
		
		return naverSection;
	}

	public static List<NaverSection> preparedList() {
		return Arrays.asList(
				new NaverSection[] {
						NaverSectionTest.CREATE("111", "sectionName1"),
						NaverSectionTest.CREATE("222", "sectionName2"),
						NaverSectionTest.CREATE("333", "sectionName3")
				});
	}
}
