package refinery.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import elixir.model.Section;

public class NaverSections {

	public static List<Section> convert(List<NaverSection> naverSections) {
		List<Section> covertedList = new ArrayList<Section>();
		
		Iterator<NaverSection> ir = naverSections.iterator();
		while (ir.hasNext()) {
			covertedList.add(ir.next().convert());
		}
		
		return covertedList;
	}

}
