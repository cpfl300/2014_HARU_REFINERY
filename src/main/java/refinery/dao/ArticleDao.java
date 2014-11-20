package refinery.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
		
		article.setId(rs.getInt("id"));
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

	public void add(Article article) {

		this.jdbcTemplate.update(
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

	public Article get(int id) {
		
		return this.jdbcTemplate.queryForObject (
					"SELECT * FROM articles WHERE id = ?",
					new Object[]{id},
					this.articleMapper
				);
	}

	public int delete(int id) {
		
		return this.jdbcTemplate.update(
					"DELETE FROM articles WHERE id = ?",
					new Object[] {id}
				);
		
	}

	public void addBatch(final List<Article> articles) {
		
		this.jdbcTemplate.batchUpdate(
					"insert into articles(id, hotissues_id, title, journals_id, minor_sections_id, date, content, hits, completed_reading_count) values (?,?,?,?,?,?,?,?,?)",
					new BatchPreparedStatementSetter() {
	
						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Article article = articles.get(i);
							ps.setInt(1, article.getId());
							ps.setInt(2, article.getHotissue().getId());
							ps.setString(3, article.getTitle());
							ps.setInt(4, article.getJournal().getId());
							ps.setInt(5, article.getSection().getId());
							ps.setString(6, article.getDate());
							ps.setString(7, article.getContent());
							ps.setInt(8, article.getHits());
							ps.setInt(9, article.getCompletedReadingCount());
						}
	
						@Override
						public int getBatchSize() {
							
							return articles.size();
						}
						
					}
				);
		
	}

}
