package core.template;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import core.template.GsonMapper;

public class HttpClientTemplate extends HttpTemplateImpl {
	
	public HttpClientTemplate(String host, String context) {
		super(host, context);
	}

	@Override
	public <T> T request(String uri, String query, Object[] objects, GsonMapper<T> gsonMapper) {
		String url = createURL(uri, query, objects);

		return gsonReader(requestHttpClient(url), gsonMapper);
	}
	
	@Override
	public <T> T request(String uri, GsonMapper<T> gsonMapper) {
		String url = createURL(uri);
		
		return gsonReader(requestHttpClient(url), gsonMapper);
	}


	private <T> T gsonReader(InputStreamReader responseReader, GsonMapper<T> gsonMapper) {
		T mappedObject = null;
		JsonReader gsonReader = null;
		Gson gson = new Gson();
		
		try {
			gsonReader = new JsonReader(responseReader);
			mappedObject = gsonMapper.map(gson, gsonReader);
			
		} catch (IOException e) {
			throw new HttpResponseFailureException("response io error", e);
			
		} finally {
			if (responseReader != null) {
				try {
					responseReader.close();
				} catch (IOException e) {
					throw new HttpResponseFailureException("response reader close error", e);
				}
			}
			
			if(gsonReader != null) {
				try {
					gsonReader.close();
				} catch (IOException e) {
					throw new HttpResponseFailureException("gson reader close error", e);
				}
			}
		}

		return mappedObject;
	}

	private InputStreamReader requestHttpClient(String url) {
		// HttpSource
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		
		HttpResponse response = null;
		InputStreamReader isr = null;
		int statusCode = 0;
		
		// requeset
		try {
			response = client.execute(request);
		} catch (IOException e) {
			throw new HttpRequestFailureException("request denied", e);
		}
		
		// response
		statusCode = response.getStatusLine().getStatusCode();
		if (statusCode >= 500) {
			throw new HttpResponseFailureException("[" + statusCode + "] " + url);
		}
		
		// response io
		try {
			isr = new InputStreamReader(response.getEntity().getContent());
			
		} catch (IOException e) {
			throw new HttpResponseFailureException("response io error", e);
			
		}

		return isr;
	}

	

}
