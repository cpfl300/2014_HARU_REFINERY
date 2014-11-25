package refinery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import refinery.model.Article;

@Service
public class HalfDayService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Article> save(int n) {
		
		
		
		return null;
	}

}
