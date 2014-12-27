package refinery.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import elixir.archive.StatusArchive;
import elixir.model.Hotissue;
import elixir.model.HotissueTest;
import elixir.model.Section;
import elixir.model.Status;
import elixir.service.HotissueService;

@RunWith(MockitoJUnitRunner.class)
public class RefineryServiceTest {
	
	@InjectMocks
	private RefineryService refineryService;
	
	@Mock
	private NaverService naverService;
	
	@Mock
	private HotissueService hotissueService;
	
	@Mock
	private StatusArchive statusArcive;
	
	private List<Hotissue> hotissues;
	private Status status;

	
	@Before
	public void setup() {
		status = new Status("20141207", false);
	}

	
	@Test
	public void saveHotissueList() {
		// given
		prepareHotissues();
		when(statusArcive.getStatus()).thenReturn(status);
		when(naverService.getHotissueList()).thenReturn(hotissues);
		
		// when
		refineryService.saveHotissueList();
		
		// then
		verify(hotissueService, times(1)).addAll(status, hotissues);
	}
	

	private void prepareHotissues() {
		hotissues = Arrays.asList(new Hotissue[]{
			HotissueTest.create("887522", "연애지침서", new Section("103")),	
			HotissueTest.create("893847", "화제의 판결", new Section("102")),	
			HotissueTest.create("895439", "지구촌 화제", new Section("104"))	
		});
		
	}
}
