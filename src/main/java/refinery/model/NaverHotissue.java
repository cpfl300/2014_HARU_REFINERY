package refinery.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import refinery.model.convertible.Convertible;
import elixir.model.Hotissue;

public class NaverHotissue implements Convertible<Hotissue> {
	
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
	public String toString() {
		return "NaverHotissue [panelId=" + panelId + ", componentId=" + componentId + ", title=" + title + ", url=" + url + "]";
	}
	
	@Override
	public Hotissue convert() {
		Hotissue hotissue = new Hotissue();
		
		hotissue.setHotissueId(this.componentId);
		hotissue.setTitle(this.title);
		
		return hotissue;
	}
	
	public static List<Hotissue> convert(List<NaverHotissue> naverHotissues) {
		List<Hotissue> hotissues = new ArrayList<Hotissue>();
		Iterator<NaverHotissue> ir = naverHotissues.iterator();
		while (ir.hasNext()) {
			NaverHotissue naverArticle = ir.next();
			hotissues.add(naverArticle.convert());
		}
		
		return hotissues;
	}
	

}
