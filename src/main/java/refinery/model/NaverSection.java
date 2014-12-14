package refinery.model;

import java.util.Iterator;
import java.util.List;

import refinery.model.convertible.Convertible;
import refinery.model.convertible.NaverConvertCheckOutNextException;
import refinery.model.convertible.NaverConvertFailureException;
import elixir.model.Section;

public class NaverSection implements Convertible<Section>{
	
	private String sectionId;
	private String sectionName;
	
	// empty
	public NaverSection() { }
	
	// setter getter
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	// converter
	@Override
	public Section convert() {
		String first = this.sectionId.substring(0,1);
		
		if (first.equals("1")) 			throw new NaverConvertCheckOutNextException("need to check next naver convertible, current first string is " + first);
		else if (first.equals("2")) 	return new Section(this.sectionId, this.sectionName);
		else 							throw new NaverConvertFailureException("can not convert naver convertible, current first string is " + first);
	}

	public static Section convert(List<NaverSection> naverSections) {
		int size = naverSections.size();
		
		Iterator<NaverSection> ir = naverSections.iterator();
		Section section = null;

		while(ir.hasNext()) {
			NaverSection naverSection = ir.next();
			
			try {
				section = naverSection.convert();
				break;
				
			} catch (NaverConvertCheckOutNextException e) {
				if (size < 2) {
					throw new NaverConvertFailureException("can not convert naver convertible, NaverSectionList size greater then 2, current size is " + size);
				}
				continue;
				
			} catch (NaverConvertFailureException e) {
				throw e;
			}
		}
		
		return section;
	}
}