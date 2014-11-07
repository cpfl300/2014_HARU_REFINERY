package learningtest.jackson;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import refinery.config.Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class, loader=AnnotationConfigContextLoader.class)
public class JacksonTest {
	
	@Autowired
	private ApplicationContext context;
	
	private static final Logger log = LoggerFactory.getLogger(JacksonTest.class);

	@Test
	public void mapJson() throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
		ObjectMapper mapper = new ObjectMapper();		
		Resource resource = context.getResource("classpath:learningtest/jackson/user.json");
		
		File jsonFile = resource.getFile();
		User convertedUser = mapper.readValue(jsonFile, User.class);
		
		User user = new User("dec7");
		assertSameUser(convertedUser, user);

	}

	private void assertSameUser(User actual, User expected) {
		assertThat(actual.getName(), is(expected.getName()));
		
	}

}
