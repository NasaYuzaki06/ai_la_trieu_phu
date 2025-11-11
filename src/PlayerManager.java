package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlayerManager {
    private final String SCORE_FILE_PATH = "data/scores.txt";
    private List<Player> playerList = new ArrayList<>();

    public void savePlayerScore(String playerName, int questionsAnswered, long prizeMoney) {
        String scoreData = playerName + "," + questionsAnswered + "," + prizeMoney;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE_PATH, true))) {
            writer.write(scoreData);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Lỗi: Không thể lưu điểm vào file " + SCORE_FILE_PATH);
            e.printStackTrace();
        }
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void loadPlayerScore() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/scores.txt"))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {

                lines.add(currentLine);
            }
        } catch (IOException ex) {
            System.out.println("Lỗi khi đọc file: " + ex.getMessage());
            return;
        }
        Player currentPlayer;
        for (String line : lines) {
            String[] parts = line.split(",");
            String playerName = parts[0];
            int currentQuestionNumber = Integer.parseInt(parts[1]);
            long prize = Long.parseLong(parts[parts.length - 1]);
            currentPlayer = new Player(playerName, currentQuestionNumber, prize);
            playerList.add(currentPlayer);
        }
        playerList.sort(
                Comparator.comparingLong(Player::getCurrentPrize).reversed()
        );
    }
}