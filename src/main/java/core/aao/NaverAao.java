package core.aao;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import refinery.model.NaverArticle;
import refinery.model.NaverArticleCount;
import refinery.model.NaverArticleCountList;
import refinery.model.NaverArticleList;
import refinery.model.NaverHotissueList;
import refinery.model.NaverResult;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import core.template.GsonMapper;
import core.template.HttpTemplate;

@Component
public class NaverAao {
	private static final Logger log = LoggerFactory.getLogger(NaverAao.class);
	
	
	@Autowired
	private HttpTemplate httpTemplate;

	public NaverArticle getArticle(String officeId, String articleId) {
		
		return this.httpTemplate.request(
				"/read.nhn",
				"officeId=?&articleId=?",
				new Object[] { officeId, articleId },
				new GsonMapper<NaverArticle> () {

					@Override
					public NaverArticle map(Gson gson, JsonReader gsonReader) throws IOException {
						gsonReader.beginObject();
						gsonReader.nextName();
						NaverArticle naverArticle = gson.fromJson(gsonReader, NaverArticle.class);
						gsonReader.endObject();
						
						return naverArticle;
					}
					
					
				}
				
			);
	}
	

	public List<NaverArticle> getArticleList(String datehour) {

		return this.httpTemplate.request(
					"/articles.nhn",
					"datehour=?",
					new Object[] { datehour },
					new GsonMapper<List<NaverArticle>> () {

						@Override
						public List<NaverArticle> map(Gson gson, JsonReader gsonReader) throws IOException {
							gsonReader.beginObject();
							gsonReader.nextName();
							NaverResult naverResult = gson.fromJson(gsonReader, NaverResult.class);
							gsonReader.endObject();
							
							return naverResult.getArticles();
						}
						
					}
				);
				
	}
	

	public List<NaverArticle> getArticleListByHotissueId(String hotissueId) {
		
		return this.httpTemplate.request(
				"/hotissueArticles.nhn",
				"componentId=?",
				new Object[] { hotissueId },
				new GsonMapper<List<NaverArticle>> () {

					@Override
					public List<NaverArticle> map(Gson gson, JsonReader gsonReader) throws IOException {
						gsonReader.beginObject();
						gsonReader.nextName();
						NaverResult naverResult = gson.fromJson(gsonReader, NaverResult.class);
						gsonReader.endObject();
						
						return naverResult.getArticles();
					}
					
				}
			);
	}


	public NaverHotissueList getHotissueList() {
		
		return this.httpTemplate.request(
				"/hotissues.nhn",
				new GsonMapper<NaverHotissueList> () {

					@Override
					public NaverHotissueList map(Gson gson, JsonReader gsonReader) throws IOException {
						gsonReader.beginObject();
						gsonReader.nextName();
						NaverHotissueList naverHotissueList = gson.fromJson(gsonReader, NaverHotissueList.class);
						gsonReader.endObject();
						
						return naverHotissueList;
					}
					
				}
			);
	}


	public List<NaverArticleCount> getArticleCountListInRealtime(String sectionId) {
		return this.httpTemplate.request(
				"/rankingArticlesRealtime.nhn",
				"sectionId=?&rankingType=hit",
				new Object[] { sectionId },
				new GsonMapper<List<NaverArticleCount>> () {

					@Override
					public List<NaverArticleCount> map(Gson gson, JsonReader gsonReader) throws IOException {
						gsonReader.beginObject();
						gsonReader.nextName();
						NaverResult naverResult = gson.fromJson(gsonReader, NaverResult.class);
						gsonReader.endObject();
						
						return naverResult.getArticleCountList();
					}
					
				}
			);
	}


	public NaverArticleList getArticleCountListAtHour(String datehour, String sectionId) {
		return this.httpTemplate.request(
				"/rankingArticles.nhn",
				"sectionId=?&rankingType=popular_day&datehour=?",
				new Object[] { sectionId, datehour },
				new GsonMapper<NaverArticleList> () {

					@Override
					public NaverArticleList map(Gson gson, JsonReader gsonReader) throws IOException {
						gsonReader.beginObject();
						gsonReader.nextName();
						NaverArticleList naverArticleList = gson.fromJson(gsonReader, NaverArticleList.class);
						gsonReader.endObject();
						
						return naverArticleList;
					}
					
				}
			);
	}


	public List<NaverArticle> getArticleCountListAtDay(String datehour, String sectionId) {
		return this.httpTemplate.request(
				"/rankingArticles.nhn",
				"sectionId=?&rankingType=popular_day&datehour=?",
				new Object[] { sectionId, datehour },
				new GsonMapper<List<NaverArticle>> () {

					@Override
					public List<NaverArticle> map(Gson gson, JsonReader gsonReader) throws IOException {
						gsonReader.beginObject();
						gsonReader.nextName();
						NaverResult naverResult = gson.fromJson(gsonReader, NaverResult.class);
						gsonReader.endObject();
						
						return naverResult.getArticles();
					}
					
				}
			);
	}
}