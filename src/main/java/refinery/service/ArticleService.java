package refinery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import refinery.dao.ArticleDao;
import refinery.dao.HotissueDao;
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
	private HotissueDao hotissueDao;
	
	@Autowired
	private HotissueService hotissueService;
	
	@Autowired
	private JournalDao journalDao;
	
	@Autowired
	private SectionDao sectionDao;

	@Transactional
	public void add(Article article) {
		Journal journal = article.getJournal();
		Section section = article.getSection();
		Hotissue hotissue = article.getHotissue();
		
		article.setJournal(journalDao.getByName(journal.getName()));
		article.setSection(sectionDao.getByMinor(section.getMinor()));
		hotissue.setId(hotissueService.add(hotissue));

		articleDao.add(article);
	}

	@Transactional
	public void delete(String id) {
		Hotissue hotissue = articleDao.get(id).getHotissue();
		
		articleDao.delete(id);
		hotissueService.delete(hotissue.getId());

	}
	


}
