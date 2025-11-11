package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

enum GameStatus {
    PLAYING,
    WALK_AWAY,
    GAME_OVER,
    WINNER;
}

public class GameEngine {
    private GameStatus gameStatus;
    private GameUI gameUI;
    private Player player;
    private PrizeManager prizeManager;
    private PlayerManager playerManager;
    private QuestionRepository questionRepository;

    public GameEngine(GameUI gameUI, Player player, QuestionRepository questionRepository) {
        this.gameUI = gameUI;
        this.player = player;
        this.questionRepository = questionRepository;
        this.prizeManager = new PrizeManager();
        this.playerManager = new PlayerManager();
    }

    public void startGame() {
        gameUI.displayWelcomeMessage();
        this.gameStatus = GameStatus.PLAYING;
        switch (gameUI.displayAndInputForStartGame()) {
            case 1:
                inputPlayerName();
                questionRepository.loadQuestion();
                gameLoop();
                break;
            case 2:
                playerManager.loadPlayerScore();
                gameUI.clearScreen();
                gameUI.displayRanking(playerManager);
            case 3:
                break;
        }
    }

    public void inputPlayerName() {
        System.out.print("Nhập tên của bạn: ");
        String playerName = gameUI.getScanner().nextLine();
        player.setName(playerName);
    }

    private void gameLoop() {
        while (this.gameStatus == GameStatus.PLAYING) {
            Question currentQuestion = questionRepository.getQuestionByLevel(player.getCurrentLevel());
            boolean answered = false;
            while (!answered) {
//                gameUI.clearScreen();
                gameUI.displayQuestion(currentQuestion, prizeManager, player);
                int playerChoice = gameUI.displayAndInputPlayerChoice();
                answered = handlePlayerChoice(playerChoice, currentQuestion);
            }
        }
    }

    private boolean handlePlayerChoice(int playerChoice, Question currentQuestion) {
        return switch (playerChoice) {
            case 1 -> handleAnswer(currentQuestion);
            case 2 -> {
                handleLifeLine(currentQuestion);
                yield false;
            }
            case 3 -> handleWalkAway();
            default -> false;
        };
    }

    private boolean handleAnswer(Question currentQuestion) {
        char answerChoice = gameUI.displayAndInputAnswerChoice(currentQuestion);
        if (checkAnswer(answerChoice, currentQuestion)) {
            updatePrizeAndLevel();
        } else {
            long prize = prizeManager.getMilestonePrizeOnFailure(player.getCurrentQuestionNumber());
            gameUI.displayMessage(
                    "Câu trả lời của bạn không chính xác 😆 - Tiền thưởng của bạn là: ", prize);
            playerManager.savePlayerScore(player.getName(), player.getCurrentQuestionNumber() - 1, prize);
            this.gameStatus = GameStatus.GAME_OVER;
        }
        return true;
    }

    private boolean handleWalkAway() {
        long prize = prizeManager.getPrizeForQuestion(player.getCurrentQuestionNumber() - 1);
        gameUI.displayMessage("Bạn có chắc chắn muốn bỏ cuộc? Tiền thưởng của bạn nhận được là: ", prize);
        int choice = gameUI.displayAndInputGiveUpChoice();
        if (choice == 1) {
            gameUI.displayMessage("Tiền thưởng cuối cùng của bạn là: ", prize);
            playerManager.savePlayerScore(player.getName(), player.getCurrentQuestionNumber() - 1, player.getCurrentPrize());
            this.gameStatus = GameStatus.WALK_AWAY;
            return true;
        }
        return false;
    }

    private void handleLifeLine(Question currentQuestion) {
        int choice = gameUI.displayAndInputLifeLineChoice();
        switch (choice) {
            case 1:
                useFiftyFifty(currentQuestion);
                break;
            case 2:
                useAskTheAudience(currentQuestion);
                break;
            case 3:
                usePhoneAFriend(currentQuestion);
                break;
            case 4:
                break;
        }
    }

    private void useFiftyFifty(Question currentQuestion) {
        if (!player.getAvailableLifelines().getFirst().isUsed()) {
            player.getAvailableLifelines().getFirst().use(currentQuestion, gameUI, player);
        } else {
            System.out.println("Bạn đã sử dụng quyền trợ giúp này rồi!");
        }
    }

    private void useAskTheAudience(Question currentQuestion) {
        if (!player.getAvailableLifelines().get(1).isUsed()) {
            player.getAvailableLifelines().get(1).use(currentQuestion, gameUI, player);
        } else {
            System.out.println("Bạn đã sử dụng quyền trợ giúp này rồi!");
        }
    }

    private void usePhoneAFriend(Question currentQuestion) {
        if (!player.getAvailableLifelines().get(2).isUsed()) {
            player.getAvailableLifelines().get(2).use(currentQuestion, gameUI, player);
        } else {
            System.out.println("Bạn đã sử dụng quyền trợ giúp này rồi!");
        }
    }

    private void updatePrizeAndLevel() {
        int currentQuestionNumber = player.getCurrentQuestionNumber();
        player.setCurrentPrize(prizeManager.getPrizeForQuestion(currentQuestionNumber));
        currentQuestionNumber++;
        player.setCurrentQuestionNumber(currentQuestionNumber);

        if (currentQuestionNumber > 10) {
            player.setCurrentLevel(15);
        } else if (currentQuestionNumber > 5) {
            player.setCurrentLevel(10);
        }
        if (currentQuestionNumber > 15) {
            this.gameStatus = GameStatus.WINNER;
            playerManager.savePlayerScore(player.getName(), player.getCurrentQuestionNumber() - 1, player.getCurrentPrize());
            gameUI.displayMessage(
                    "Bạn đã chiến thắng toàn bộ game 🤩 - Tiền thưởng: ",
                    player.getCurrentPrize());
            return;
        }
        gameUI.displayMessage(
                "Chúc mừng bạn đã trả lời chính xác 👏 - Tiền thưởng hiện tại của bạn là: ",
                player.getCurrentPrize()
        );
    }

    private boolean checkAnswer(char answerChoice, Question currentQuestion) {
        for (Answer answer : currentQuestion.getAnswer()) {
            if (answer.getOptionIdentifier() == answerChoice && answer.isCorrectAnswer()) {
                return true;
            }
        }
        return false;
    }
}