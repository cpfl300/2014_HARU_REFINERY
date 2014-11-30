package refinery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elixir.dao.HalfDayDao;
import elixir.model.Article;

@Service
public class HalfDayService {
	
	@Autowired
	private HalfDayDao halfDayDao;

	
	public int addArticles(List<Article> articles) {

		return getCount(halfDayDao.addArticles(articles));
	}
	
	private int getCount(int[] rows) {
		int count = 0;
		
		for (int row : rows) {
			count += row;
		}
		
		return count;
	}

}
