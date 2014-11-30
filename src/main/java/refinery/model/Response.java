package refinery.model;

import java.util.List;

public class Response {
	
	private List<ResponseArticle> responseArticles;

	public List<ResponseArticle> getResponseArticles() {
		return responseArticles;
	}

	public void setResponseArticles(List<ResponseArticle> articles) {
		this.responseArticles = articles;
	}

	@Override
	public String toString() {
		return "Response [responseArticles=" + responseArticles + "]";
	}

}
