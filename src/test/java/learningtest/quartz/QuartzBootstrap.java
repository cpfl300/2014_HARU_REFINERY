package learningtest.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import refinery.config.Config;

public class QuartzBootstrap {
	
	private static final Logger log = LoggerFactory.getLogger(QuartzBootstrap.class);

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		
		log.debug("api host: " + (String) context.getBean("host"));
		
	}

}
