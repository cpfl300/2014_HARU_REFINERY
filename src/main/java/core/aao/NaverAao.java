package core.aao;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import refinery.model.NaverArticle;
import refinery.model.NaverArticleList;
import refinery.model.NaverHotissueList;

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
	

	public NaverArticleList getArticleList(String datehour) {

		return this.httpTemplate.request(
					"/articles.nhn",
					"datehour=?",
					new Object[] { datehour },
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
	

	public NaverArticleList getArticleListOfHotissue(String componentId) {
		
		return this.httpTemplate.request(
				"/hotissueArticles.nhn",
				"componentId=?",
				new Object[] { componentId },
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

	/*
	public NaverArticleList getArticleListAtPopularDay(String datehour, String sectionId) {
		
		return this.httpTemplate.request(
				"/rankingArticles.nhn",
				"sectionId=?&rankingType=popular_day&datehour=?",
				new Object[] { sectionId, datehour },
				new GsonMapper<NaverArticleList> () {

					@Override
					public NaverArticleList map(Gson gson, JsonReader gsonReader) throws IOException {
						NaverArticleList naverArticleList = new NaverArticleList();
						List<NaverArticle> naverArticles = new ArrayList<NaverArticle>();
						NaverArticle naverArticle = null;
						//result
						gsonReader.beginObject();
						gsonReader.nextName();
						
						// actual
						gsonReader.beginObject();
						gsonReader.nextName();
						naverArticleList.setDatehour(gsonReader.nextString());
						
						gsonReader.nextName();
						naverArticleList.setSectionId(gsonReader.nextString());
						
						gsonReader.nextName();
						naverArticleList.setRankingType(gsonReader.nextString());
						
						gsonReader.nextName();
						gsonReader.beginArray();
						
						while(gsonReader.peek() == JsonToken.BEGIN_OBJECT) {
							naverArticle = new NaverArticle();
							gsonReader.beginObject();
							
							log.debug("name: " + gsonReader.nextName());
							naverArticle.setOfficeId(gsonReader.nextString());
							
							// pass officeName
//							gsonReader.nextName();
							log.debug("name: " + gsonReader.nextName());
							gsonReader.skipValue();
							
//							gsonReader.nextName();
							log.debug("name: " + gsonReader.nextName());
							naverArticle.setArticleId(gsonReader.nextString());
							
//							gsonReader.nextName();
							log.debug("name: " + gsonReader.nextName());
							naverArticle.setSectionId(gsonReader.nextString());
							
//							gsonReader.nextName();
							log.debug("name: " + gsonReader.nextName());
							naverArticle.setType(gsonReader.nextString());
							
							// pass serviceDate
//							gsonReader.nextName();
							log.debug("name: " + gsonReader.nextName());
							gsonReader.skipValue();
							
//							gsonReader.nextName();
							log.debug("name: " + gsonReader.nextName());
							naverArticle.setServiceTime(gsonReader.nextString());
							
//							gsonReader.nextName();
							log.debug("name: " + gsonReader.nextName());
							naverArticle.setHitCount(gsonReader.nextString());
							
//							gsonReader.nextName();
							log.debug("name: " + gsonReader.nextName());
							naverArticle.setReadCount(gsonReader.nextString());
							
//							gsonReader.nextName();
							log.debug("name: " + gsonReader.nextName());
							naverArticle.setRank(gsonReader.nextString());
							
							gsonReader.endObject();
							naverArticles.add(naverArticle);
						}
						gsonReader.endArray();
						gsonReader.endObject();
						gsonReader.endObject();
						
						return naverArticleList;
					}
					
				}
			);
	}
	*/




}