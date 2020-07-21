package testUtilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TestUtilities {
    public static String toJson(final Object object) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter=objectMapper.writerWithDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }
}
