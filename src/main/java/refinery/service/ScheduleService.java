package refinery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import elixir.utility.ElixirUtils;

@Service
public class ScheduleService {
	
	@Autowired
	private RefineryService refineryService;
	
	@Scheduled(cron="* 0/10 * * * ?")
	public void saveHotissueList() {
		refineryService.saveHotissueList();
	}
	
	@Scheduled(cron="* 0/10 * * * ?")
	public void saveArticleList() {
		String datehour = ElixirUtils.format("yyyyMMddHHmm", ElixirUtils.now()).substring(0, 11);
		refineryService.saveArticleList(datehour);
	}
	
}
