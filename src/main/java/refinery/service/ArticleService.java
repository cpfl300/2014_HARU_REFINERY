package refinery.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger log = LoggerFactory.getLogger(ArticleService.class);
	
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
		setJournalAndSection(article);
		hotissueService.add(article.getHotissue());
		
		int id = article.hashCode();
		article.setId(id);
		
		log.debug("article id: " + id);

		try {			
			articleDao.add(article);
		} catch (DuplicateKeyException e) {
			// do-nothing
		}
		
		return id;
	}
	
	@Transactional
	public int addArticles(List<Article> articles) {
		List<Hotissue> hotissues = new ArrayList<Hotissue>();
		
		Iterator<Article> ir = articles.iterator();
		while(ir.hasNext()) {
			Article article = ir.next();
			setJournalAndSection(article);
			hotissues.add(article.getHotissue());
		}
		
		hotissueService.addHotissues(hotissues);
		
		int[] affectedRows = articleDao.addArticles(articles);
		
		return getCount(affectedRows);
		
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
	
	@Transactional
	public int calcScore(String from, String to) {		
		List<Article> articles = articleDao.getArticlesBetweenDates(from, to);
		Iterator<Article> ir = articles.iterator();
		while (ir.hasNext()) {
			ir.next().clacScore();
		}
		
		int[] rowState = articleDao.updateScores(articles);
		
		return getCount(rowState);
	}

	private void setJournalAndSection(Article article) {
		Journal journal = article.getJournal();
		Section section = article.getSection();
		
		article.setJournal(journalDao.getByName(journal.getName()));
		article.setSection(sectionDao.getByMinor(section.getMinor()));
	}
	
	private int getCount(int[] rows) {
		int count = 0;
		
		for (int row : rows) {
			count += row;
		}
		
		return count;
	}

	public List<Article> getByOrderedScore(int size) {
		
		return articleDao.getByOrderedScore(size);
	}

	public List<Article> getArticlesBetweenDates(String from, String to) {
		
		return articleDao.getArticlesBetweenDates(from, to);
	}
	

}
