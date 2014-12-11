package refinery.model;

import java.util.List;

public class NaverArticleList {
	
	private String componentId;
	private String sectionId;
	private String rankingType;
	private String datehour;
	private List<NaverArticle> articles;
	
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getRankingType() {
		return rankingType;
	}
	public void setRankingType(String rankingType) {
		this.rankingType = rankingType;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public String getDatehour() {
		return datehour;
	}
	public void setDatehour(String datehour) {
		this.datehour = datehour;
	}
	public List<NaverArticle> getArticles() {
		return articles;
	}
	public void setArticles(List<NaverArticle> articles) {
		this.articles = articles;
	}
	
	@Override
	public String toString() {
		return "NaverArticleList [componentId=" + componentId + ", sectionId=" + sectionId + ", rankingType=" + rankingType + ", datehour="
				+ datehour + ", articles=" + articles + "]";
	}
	
}
