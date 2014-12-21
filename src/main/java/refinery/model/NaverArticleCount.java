package refinery.model;

public class NaverArticleCount {
	private String title;
	private String officeId;
	private String articleId;
	private String hitCount;
	private String readCount;
	private String relativeRate;
	private String updateUserId;
	private String articleDatetime;
	private String formattedArticleDatetime;
	
	public NaverArticleCount() { }
	
	public NaverArticleCount(String title, String officeId, String articleId, String hitCount, String readCount, String relativeRate,
			String updateUserId, String articleDatetime, String formattedArticleDatetime) {
		this.title = title;
		this.officeId = officeId;
		this.articleId = articleId;
		this.hitCount = hitCount;
		this.readCount = readCount;
		this.relativeRate = relativeRate;
		this.updateUserId = updateUserId;
		this.articleDatetime = articleDatetime;
		this.formattedArticleDatetime = formattedArticleDatetime;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
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
	public String getRelativeRate() {
		return relativeRate;
	}
	public void setRelativeRate(String relativeRate) {
		this.relativeRate = relativeRate;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getArticleDatetime() {
		return articleDatetime;
	}
	public void setArticleDatetime(String articleDatetime) {
		this.articleDatetime = articleDatetime;
	}
	public String getFormattedArticleDatetime() {
		return formattedArticleDatetime;
	}
	public void setFormattedArticleDatetime(String formattedArticleDatetime) {
		this.formattedArticleDatetime = formattedArticleDatetime;
	}
	
	@Override
	public String toString() {
		return "NaverArticleCountList [title=" + title + ", officeId=" + officeId + ", articleId=" + articleId + ", hitCount=" + hitCount
				+ ", readCount=" + readCount + ", relativeRate=" + relativeRate + ", updateUserId=" + updateUserId + ", articleDatetime="
				+ articleDatetime + ", formattedArticleDatetime=" + formattedArticleDatetime + "]";
	}

}