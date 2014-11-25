package refinery.utility;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import org.junit.Test;

public class RefineryUtilsTest {

	@Test
	public void getServiceDatesByTime() {
		String[] actualDates1 = RefineryUtils.getServiceDatesByTime(2014, Calendar.DECEMBER, 7, 6);
		String[] actualDates2 = RefineryUtils.getServiceDatesByTime(2014, Calendar.DECEMBER, 7, 18);
		
		assertThat(actualDates1[0], is("2014-12-06 18:00:00"));
		assertThat(actualDates1[1], is("2014-12-07 06:00:00"));
		assertThat(actualDates2[0], is("2014-12-07 06:00:00"));
		assertThat(actualDates2[1], is("2014-12-07 18:00:00"));
	}
	
	@Test
	public void getDate() {
		String actualDate1 = RefineryUtils.getDate(2014, Calendar.DECEMBER, 7, 6);
		String actualDate2 = RefineryUtils.getDate(2014, Calendar.DECEMBER, 7, 18);
		
		assertThat(actualDate1, is("2014-12-07 06:00:00"));
		assertThat(actualDate2, is("2014-12-07 18:00:00"));
		
	}
	
//	@Test
//	public void getToday() {
//		String actualToday = RefineryUtils.getToday();
//		
//		assertThat(actualDate1, is("2014-12-07 06:00:00"));
//		assertThat(actualDate2, is("2014-12-07 18:00:00"));
//	}

}
