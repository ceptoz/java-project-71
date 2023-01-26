package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonProcessor {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Map<String, Object> getMapFromJsonString(String rawText) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(rawText, new TypeReference<>() {});
    }
}
