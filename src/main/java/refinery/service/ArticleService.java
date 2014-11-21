package refinery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import refinery.dao.ArticleDao;
import refinery.dao.JournalDao;
import refinery.dao.SectionDao;
import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.model.Journal;
import refinery.model.Section;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private HotissueService hotissueService;
	
	@Autowired
	private JournalDao journalDao;
	
	@Autowired
	private SectionDao sectionDao;

	@Transactional
	public int add(Article article) {
		Journal journal = article.getJournal();
		Section section = article.getSection();
		Hotissue hotissue = article.getHotissue();
		
		article.setJournal(journalDao.getByName(journal.getName()));
		article.setSection(sectionDao.getByMinor(section.getMinor()));
		hotissueService.add(hotissue);
		
		int id = article.hashCode();
		article.setId(id);

		try {			
			articleDao.add(article);
		} catch (DuplicateKeyException e) {
			// do-nothing
		}
		
		return id;
	}
	

	public boolean has(int id) {
		
		try {
			articleDao.get(id);
			
			return true;
			
		} catch(EmptyResultDataAccessException e) {
			
			return false;
		}
		
		
	}

	@Transactional
	public int delete(int id) {
		Hotissue hotissue = articleDao.get(id).getHotissue();
		
		int affectedRow = articleDao.delete(id);
		hotissueService.delete(hotissue.getId());
		
		return affectedRow;

	}



}
