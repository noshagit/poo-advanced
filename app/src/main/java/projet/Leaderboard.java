package projet;

import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Leaderboard {
    private Map<String, Integer> board = new HashMap<>();
    private final String filePath = "leaderboard.json";

    public Map<String, Integer> getScores() {
        return this.board;
    }

    public void setScore(String player, int score) {
        this.board.put(player, score);
        
        // Keep only top 10 scores
        if (this.board.size() > 10) {
            String lowestPlayer = this.board.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
            if (lowestPlayer != null) {
                this.board.remove(lowestPlayer);
            }
        }
        this.save();
    }

    public void save() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(this.board, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        File file = new File(filePath);
        if (!file.exists()) {
            // Create empty file if it doesn't exist
            save();
        }

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            Type mapType = new TypeToken<Map<String, Integer>>() {
            }.getType();
            Map<String, Integer> map = gson.fromJson(reader, mapType);
            this.board.clear();
            this.board.putAll(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
