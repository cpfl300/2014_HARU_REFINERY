package refinery.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import refinery.dao.HotissueDao;
import refinery.model.Article;
import refinery.model.Hotissue;

@Service
public class HotissueService {
	
	@Autowired
	private HotissueDao hotissueDao;
	
	@Autowired
	private ArticleService articleService;
	
	@Transactional
	public int add(Hotissue hotissue) {
		
		int id = hotissue.hashCode();
		hotissue.setId(id);
		
		try {
			hotissueDao.get(id);
			
		} catch (EmptyResultDataAccessException e) {
			hotissueDao.add(hotissue);

		}
		
		return id;
			
	}

	public int addHotissues(List<Hotissue> hotissues) {
		
		setId(hotissues);
		int[] affectedRows = hotissueDao.addHotissues(hotissues);
		
		return getCount(affectedRows);
	}


	@Transactional
	public int delete(int id) {
			
		try {		
			
			return hotissueDao.delete(id);
			
		} catch (DataIntegrityViolationException e) {
			// do-nothing
			return 0;
		}

	}
	
	
	@Transactional
	public int calcScore(String from, String to) {

		List<Article> articles = articleService.getArticlesBetweenDates(from, to);
		List<Hotissue> hotissues = Hotissue.orderByHotissue(articles);
		
		for (Hotissue hotissue : hotissues) {			
			hotissue.calcScore();
		}
		
		return getCount(hotissueDao.updateScores(hotissues));
	}
	

	public List<Hotissue> getWithArticlesByOrderedScore(int size) {
		
		return this.hotissueDao.getWithArticlesByOrderedScore(size);
	}
	
	
	private int getCount(int[] rows) {
		int count = 0;
		
		for (int row : rows) {
			count += row;
		}
		
		return count;
	}

	private void setId(List<Hotissue> hotissues) {
		
		Iterator<Hotissue> ir = hotissues.iterator();
		while(ir.hasNext()) {
			Hotissue hotissue = ir.next();
			hotissue.setId(hotissue.hashCode());
		}
		
	}

	
}
