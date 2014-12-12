package core.aao;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import refinery.model.NaverArticle;
import refinery.model.NaverArticleCountList;
import refinery.model.NaverArticleList;
import refinery.model.NaverHotissueList;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import core.template.GsonMapper;
import core.template.HttpTemplate;

@Component
public class NaverAao {
	
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
	

	public NaverArticleList getArticleListByHotissueId(String hotissueId) {
		
		return this.httpTemplate.request(
				"/hotissueArticles.nhn",
				"componentId=?",
				new Object[] { hotissueId },
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


	public NaverArticleCountList getArticleCountListInRealtime(String sectionId) {
		return this.httpTemplate.request(
				"/rankingArticlesRealtime.nhn",
				"sectionId=?&rankingType=hit",
				new Object[] { sectionId },
				new GsonMapper<NaverArticleCountList> () {

					@Override
					public NaverArticleCountList map(Gson gson, JsonReader gsonReader) throws IOException {
						gsonReader.beginObject();
						gsonReader.nextName();
						NaverArticleCountList naverArticleCountList = gson.fromJson(gsonReader, NaverArticleCountList.class);
						gsonReader.endObject();
						
						return naverArticleCountList;
					}
					
				}
			);
	}
}