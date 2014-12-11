package refinery.model;

import java.util.List;

public class NaverHotissueList {
	
	List<NaverHotissue> hotissues;

	public List<NaverHotissue> getHotissues() {
		return hotissues;
	}

	public void setHotissues(List<NaverHotissue> hotissues) {
		this.hotissues = hotissues;
	}

	@Override
	public String toString() {
		return "NaverHotissueList [hotissues=" + hotissues + "]";
	}
	
}
