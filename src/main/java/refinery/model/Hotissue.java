package refinery.model;

import java.util.List;

public class Hotissue {
	
	private long id;
	private String name;
	private String timestamp;
	private List<Article> articles;
	private double score;
	
	public Hotissue() {
		
	}
	
	public Hotissue(long id) {
		this(id, null);
	}
	
	public Hotissue(String name) {
		this(0, name);
	}
	
	public Hotissue(long id, String name) {
		this.id = id;
		this.name = name;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	
	private String usableDateStr(String dateStr) {
		
		return dateStr.substring(0, dateStr.lastIndexOf("."));
	}

	@Override
	public String toString() {
		return "Hotissue [id=" + id + ", name=" + name + ", timestamp=" + timestamp + ", articles=" + articles + ", score=" + score + "]";
	}
	
	
}
