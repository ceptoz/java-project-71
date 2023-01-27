package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Differ {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static String readStringFromFile(String filePath) throws IOException {
        Path fullPath = Path.of(filePath).toAbsolutePath().normalize();
        return Files.readString(fullPath);
    }

    private static Map<String, Object> getMapFromJsonString(String rawText) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(rawText, new TypeReference<>() { });
    }

    private static Map<String, Object> compareData(String filePath1, String filePath2) throws IOException {
        Map<String, Object> resultMap = new LinkedHashMap<>();

        String rawText1 = readStringFromFile(filePath1);
        String rawText2 = readStringFromFile(filePath2);

        Map<String, Object> dataToCompare1 = getMapFromJsonString(rawText1);
        Map<String, Object> dataToCompare2 = getMapFromJsonString(rawText2);

        TreeSet<String> keys = Stream.concat(dataToCompare1.keySet().stream(), dataToCompare2.keySet().stream())
                .sorted().collect(Collectors.toCollection(TreeSet::new));

        for (String key : keys) {
            if ((dataToCompare1.containsKey(key) && dataToCompare2.containsKey(key))
                    && dataToCompare1.get(key).equals(dataToCompare2.get(key))) {
                resultMap.put("  " + key, dataToCompare1.get(key));
            } else if ((dataToCompare1.containsKey(key) && dataToCompare2.containsKey(key))
                    && !dataToCompare1.get(key).equals(dataToCompare2.get(key))) {
                resultMap.put("- " + key, dataToCompare1.get(key));
                resultMap.put("+ " + key, dataToCompare2.get(key));
            } else if (dataToCompare1.containsKey(key) && !dataToCompare2.containsKey(key)) {
                resultMap.put("- " + key, dataToCompare1.get(key));
            } else if (!dataToCompare1.containsKey(key) && dataToCompare2.containsKey(key)) {
                resultMap.put("+ " + key, dataToCompare2.get(key));
            }
        }
        return resultMap;
    }

    private static String toString(Map<String, Object> mappedData) {
        StringBuilder stringBuilder = new StringBuilder("{\n");
        if (mappedData.isEmpty()) {
            return "{}";
        }
        for (Map.Entry<String, Object> entry : mappedData.entrySet()) {
            stringBuilder.append(String.format("  %s: %s\n", entry.getKey(), entry.getValue().toString()));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static String generate(String filePath1, String filePath2) throws IOException {
        Map<String, Object> mappedDiff = compareData(filePath1, filePath2);
        String result = toString(mappedDiff);
        System.out.println(result);
        return result;
    }
}
