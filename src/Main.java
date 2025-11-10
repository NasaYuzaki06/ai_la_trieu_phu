package src;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<Question> questionList1 = new ArrayList<>();
        List<Question> questionList2 = new ArrayList<>();
        List<Question> questionList3 = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        Player player = new Player();

        QuestionRepository questionRepository = new QuestionRepository(questionList1, questionList2, questionList3);
        GameUI gameUI = new GameUI(scanner);
        GameEngine gameEngine = new GameEngine(gameUI, player, questionRepository);
        gameEngine.startGame();
//        Random random = new Random();
//        for (int i = 1; i <= 10; i++) {
//            double doubleRandomNumber = random.nextDouble(80, 100);
//            BigDecimal bd = BigDecimal.valueOf(doubleRandomNumber);
//            BigDecimal roundNumber = bd.setScale(1, RoundingMode.HALF_UP);
//            System.out.println(roundNumber);
//        }
    }
}