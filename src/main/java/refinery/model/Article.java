package refinery.model;

public class Article {

	private String id;
	private Hotissue hotissue;
	private Journal journal;
	private Section section;
	private String title;
	private String date;
	private String content;
	private int hits;
	private int completedReadingCount;
	private double score;
	private String timestamp;

	public Article() {

	}

	public Article(Hotissue hotissue, Journal journal, Section section, String title, String date, String content, int hits,
			int completedReadingCount, double score) {

		this.hotissue = hotissue;
		this.journal = journal;
		this.section = section;
		this.title = title;
		this.date = date;
		this.content = content;
		this.hits = hits;
		this.completedReadingCount = completedReadingCount;
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Hotissue getHotissue() {
		return hotissue;
	}

	public void setHotissue(Hotissue hotissue) {
		this.hotissue = hotissue;
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		if (date.lastIndexOf('.') != -1) {
			this.date = usableDateStr(date);
			return;
		}
		
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
		if (timestamp.lastIndexOf('.') != -1) {
			this.timestamp = usableDateStr(timestamp);
			return;
		}
		
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", hotissue=" + hotissue + ", journal=" + journal + ", section=" + section + ", title=" + title + ", date="
				+ date + ", content=" + content + ", hits=" + hits + ", completedReadingCount=" + completedReadingCount + ", score=" + score
				+ ", timestamp=" + timestamp + "]";
	}
	
	private String usableDateStr(String dateStr) {
		
		return dateStr.substring(0, dateStr.lastIndexOf("."));
	}
	
}
