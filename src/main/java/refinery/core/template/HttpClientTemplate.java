package refinery.core.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

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
	
	
	@Override
	public <T> T request(String uri, String query, Object[] objects, Class<T> clazz) {
		String url = createURL(uri, query, objects);
		
		StringBuilder body = validateResponse(requestHttpClient(url));
		
		Gson gson = new Gson();
		
		return gson.fromJson(body.toString(), clazz);

	}

	private <T> StringBuilder validateResponse(InputStreamReader responseReader) {
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(responseReader);
		boolean flag = false;
		
		String line = null;
		try {
			while((line = br.readLine()) != null) {
				// first line
				if (!flag) {
					line = line.replace("{\"result\" : {", "{");
					flag = true;
				}
				
				if (line.contains("title") || line.contains("serviceDate") || line.contains("serviceTime")) continue;
				
				sb.append(line);
				
			}
			
			// last line
			int lastBraceIdx = sb.lastIndexOf("}");
			sb.deleteCharAt(lastBraceIdx);
			
		} catch (IOException e) {
			throw new HttpResponseFailureException("response io error", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new HttpResponseFailureException("response reader close error", e);
				}
			}

		}
		
		return sb;
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
