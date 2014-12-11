package refinery.model;

public class NaverHotissue {
	
	private String panelId;
	private String componentId;
	private String title;
	private String url;
	
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
	public String toString() {
		return "NaverHotissue [panelId=" + panelId + ", componentId=" + componentId + ", title=" + title + ", url=" + url + "]";
	}

}
