package refinery.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import refinery.scheduler.config.RefinerySchedulerConfig;

public class RefineryMain {
	
	private static final Logger log = LoggerFactory.getLogger(RefineryMain.class);

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RefinerySchedulerConfig.class);
		
		log.debug("context: " + context);
		
		
	}

}
