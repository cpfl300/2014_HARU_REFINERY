package refinery.scheduler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import refinery.service.RefineryService;
import elixir.archive.StatusArchive;
import elixir.utility.ElixirUtils;

@Component
public class TaskScheduler {
	
	@Autowired
	private RefineryService refineryService;
	
	@Autowired
	private StatusArchive statusArchive;
	
	@Scheduled(cron="0 0 0/1 * * ?")
	public void syncStatus() {
		Date now = ElixirUtils.now();
		statusArchive.sync(now);
	}
	

	@Scheduled(cron="0 50 5/12 * * ?")
	public void execAlgorithm() {
		
		// execAlgorithm
		
		// 
		saveHotissueList();
	}
	
	
	@Scheduled(cron="* 0/10 * * * ?")
	public void saveArticleList() {
		String datehour = ElixirUtils.format("yyyyMMddHHmm", ElixirUtils.now()).substring(0, 11);
		refineryService.saveArticleList(datehour);
	}
	
	@Async
	private void saveHotissueList() {
		refineryService.saveHotissueList();
	}
	
}
