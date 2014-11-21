package scheduler.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NaverNewsTask {
	
	private static final Logger log = LoggerFactory.getLogger(NaverNewsTask.class);

	public void getNews() {
		
		log.debug("naverNewsTask is OK");
	}

}
