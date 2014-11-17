package refinery.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class HotissueTest {
	
	private Hotissue hotissue1;
	private Hotissue hotissue2;
	private Hotissue hotissue3;
	
	@Before
	public void setup() {
		hotissue1 = new Hotissue("hotissue1");
		hotissue2 = new Hotissue("hotissue2");
		hotissue3 = new Hotissue("hotissue3");
	}
	
	@Test
	public void hashcode() {
		Hotissue actual1 = new Hotissue("hotissue1");
		assertThat(actual1.hashCode(), is(hotissue1.hashCode()));
		
		Hotissue actual2 = new Hotissue("hotissue2");
		assertThat(actual2.hashCode(), is(hotissue2.hashCode()));
		
		Hotissue actual3 = new Hotissue("hotissue3");
		assertThat(actual3.hashCode(), is(hotissue3.hashCode()));
	}
	
	@Test
	public void hashcodeByName() {
		Hotissue actual1 = new Hotissue(1, "hotissue1");
		assertThat(actual1.hashCode(), is(hotissue1.hashCode()));
		
		Hotissue actual2 = new Hotissue(2, "hotissue2");
		assertThat(actual2.hashCode(), is(hotissue2.hashCode()));
		
		Hotissue actual3 = new Hotissue(3, "hotissue3");
		assertThat(actual3.hashCode(), is(hotissue3.hashCode()));
	}
	
	
	@Test
	public void notHashcode() {
		Hotissue actual1 = new Hotissue(1, "hotissue11");
		assertThat(actual1.hashCode(), not(is(hotissue1.hashCode())));
		
		Hotissue actual2 = new Hotissue(2, "hotissue22");
		assertThat(actual2.hashCode(), not(is(hotissue2.hashCode())));
		
		Hotissue actual3 = new Hotissue(3, "hotissue33");
		assertThat(actual3.hashCode(), not(is(hotissue3.hashCode())));
	}


}
