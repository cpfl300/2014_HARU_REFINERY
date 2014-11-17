package core.naver.model;

import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.model.Journal;
import refinery.model.Section;


public class ResponseArticle {
	private String id;
	private String hotissue;
	private String title;
	private String journal;
	private String section;
	private String date;
	private String content;
	private int hits;
	private int completedReadingCount;
	private double score;
	private String timestamp;
	
	public ResponseArticle(String hotissue, String title, String journal, String section, String date, String content, int hits,
			int completedReadingCount, double score, String timestamp) {
		this.hotissue = hotissue;
		this.title = title;
		this.journal = journal;
		this.section = section;
		this.date = date;
		this.content = content;
		this.hits = hits;
		this.completedReadingCount = completedReadingCount;
		this.score = score;
		this.timestamp = timestamp;
	}
	
	public ResponseArticle(String hotissue, String title, String journal, String section, String date, String content, int hits, int completedReadingCount) {
		this(hotissue, title, journal, section, date, content, hits, completedReadingCount, 0, null);
	}
	
	
	public ResponseArticle() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHotissue() {
		return hotissue;
	}

	public void setHotissue(String hotissue) {
		this.hotissue = hotissue;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getCompletedReadingCount() {
		return completedReadingCount;
	}

	public void setCompletedReadingCount(int completedReadingCount) {
		this.completedReadingCount = completedReadingCount;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}


	public Article toArticle() {
		Hotissue hotissue = new Hotissue(this.hotissue);
		Journal journal = new Journal(this.journal);
		Section section = new Section(this.section);
		
		Article article = new Article(hotissue, journal, section, this.title, this.date, this.content, this.hits, this.completedReadingCount);
		
		return article;
	}

	@Override
	public String toString() {
		return "ResponseArticle [id=" + id + ", hotissue=" + hotissue + ", title=" + title + ", journal=" + journal + ", section=" + section
				+ ", date=" + date + ", content=" + content + ", hits=" + hits + ", completedReadingCount=" + completedReadingCount + ", score="
				+ score + ", timestamp=" + timestamp + "]";
	}
	
	
}
