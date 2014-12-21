package refinery.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import refinery.config.RefineryConfig;
import refinery.config.RefineryServiceConfig;
import elixir.config.ElixirConfig;

public class RefineryMain {
	
	private static final Logger log = LoggerFactory.getLogger(RefineryMain.class);

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext parent = new AnnotationConfigApplicationContext(ElixirConfig.class);
		
		AnnotationConfigApplicationContext firstChild = new AnnotationConfigApplicationContext();
		firstChild.setParent(parent);
		firstChild.register(RefineryConfig.class);
		firstChild.refresh();
		
		AnnotationConfigApplicationContext secondChild = new AnnotationConfigApplicationContext();
		secondChild.setParent(firstChild);
		secondChild.register(RefineryServiceConfig.class);
		secondChild.refresh();
		
		log.debug("start");
		
	}

}
