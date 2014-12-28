package refinery.model;

import elixir.model.Hotissue;
import elixir.model.Section;


public class NaverHotissue implements Convertible<Hotissue>{
	private static final String SECTION_ID_EXP = "sid1=";
	
	private String panelId;
	private String componentId;
	private String title;
	private String url;
	
	// empty
	public NaverHotissue() { }
	
	
	// setter getter
	public String getPanelId() {
		return panelId;
	}
	public void setPanelId(String panelId) {
		this.panelId = panelId;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	
	@Override
	public Hotissue convert() {
		Hotissue hotissue = new Hotissue();
		
		hotissue.setHotissueId(this.componentId);
		hotissue.setTitle(this.title);
		hotissue.setSection(new Section(extractSectionId()));
		
		return hotissue;
	}

	private String extractSectionId() {
		String[] strs = this.url.split(NaverHotissue.SECTION_ID_EXP);
		String sectionId = strs[1].substring(0,3); 

		return sectionId;
	}
	

}
