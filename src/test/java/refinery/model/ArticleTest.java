package refinery.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ArticleTest {
	
	private Article article1;
	private Article article2;
	private Article article3;
	
	@Before
	public void setup() {
		Journal journal1 = new Journal(84, "인벤", "스포츠/연예");
		Section section1 = new Section(3, "정치", "북한");
		Hotissue hotissue1 = new Hotissue(1, "hotissue1");
		article1 = new Article(1, hotissue1, journal1, section1, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		
		
		Journal journal2 = new Journal(10, "한국일보", "종합");
		Section section2 = new Section(10, "경제", "금융");
		Hotissue hotissue2 = new Hotissue(2, "hotissue2");
		article2 = new Article(2, hotissue2, journal2, section2, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000, 20.1);
		
		
		Journal journal3 = new Journal(23, "전자신문", "IT");
		Section section3 = new Section(23, "사회", "언론");
		Hotissue hotissue3 = new Hotissue(3, "hotissue3");
		article3 = new Article(3, hotissue3, journal3, section3, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000, 10.1);
	}

	@Test
	public void hashcode() {
		Article actual1 = new Article(new Hotissue("hotissue1"), new Journal("인벤"), new Section("북한"), "title1", "1111-01-01 01:11:11");
		assertThat(actual1.hashCode(), is(article1.hashCode()));
		
		Article actual2 = new Article(new Hotissue("hotissue2"), new Journal("한국일보"), new Section("금융"), "title2", "1222-02-02 02:11:11");
		assertThat(actual2.hashCode(), is(article2.hashCode()));
		
		Article actual3 = new Article(new Hotissue("hotissue3"), new Journal("전자신문"), new Section("언론"), "title3", "1333-03-03 03:11:11");
		assertThat(actual3.hashCode(), is(article3.hashCode()));
	}
	
	@Test
	public void notHashcode() {
		Article actual1_1 = new Article(new Hotissue("hotissue1-----"), new Journal("인벤"), new Section("북한"), "title1", "1111-01-01 01:11:11");
		assertThat(actual1_1.hashCode(), not(is(article1.hashCode())));
		
		Article actual1_2 = new Article(new Hotissue("hotissue1"), new Journal("인벤-----"), new Section("북한"), "title1", "1111-01-01 01:11:11");
		assertThat(actual1_2.hashCode(), not(is(article1.hashCode())));
		
		Article actual1_3 = new Article(new Hotissue("hotissue1"), new Journal("인벤"), new Section("북한-----"), "title1", "1111-01-01 01:11:11");
		assertThat(actual1_3.hashCode(), not(is(article1.hashCode())));
		
		
		Article actual2 = new Article(new Hotissue("hotissue2"), new Journal("한국일보"), new Section("금융"), "title2-----", "1222-02-02 02:11:11");
		assertThat(actual2.hashCode(), not(is(article2.hashCode())));
		
		Article actual3 = new Article(new Hotissue("hotissue3"), new Journal("전자신문"), new Section("언론"), "title3", "1333-03-03 03:11:11-----");
		assertThat(actual3.hashCode(), not(is(article3.hashCode())));
	}

}
