package hexlet.code;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static hexlet.code.FileReader.readStringFromFile;
import static hexlet.code.JsonProcessor.getMapFromJsonString;

public class Differ {
    private static Map<String, Object> compareData(String filePath1, String filePath2) throws IOException {
        Map<String, Object> resultMap = new TreeMap<>();

        String rawText1 = readStringFromFile(filePath1);
        String rawText2 = readStringFromFile(filePath2);

        Map<String, Object> dataToCompare1 = getMapFromJsonString(rawText1);
        Map<String, Object> dataToCompare2 = getMapFromJsonString(rawText2);

        Set<String> keys = Stream.concat(dataToCompare1.keySet().stream(), dataToCompare2.keySet().stream())
                .sorted().collect(Collectors.toCollection(TreeSet::new));

        for (String key : keys) {
            if((dataToCompare1.containsKey(key) && dataToCompare2.containsKey(key))
                    && dataToCompare1.get(key).equals(dataToCompare2.get(key))) {
                resultMap.put(key, dataToCompare1.get(key));
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

    }

    public static String generate(String filePath1, String filePath2) throws IOException {
        Map<String, Object> mappedDiff = compareData(filePath1, filePath2);
        
        return null;
    }
}
