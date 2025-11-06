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
    private QuestionRepository questionRepository;

    public GameEngine(GameUI gameUI, Player player, QuestionRepository questionRepository) {
        this.gameUI = gameUI;
        this.player = player;
        this.questionRepository = questionRepository;
        this.prizeManager = new PrizeManager();
    }

    public void startGame() throws IOException, InterruptedException {
        gameUI.displayWelcomeMessage();
        this.gameStatus = GameStatus.PLAYING;
        int currentQuestionNumber = player.getCurrentQuestionNumber();
        switch (gameUI.displayAndInputForStartGame()) {
            case 1:
                questionRepository.loadQuestion();
                int checkAppearAskTheAudience = 0; //
                int checkAppearPhoneAFriend = 0; //
                while (this.gameStatus == GameStatus.PLAYING) {
                    boolean answered = false;
                    Question currentQuestion = questionRepository.getQuestionByLevel(player.getCurrentLevel());
                    while (!answered) {
                        gameUI.clearScreen();// Xóa màn hình console
                        if (currentQuestion != null) { // In câu hỏi và câu trả lời
                            gameUI.displayQuestion(currentQuestion, prizeManager, player);
                        }
                        if (checkAppearAskTheAudience == 1) {
                            player.getAvailableLifelines().get(1).use(currentQuestion, gameUI, player);
                        }
                        if (checkAppearPhoneAFriend == 1) {
                            player.getAvailableLifelines().get(2).use(currentQuestion, gameUI, player);
                        }
                        checkAppearPhoneAFriend = 0;
                        checkAppearAskTheAudience = 0;
                        switch (gameUI.displayAndInputPlayerChoice()) {
                            case 1:
                                char answerChoice = gameUI.displayAndInputAnswerChoice(currentQuestion);
                                assert currentQuestion != null;
                                if (checkAnswer(answerChoice, currentQuestion)) {
                                    player.setCurrentPrize(prizeManager.getPrizeForQuestion(currentQuestionNumber));
                                    currentQuestionNumber++;
                                    player.setCurrentQuestionNumber(currentQuestionNumber);
                                    if (currentQuestionNumber > 10) {
                                        player.setCurrentLevel(15);
                                    } else if (currentQuestionNumber > 5) {
                                        player.setCurrentLevel(10);
                                    }
                                    if (currentQuestionNumber > 15) {
                                        gameUI.displayMessage("Chúc mừng bạn đã hoàn thành xuất sắc cuộc chơi 🤩 - Tiền thưởng của bạn là: ", player.getCurrentPrize());
                                        this.gameStatus = GameStatus.WINNER;
                                        answered = true;
                                        break;
                                    }
                                    gameUI.displayMessage("Chúc mừng bạn đã trả lời chính xác 👏 - Tiền thưởng hiện tại của bạn là: ", player.getCurrentPrize());
                                } else {
                                    gameUI.displayMessage("Câu trả lời của bạn không chính xác 😆 - Tiền thưởng của bạn là: ", prizeManager.getMilestonePrizeOnFailure(currentQuestionNumber));
                                    this.gameStatus = GameStatus.GAME_OVER;
                                }
                                answered = true;
                                break;
                            case 2:
                                switch (gameUI.displayAndInputLifeLineChoice()) {
                                    case 1:// 50:50
                                        if (!player.getAvailableLifelines().getFirst().isUsed()) {
                                            player.getAvailableLifelines().getFirst().use(currentQuestion, gameUI, player);
                                        } else {
                                            System.out.println("Bạn đã sử dụng quyền trợ giúp này rồi!!!🥲");
                                        }
                                        break;
                                    case 2:// Ask the audience
                                        if (!player.getAvailableLifelines().get(1).isUsed()) {
                                            checkAppearAskTheAudience = 1;
                                        } else {
                                            System.out.println("Bạn đã sử dụng quyền trợ giúp này rồi!!!🥲");
                                        }
                                        break;
                                    case 3:// Phone a friend
                                        if (!player.getAvailableLifelines().get(2).isUsed()) {
                                            checkAppearPhoneAFriend = 1;
                                        } else {
                                            System.out.println("Bạn đã sử dụng quyền trợ giúp này rồi!!!🥲");
                                        }
                                        break;
                                }
                                break;
                            case 3:
                                System.out.println("Bạn có chắc chắn muốn bỏ cuộc và nhận số tiền thưởng - " + prizeManager.getPrizeForQuestion(player.getCurrentQuestionNumber() - 1) + "🤔");
                                switch (gameUI.displayAndInputGiveUpChoice()) {
                                    case 1:
                                        System.out.println("Tiền thưởng của bạn - " + prizeManager.getPrizeForQuestion(player.getCurrentQuestionNumber() - 1));
                                        System.out.println("Hẹn gặp lại bạn lần sau 😊");
                                        this.gameStatus = GameStatus.WALK_AWAY;
                                        answered = true;
                                        break;
                                    case 2:
                                        break;
                                }
                                break;
                        }
                    }

                }
                break;
            case 2:
                break;
        }
    }

    public void gameLoop() {

    }

    public boolean checkAnswer(char answerChoice, Question currentQuestion) {
        for (Answer answer : currentQuestion.getAnswer()) {
            if (answer.getOptionIdentifier() == answerChoice && answer.isCorrectAnswer()) {
                return true;
            }
        }
        return false;
    }

}