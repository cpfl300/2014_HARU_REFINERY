package refinery.model;

import refinery.model.able.Convertible;
import refinery.model.able.Separable;
import elixir.model.Section;


public class NaverSection implements Convertible<Section>, Separable<NaverSection> {
	
	private String sectionId;
	private String sectionName;
	
	// empty
	public NaverSection() { }
	
	// setter getter
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	// converter
	@Override
	public Section convert() {
		Section section = new Section();
		
		section.setSectionId(sectionId);
		section.setSectionName(sectionName);
		
		return section;
	}

	@Override
	public int separateFrom(NaverSection previous) {
		
		if (sectionId == null || sectionId.length() != 3) return -1;
		
		char firstCurrent = this.sectionId.charAt(0);
		char firstPrevious = previous.sectionId.charAt(0);
		
		if (firstPrevious > '1' && firstCurrent == firstPrevious) return -1;
		
		if (firstPrevious < firstCurrent) return 0;
		
		return 1;
	}

	@Override
	public String toString() {
		return "NaverSection [sectionId=" + sectionId + ", sectionName=" + sectionName + "]";
	}
	
	
	
	

}