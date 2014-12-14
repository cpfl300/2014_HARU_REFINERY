package refinery.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import refinery.model.convertible.Convertible;
import elixir.model.Article;
import elixir.model.Office;

public class NaverArticle implements Convertible<Article> {
	private String officeId;
	private String officeName;
	private String articleId;
	private String type;
	private String title;
	private String subcontent;
	private String content;
	private String orgUrl;
	private List<NaverSection> sections;
	private String serviceDate;
	private String serviceTime;
	private String imageUrl;
	private String reporter;
	private String copyright;
	private String hitCount;
	private String readCount;
	private String rank;
	
	// empty
	public NaverArticle() { }
	

	// setter getter
	public String getHitCount() {
		return hitCount;
	}
	public void setHitCount(String hitCount) {
		this.hitCount = hitCount;
	}
	public String getReadCount() {
		return readCount;
	}
	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubcontent() {
		return subcontent;
	}
	public void setSubcontent(String subcontent) {
		this.subcontent = subcontent;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOrgUrl() {
		return orgUrl;
	}
	public void setOrgUrl(String orgUrl) {
		this.orgUrl = orgUrl;
	}
	public List<NaverSection> getSections() {
		return sections;
	}
	public void setSections(List<NaverSection> sections) {
		this.sections = sections;
	}
	public String getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}


	@Override
	public Article convert() {
		Article article = new Article();
		
		article.setArticleId(this.articleId);
		article.setOffice(new Office(this.officeId, this.officeName));
		if (this.sections != null) { article.setSection(NaverSection.convert(this.sections)); }
		article.setContributionDate(this.serviceDate);
		article.setContributionTime(this.serviceTime);
		article.setTitle(this.title);
		
		article.setContent(this.content);
		article.setOrgUrl(this.orgUrl);
		article.setImageUrl(this.imageUrl);
		
		return article;
	}

	public static List<Article> convert(List<NaverArticle> naverArticles) {
		List<Article> articles = new ArrayList<Article>();
		Iterator<NaverArticle> ir = naverArticles.iterator();
		while (ir.hasNext()) {
			NaverArticle naverArticle = ir.next();
			articles.add(naverArticle.convert());
		}
		
		return articles;
	}


	@Override
	public String toString() {
		return "NaverArticle [officeId=" + officeId + ", officeName=" + officeName + ", articleId=" + articleId + ", type=" + type + ", title="
				+ title + ", subcontent=" + subcontent + ", content=" + content + ", orgUrl=" + orgUrl + ", sections=" + sections + ", serviceDate="
				+ serviceDate + ", serviceTime=" + serviceTime + ", imageUrl=" + imageUrl + ", reporter=" + reporter + ", copyright=" + copyright
				+ ", hitCount=" + hitCount + ", readCount=" + readCount + ", rank=" + rank + "]";
	}

	
	
}