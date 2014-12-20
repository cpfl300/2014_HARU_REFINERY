package refinery.model;

import refinery.model.convertible.Convertible;
import elixir.model.Section;


public class NaverSection implements Convertible<Section> {
	
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

}