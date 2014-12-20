package core.template;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


public interface GsonMapper<T> {

	T map(Gson gson, JsonReader gsonReader) throws IOException;
}
