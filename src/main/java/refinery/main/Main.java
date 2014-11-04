package refinery.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import refinery.config.Config;
import refinery.engine.Engine;

public class Main {
	
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		log.debug("context: " + context);
		log.debug("context: " + context);
		log.debug("context: " + context);
		
		Engine engine = context.getBean("engine", Engine.class);
		engine.start();
	}

}
