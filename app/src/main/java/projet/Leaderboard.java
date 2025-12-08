package projet;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Leaderboard {

    public static void save() {

    }

    public static Map<String, Integer> load() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Integer>> typeRef = new TypeReference<Map<String, Integer>>() {
        };
        String json = "{\"Alice\": 1500, \"Bob\": 1200}"; // Example JSON string
        Map<String, Integer> leaderboard = null;

        try {
            leaderboard = objectMapper.readValue(json, typeRef);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return leaderboard;
    }
}
