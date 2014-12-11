package refinery.model;

public class NaverSection {
	
	private String sectionId;
	private String sectionName;
	
	public NaverSection(String sectionId, String sectionName) {
		this.sectionId = sectionId;
		this.sectionName = sectionName;
	}
	
	public NaverSection() {
	}

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
	
	

}