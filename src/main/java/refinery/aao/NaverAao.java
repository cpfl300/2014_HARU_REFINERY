package refinery.aao;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import refinery.core.template.GsonMapper;
import refinery.core.template.HttpTemplate;
import refinery.model.NaverArticle;
import refinery.model.NaverArticleCount;
import refinery.model.NaverHotissue;
import refinery.model.NaverResult;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import elixir.model.Signature;

@Component
public class NaverAao {

	@Autowired
	private HttpTemplate httpTemplate;
	
	public NaverArticle getArticle(Signature signature) {
		
		return this.httpTemplate.request(
				"/read.nhn",
				"officeId=?&articleId=?",
				new Object[] { signature.getOfficeId(), signature.getArticleId() },
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


	public List<NaverHotissue> getHotissueList() {
		
		return this.httpTemplate.request(
				"/hotissues.nhn",
				new GsonMapper<List<NaverHotissue>> () {

					@Override
					public List<NaverHotissue> map(Gson gson, JsonReader gsonReader) throws IOException {
						gsonReader.beginObject();
						gsonReader.nextName();
						NaverResult naverResult = gson.fromJson(gsonReader, NaverResult.class);
						gsonReader.endObject();
						
						return naverResult.getHotissues();
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


	public NaverResult getArticleCountList(String sectionId, String date, String hour) {
		if (hour == null) hour = "";
		
		return this.httpTemplate.request(
				"/rankingArticles.nhn",
				"sectionId=?&rankingType=popular_day&datehour=?",
				new Object[] { sectionId, date+hour },
				NaverResult.class
			);
	}
}