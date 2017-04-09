package lostandfound.util;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class IntToStringSerializer extends JsonSerializer<Integer> {
	
	@Override
	public void serialize( Integer integer, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		generator.writeObject( integer.toString() );
	}
}
