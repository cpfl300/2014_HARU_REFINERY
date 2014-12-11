package refinery.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import elixir.model.Article;
import elixir.model.Journal;
import elixir.model.Section;
import elixir.utility.ElixirUtils;

public class NaverArticle {
	private String officeId;
	private String officeName;
	private String articleId;
	private String sectionId;
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
	
	
	
	public NaverArticle() {
	}
	
	public NaverArticle(String officeId, String officeName, String articleId, String sectionId, String type, String title, String subcontent,
			String content, String orgUrl, List<NaverSection> sections, String serviceDate, String serviceTime, String imageUrl, String reporter,
			String copyright, String hitCount, String readCount, String rank) {
		this.officeId = officeId;
		this.officeName = officeName;
		this.articleId = articleId;
		this.sectionId = sectionId;
		this.type = type;
		this.title = title;
		this.subcontent = subcontent;
		this.content = content;
		this.orgUrl = orgUrl;
		this.sections = sections;
		this.serviceDate = serviceDate;
		this.serviceTime = serviceTime;
		this.imageUrl = imageUrl;
		this.reporter = reporter;
		this.copyright = copyright;
		this.hitCount = hitCount;
		this.readCount = readCount;
		this.rank = rank;
	}
	
	
	@Override
	public String toString() {
		return "NaverArticle [officeId=" + officeId + ", officeName=" + officeName + ", articleId=" + articleId + ", sectionId=" + sectionId
				+ ", type=" + type + ", title=" + title + ", subcontent=" + subcontent + ", content=" + content + ", orgUrl=" + orgUrl
				+ ", sections=" + sections + ", serviceDate=" + serviceDate + ", serviceTime=" + serviceTime + ", imageUrl=" + imageUrl
				+ ", reporter=" + reporter + ", copyright=" + copyright + ", hitCount=" + hitCount + ", readCount=" + readCount + ", rank=" + rank
				+ "]";
	}
	
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
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


	public static Article toArticle(NaverArticle naverArticle) {
		String concatenatedDate = naverArticle.getServiceDate() + naverArticle.getServiceTime();
		Date date = ElixirUtils.parse("yyyyMMddHHmmss", concatenatedDate);
		Article article = new Article();
		
		article.setId(Integer.parseInt(naverArticle.getArticleId()));
		article.setJournal(new Journal(Integer.parseInt(naverArticle.getOfficeId())));
		article.setSection(new Section(Integer.parseInt(naverArticle.getSectionId())));
		article.setContent(naverArticle.getContent());
		article.setHits(Integer.parseInt(naverArticle.getHitCount()));
		article.setCompletedReadingCount(Integer.parseInt(naverArticle.getReadCount()));
		article.setDate(ElixirUtils.format("yyyy-MM-dd HH:mm:ss", date));
		article.setTitle(naverArticle.getTitle());
		
		return article;
	}
	
	public static List<Article> asArticles(List<NaverArticle> naverArticles) {
		List<Article> articles = new ArrayList<Article>();
		for (NaverArticle naverArticle : naverArticles) {
			articles.add(NaverArticle.toArticle(naverArticle));
		}
		
		return articles;
	}
}