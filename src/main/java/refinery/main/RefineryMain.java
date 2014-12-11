package refinery.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import refinery.config.RefineryConfig;

public class RefineryMain {
	
	private static final Logger log = LoggerFactory.getLogger(RefineryMain.class);

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RefineryConfig.class);
		
		log.debug("context: " + context);
		
		
	}

}
