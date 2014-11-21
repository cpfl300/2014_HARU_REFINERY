package refinery.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import core.naver.news.api.NaverNewsAPI;
import refinery.dao.ArticleDao;

@Component
public class Engine {
	
	@Autowired
	private NaverNewsAPI naverAPI;
	
	@Autowired
	private ArticleDao articleDao;
	
	private static final Logger log = LoggerFactory.getLogger(Engine.class);
	
	public void start() {
		
		// pump
		// 5분마다 급유

		
		// extractor
		// 급유받았을 때 필요한 부분만 추출
		
		
		
		// refine
		// 상황에 맞게 정제
		
		
		// store
		// 정제된 자료를 보관
//		articleDao.set(articles);
		
	}

}
