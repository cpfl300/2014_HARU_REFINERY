package refinery.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import refinery.dao.HotissueDao;
import refinery.model.Hotissue;


@RunWith(MockitoJUnitRunner.class)
public class HotissueServiceTest {
	
	@InjectMocks
	private HotissueService hotissueService;
	
	@Mock
	private HotissueDao hotissueDao;
	
	private Hotissue hotissue1;
	private Hotissue hotissue2;
	private Hotissue hotissue3;
	
	@Before
	public void setup() {
		// fixtures
		hotissue1 = new Hotissue("hotissue1");
		hotissue2 = new Hotissue("hotissue2");
		hotissue3 = new Hotissue("hotissue3");
	}

	@Test
	public void add() {
		when(hotissueDao.get(hotissue1.hashCode())).thenReturn(hotissue1);
		when(hotissueDao.get(hotissue2.hashCode())).thenReturn(hotissue2);
		when(hotissueDao.get(hotissue3.hashCode())).thenReturn(hotissue3);
		
		int actualHotissue1Key = hotissueService.add(hotissue1); 
		assertThat(actualHotissue1Key, is(hotissue1.hashCode()));
		
		int actualHotissue2Key = hotissueService.add(hotissue2); 
		assertThat(actualHotissue2Key, is(hotissue2.hashCode()));
		
		int actualHotissue3Key = hotissueService.add(hotissue3); 
		assertThat(actualHotissue3Key, is(hotissue3.hashCode()));
	}
	
	@Test
	public void notAdd() {
		when(hotissueDao.get(hotissue1.hashCode())).thenThrow(EmptyResultDataAccessException.class);
		
		int actualHotissue1Key = hotissueService.add(hotissue1); 
		assertThat(actualHotissue1Key, is(hotissue1.hashCode()));
		
		int actualHotissue2Key = hotissueService.add(hotissue2); 
		assertThat(actualHotissue2Key, is(hotissue2.hashCode()));
		
		int actualHotissue3Key = hotissueService.add(hotissue3); 
		assertThat(actualHotissue3Key, is(hotissue3.hashCode()));
	}
	
	@Test
	public void delete() {
		when(hotissueDao.delete(hotissue1.hashCode())).thenReturn(1);
		when(hotissueDao.delete(hotissue2.hashCode())).thenReturn(1);
		when(hotissueDao.delete(hotissue3.hashCode())).thenReturn(1);
		
		assertThat(hotissueService.delete(hotissue1.hashCode()), is(1));
		assertThat(hotissueService.delete(hotissue2.hashCode()), is(1));
		assertThat(hotissueService.delete(hotissue3.hashCode()), is(1));
	}
	
	@Test
	public void notDelete() {
		when(hotissueDao.delete(hotissue1.hashCode())).thenThrow(DataIntegrityViolationException.class);
		assertThat(hotissueService.delete(hotissue1.hashCode()), is(0));
		
		when(hotissueDao.delete(hotissue2.hashCode())).thenThrow(DataIntegrityViolationException.class);
		assertThat(hotissueService.delete(hotissue2.hashCode()), is(0));
		
		when(hotissueDao.delete(hotissue3.hashCode())).thenThrow(DataIntegrityViolationException.class);
		assertThat(hotissueService.delete(hotissue3.hashCode()), is(0));
	}

}
