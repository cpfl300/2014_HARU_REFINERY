package refinery.module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Pump {
	
	private static final Logger log = LoggerFactory.getLogger(Pump.class);

	public int get(String url) throws HttpRequestErrorException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
	 
		// add request header
		//request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = null;
		int statusCode = 0;
		try {
			response = client.execute(request);
			statusCode = response.getStatusLine().getStatusCode();
			
			if (statusCode >= 400) {
				throw new HttpRequestErrorException("[" + statusCode + "] " + url);
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		 
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			log.debug(result.toString());
			
		} catch (IOException e) {
			log.debug(e.getMessage(), e);

		}
		
		return statusCode;
	}
}
