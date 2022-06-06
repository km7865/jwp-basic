package next.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObject {
    private static ObjectMapper mapper = new ObjectMapper();
    private String value;

    public JsonObject(Object obj) throws JsonProcessingException {

    }

    public String getValue() {
        return value;
    }
}
