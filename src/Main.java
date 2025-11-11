package src;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        Player player = new Player();

        QuestionRepository questionRepository = new QuestionRepository();
        GameUI gameUI = new GameUI(scanner);
        GameEngine gameEngine = new GameEngine(gameUI, player, questionRepository);
        gameEngine.startGame();
    }
}