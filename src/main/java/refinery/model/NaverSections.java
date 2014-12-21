package refinery.model;

import java.util.ArrayList;
import java.util.List;

import elixir.model.Section;

public class NaverSections {
	
	public static List<Section> convert(List<NaverSection> naverSections) {
		
		List<List<NaverSection>> naverSectionsList = NaverSections.separate(naverSections);
		List<Section> covertedList = new ArrayList<Section>();
		
		// 마지막 section만 list에 담아 전달
		for (List<NaverSection> ns : naverSectionsList) {
			covertedList.add(ns.get(ns.size()-1).convert());
		}
		
		return covertedList;
	}

	public static List<List<NaverSection>> separate(List<NaverSection> naverSections) {

		if (naverSections.size() < 2) return null;
		
		NaverSection previousSection = naverSections.get(0);
		NaverSection currentSection = null;
		
		List<List<NaverSection>> nssList = new ArrayList<List<NaverSection>>();
		List<NaverSection> currentNss = new ArrayList<NaverSection>();
		currentNss.add(previousSection);
		nssList.add(currentNss);
		
		// NaverSection 개수만큼 순회
		for (int i=1; i<naverSections.size(); i++) {
			currentSection = naverSections.get(i);
			
			// 분리가능성을 확인
			int result = currentSection.separateFrom(previousSection);
			if (result == -1) continue;
			
			// 분리시 새로운 리스트를 생성
			if (result == 1) {
				currentNss = new ArrayList<NaverSection>();
				nssList.add(currentNss);
			}
			
			// 현재 선택된 currentNss에 currentSection을 담음
			currentNss.add(currentSection);
			
			// 현재 섹션을 이전 섹션으로 변경
			previousSection = currentSection;
		}
		
		return nssList;
	}
	
	

}
