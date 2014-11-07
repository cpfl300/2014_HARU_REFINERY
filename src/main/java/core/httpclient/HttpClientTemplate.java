package core.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class HttpClientTemplate {
	
	public <T> T get(String host, String uri, Class<T> clazz) throws HttpRequestFailureException, HttpResponseFailureException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(host + uri);
	 
		// add request header
		//request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = null;
		int statusCode = 0;
		StringBuffer result = null;
		T converted = null;
		try {
			response = client.execute(request);
			statusCode = response.getStatusLine().getStatusCode();
			
			if (statusCode >= 400) {
				throw new HttpRequestFailureException("[" + statusCode + "] " + uri);
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			ObjectMapper mapper = new ObjectMapper();			
			converted = mapper.readValue(result.toString(), clazz);
			
		} catch (IOException e) {
			throw new HttpResponseFailureException("IO error", e);
		}
		
		return converted;
	}
}
