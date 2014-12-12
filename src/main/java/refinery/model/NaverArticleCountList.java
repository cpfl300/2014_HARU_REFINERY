package refinery.model;

import java.util.List;

public class NaverArticleCountList {
	
	private List<NaverArticleCount> articleCountList;

	public List<NaverArticleCount> getNaverArticleCounts() {
		return articleCountList;
	}

	public void setNaverArticleCounts(List<NaverArticleCount> naverArticleCounts) {
		this.articleCountList = naverArticleCounts;
	}

	@Override
	public String toString() {
		return "NaverArticleCountList [naverArticleCounts=" + articleCountList + "]";
	}
}
