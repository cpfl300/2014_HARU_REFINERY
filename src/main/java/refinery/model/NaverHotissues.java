package refinery.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import elixir.model.Hotissue;

public class NaverHotissues {

	public static List<Hotissue> convert(List<NaverHotissue> naverHotissues) {
		List<Hotissue> covertedList = new ArrayList<Hotissue>();
		
		Iterator<NaverHotissue> ir = naverHotissues.iterator();
		while (ir.hasNext()) {
			covertedList.add(ir.next().convert());
		}
		
		return covertedList;
	}

}
