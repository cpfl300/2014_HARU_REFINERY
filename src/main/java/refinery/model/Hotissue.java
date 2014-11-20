package refinery.model;

import java.util.List;

public class Hotissue {
	
	private int id;
	private String name;
	private String timestamp;
	private List<Article> articles;
	private double score;
	
	public Hotissue() {
		
	}
	
	public Hotissue(int id) {
		this(id, null, null);
	}
	
	public Hotissue(String name) {
		this(0, name, null);
	}
	
	public Hotissue(int id, String name) {
		this(id, name, null);
	}

	public Hotissue(int id, String name, String timestamp) {
		this.id = id;
		this.name = name;
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotissue other = (Hotissue) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return "Hotissue [id=" + id + ", name=" + name + ", timestamp=" + timestamp + ", articles=" + articles + ", score=" + score + "]";
	}
	
}
