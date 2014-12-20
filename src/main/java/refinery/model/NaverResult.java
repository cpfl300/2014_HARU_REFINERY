package refinery.model;

import java.util.List;

public class NaverResult {
	
	private String componentId;
	private String sectionId;
	private String rankingType;
	private String datehour;
	private List<NaverArticle> articles;
	private List<NaverArticleCount> articleCountList;
	private List<NaverHotissue> hotissues;
	
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
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
	public List<NaverArticleCount> getArticleCountList() {
		return articleCountList;
	}
	public void setArticleCountList(List<NaverArticleCount> articleCountList) {
		this.articleCountList = articleCountList;
	}
	public List<NaverHotissue> getHotissues() {
		return hotissues;
	}
	public void setHotissues(List<NaverHotissue> hotissues) {
		this.hotissues = hotissues;
	}
	
	

}
