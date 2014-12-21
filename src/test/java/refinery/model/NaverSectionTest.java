package refinery.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
	
	// convert
	@Test
	public void covert() {
		Section actual = naverSections.get(0).convert();
		SectionTest.ASSERT(actual, sections.get(0));
	}
	
	// separateFrom
	@Test
	public void separateFrom_연속된_첫자리_수가_증가시() {
		NaverSection naverSection1 = NaverSectionTest.create("100", "백");
		NaverSection naverSection2 = NaverSectionTest.create("200", "이백");
		NaverSection naverSection3 = NaverSectionTest.create("700", "칠백");
		
		assertThat(naverSection2.separateFrom(naverSection1), is(0));
		assertThat(naverSection3.separateFrom(naverSection2), is(0));
	}
	
	@Test
	public void separateFrom_연속된_첫자리_수가_감소할시() {
		NaverSection naverSection1 = NaverSectionTest.create("700", "칠백");
		NaverSection naverSection2 = NaverSectionTest.create("200", "이백");
		NaverSection naverSection3 = NaverSectionTest.create("100", "백");
		
		assertThat(naverSection2.separateFrom(naverSection1), is(1));
		assertThat(naverSection3.separateFrom(naverSection2), is(1));
	}
	
	@Test
	public void separateFrom_연속된_첫자리_수가_동일할시_1일때() {
		NaverSection naverSection1 = NaverSectionTest.create("100", "백");
		NaverSection naverSection2 = NaverSectionTest.create("101", "백일");
		NaverSection naverSection3 = NaverSectionTest.create("102", "백이");
		
		assertThat(naverSection2.separateFrom(naverSection1), is(1));
		assertThat(naverSection3.separateFrom(naverSection2), is(1));
	}
	
	@Test
	public void separateFrom_연속된_첫자리_수가_동일할시_2이상일때() {
		NaverSection naverSection1 = NaverSectionTest.create("100", "백");
		NaverSection naverSection2 = NaverSectionTest.create("200", "이백일");
		NaverSection naverSection3 = NaverSectionTest.create("201", "이백이");
		
		assertThat(naverSection2.separateFrom(naverSection1), is(0));
		assertThat(naverSection3.separateFrom(naverSection2), is(-1));
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
