package refinery.model;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import elixir.model.Section;
import elixir.model.SectionTest;
import elixir.test.ElixirTestUtils;

public class NaverSectionTest {
	
	private List<NaverSection> naverSections;
	private List<Section> sections;
	
	@Before
	public void setup() {
		sections = SectionTest.preparedList(new String[]{"sectionId", "sectionName"});
		naverSections = NaverSectionTest.preparedList();
	}

	@Test
	public void covert() {
		Section actual = naverSections.get(0).convert();
		SectionTest.ASSERT(actual, sections.get(0));
	}
	
	@Test
	public void convertAtList() {
		List<Section> actuals = NaverSections.convert(naverSections);
		SectionTest.ASSERTS(actuals, sections);
		
	}

	
	// creator
	public static NaverSection create(String sectionId, String sectionName) {
		NaverSection naverSection = new NaverSection();
		
		naverSection.setSectionId(sectionId);
		naverSection.setSectionName(sectionName);
		
		return naverSection;
	}
	
	// preparedList
	public static List<NaverSection> preparedList() {
		return Arrays.asList(
				new NaverSection[] {
						NaverSectionTest.create("104", "생활/문화"),
						NaverSectionTest.create("227", "지역"),
						NaverSectionTest.create("316", "3단계_16")
				});
	}
	
	public static List<NaverSection> preparedList(String[] fields) {
		List<NaverSection> naverSections = NaverSectionTest.preparedList();
		
		ElixirTestUtils.initComplementaryFields(naverSections, fields);
		
		return naverSections;
	}
}
