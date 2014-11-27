package core.template.fileio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.naver.model.Response;
import core.naver.model.ResponseArticle;
import core.template.Template;

public class FileIOTemplate implements Template {
	
	private static final Logger log = LoggerFactory.getLogger(FileIOTemplate.class);

	public <T> T get(String host, String uri, Class<T> clazz) {
		
		BufferedReader br = null;
		File journalDir = new File(host);
		int sequence = 0;
		Response response = null;
		List<ResponseArticle> responseArticles;
		Random random = new Random();
		
		if (journalDir.isDirectory()) {
			File[] fileArr = journalDir.listFiles();
			response = new Response();
			responseArticles = new ArrayList<ResponseArticle>();
			
			for (File file : fileArr) {
				String name = file.getName();
				sequence = 0;
				StringBuilder sb = new StringBuilder();
				ResponseArticle ra = new ResponseArticle();
				int hits = random.nextInt(100000);
				double ratio = random.nextInt(10)/10.0;
				int completed = (int) (hits * ratio);
				
				if (!name.startsWith(".")) {
					try {
						br = new BufferedReader(new FileReader(file));
						String line = null;
						char newline = 0;
						
						try {
							while((line = br.readLine()) != null) {
								line = line.trim();
								if (sequence < 6) {
									if (sequence == 1) {
										// do nothing
									} else {
										switch (sequence) {
											case 0:
												ra.setHotissue(line);
												break;
											case 2:
												ra.setTitle(line);
												break;
											case 3:
												ra.setDate(line + ":00");
												break;
											case 4:
												ra.setJournal(line);
												break;
											case 5:
												String[] splits = line.split("-");
												ra.setSection(splits[1].trim());
												break;
										}
									}
									
									sequence++;
								} else {
									sb.append(line);
								}
								
							}
							
						} catch (IOException e) {
							log.debug("io error");
						}
						ra.setContent(sb.toString());
						ra.setHits(hits);
						ra.setCompletedReadingCount(completed);
						
						responseArticles.add(ra);
						
						
					} catch (FileNotFoundException e) {
						log.debug("[" + name + "] not found");
					}
					
					response.setResponseArticles(responseArticles);
				}
				
				
			}
		}

		
		return (T) response;
	}

}