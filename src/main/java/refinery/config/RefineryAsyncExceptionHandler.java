package refinery.config;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class RefineryAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(RefineryAsyncExceptionHandler.class);

	 @Override
	    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
	        log.debug("Exception message - " + throwable.getMessage());
	        log.debug("Method name - " + method.getName());
	        for (Object param : obj) {
	        	log.debug("Parameter value - " + param);
	        }
	    }

}