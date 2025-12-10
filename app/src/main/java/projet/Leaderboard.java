package projet;

import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Leaderboard {
    private Map<String, Integer> board = new HashMap<>();
    private final String filePath = "leaderboard.json";

    public Map<String, Integer> getScores() {
        return this.board;
    }

    public void setScore(String player, int score) {
        this.board.put(player, score);
        
        // keep only top 10 scores
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
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("{\n");
            int count = 0;
            int size = this.board.size();
            for (Map.Entry<String, Integer> entry : this.board.entrySet()) {
                writer.write("  \"" + entry.getKey() + "\": " + entry.getValue());
                if (++count < size) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        File file = new File(filePath);
        if (!file.exists()) {
            // create empty file if it doesn't exist
            save();
            return;
        }

        this.board.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.equals("{") || line.equals("}") || line.isEmpty()) {
                    continue;
                }
                
                if (line.endsWith(",")) {
                    line = line.substring(0, line.length() - 1);
                }
                
                // split entry on ":"
                int colonIndex = line.indexOf("\":");
                if (colonIndex > 0) {
                    String player = line.substring(1, colonIndex);
                    
                    String scoreStr = line.substring(colonIndex + 2).trim();
                    int score = Integer.parseInt(scoreStr);
                    this.board.put(player, score);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
