package refinery.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import refinery.config.RefineryConfig;
import elixir.config.ElixirConfig;

public class RefineryMain {
	
	private static final Logger log = LoggerFactory.getLogger(RefineryMain.class);

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext parent = new AnnotationConfigApplicationContext(ElixirConfig.class);
		AnnotationConfigApplicationContext child = new AnnotationConfigApplicationContext();
		child.setParent(parent);
		child.register(RefineryConfig.class);
		child.refresh();
		
		log.debug("start");
		
	}

}
