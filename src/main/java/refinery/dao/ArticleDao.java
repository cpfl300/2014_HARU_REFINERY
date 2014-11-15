package refinery.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.model.Journal;
import refinery.model.Section;


@Repository
public class ArticleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Article> articleMapper = (rs, rowNum) -> {		
		Article article = new Article();
		Hotissue hotissue = new Hotissue(rs.getInt("hotissues_id"));
		Journal journal = new Journal(rs.getInt("journals_id"));
		Section section = new Section(rs.getInt("minor_sections_id"));
		
		article.setHotissue(hotissue);
		article.setJournal(journal);
		article.setSection(section);
		
		article.setId(rs.getString("id"));
		article.setTitle(rs.getString("title"));
		article.setDate(rs.getString("date"));
		article.setContent(rs.getString("content"));
		article.setHits(rs.getInt("hits"));
		article.setCompletedReadingCount(rs.getInt("completed_reading_count"));
		article.setTimestamp(rs.getString("timestamp"));
		
		return article;
	};


	public int getCount() {
		
		return this.jdbcTemplate.queryForInt("select count(*) from articles");
		
	}

	public int deleteAll() {
		
		return this.jdbcTemplate.update("delete from articles");

	}

	public int add(Article article) {

		return this.jdbcTemplate.update(
				"insert into articles(id, hotissues_id, title, journals_id, minor_sections_id, date, content, hits, completed_reading_count) values (?,?,?,?,?,?,?,?,?)",
				article.getId(),
				article.getHotissue().getId(),
				article.getTitle(),
				article.getJournal().getId(),
				article.getSection().getId(),
				article.getDate(),
				article.getContent(),
				article.getHits(),
				article.getCompletedReadingCount()
		);
		
	}

	public Article get(String id) {
		
		return this.jdbcTemplate.queryForObject (
					"SELECT * FROM articles WHERE id = ?",
					new Object[]{id},
					this.articleMapper
				);
	}

	public int delete(String id) {
		
		return this.jdbcTemplate.update(
					"DELETE FROM articles WHERE id = ?",
					new Object[] {id}
				);
		
	}

}
