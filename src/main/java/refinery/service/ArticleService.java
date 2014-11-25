package refinery.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

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
	
	private static final String BEFORE_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
	private static final String AFTER_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private SimpleDateFormat beforeFormat;
	private SimpleDateFormat afterFormat;
	
	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private HotissueService hotissueService;
	
	@Autowired
	private JournalDao journalDao;
	
	@Autowired
	private SectionDao sectionDao;
	
	@PostConstruct
	public void postConstructer() {
		TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
		beforeFormat = new SimpleDateFormat(BEFORE_DATE_FORMAT);
		
		beforeFormat.setTimeZone(zone);
		afterFormat = new SimpleDateFormat(AFTER_DATE_FORMAT);
		afterFormat.setTimeZone(zone);
	}

	@Transactional
	public int add(Article article) {
		setJournalAndSection(article);
		hotissueService.add(article.getHotissue());
		
		int id = article.hashCode();
		article.setId(id);

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
		
		int[] rowState = articleDao.updateScore(articles);
		
		return getCount(rowState);
	}


	String[] getBetweenDate(Calendar cal) {
		String to  = beforeFormat.format(cal.getTime());
		
		cal.add(Calendar.HOUR_OF_DAY, -12);
		String from = beforeFormat.format(cal.getTime());
		
		return new String[] {from, to};
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

	public List<Article> getArticlesOfHalfDayByCalendarTo(Calendar to) {
		String[] dates = new String[2];
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dates[1] = format.format(to.getTime());
		
		to.add(Calendar.HOUR, -12);
		dates[0] = format.format(to.getTime());
		
		List<Article> articles = articleDao.getArticlesBetweenDates(dates[0], dates[1]);
		
		return articles;
	}

	public List<Article> getByOrderedScore(int size) {
		
		return articleDao.getByOrderedScore(size);
	}
	

}
