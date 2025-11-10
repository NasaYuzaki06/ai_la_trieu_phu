package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GameUI {
    private Scanner scanner;
    private DrawFrame drawFrame;

    public GameUI(Scanner scanner) {
        this.scanner = scanner;
        this.drawFrame = new DrawFrame();
    }

    public Scanner getScanner() {
        return scanner;
    }

    // Kiểm tra dữ liệu người dùng
    public int getIntegerInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int number = scanner.nextInt();
                scanner.nextLine();
                return number;

            } catch (InputMismatchException e) {
                System.out.println("Lỗi: Vui lòng nhập đúng! Hãy thử lại!!!");
                scanner.nextLine();
            }
        }
    }

    public char getCharInput(Scanner scanner, String prompt, Question currentQuestion) {
        List<Character> validOptions = new ArrayList<>();
        for (Answer ans : currentQuestion.getAnswer()) {
            validOptions.add(ans.getOptionIdentifier());
        }
        while (true) {
            System.out.print(prompt);
            String character = scanner.nextLine().toUpperCase();
            if (character.length() == 1 && validOptions.contains(character.charAt(0))) {
                if (character.charAt(0) == 'A' || character.charAt(0) == 'B' || character.charAt(0) == 'C' || character.charAt(0) == 'D') {
                    return character.charAt(0);
                }
            }
            else {
                System.out.println("Vui lòng nhập đúng!");
            }
        }
    }

    // Hiển thị câu hỏi, các câu trả lời, tiền thưởng, độ
    public void displayQuestion(Question questions, PrizeManager prizeManager, Player player) {
        drawFrame.drawBox(questions.getQuestionContent(), 
                questions.getAnswer(),
                prizeManager.getPrizeForQuestion(player.getCurrentQuestionNumber()),
                player.getCurrentQuestionNumber());
    }

    // Hiển thị lời chào và luật chơi
    public void displayWelcomeMessage() {
        drawFrame.drawBox();
    }

    // Hiển thị lựa chọn
    public int displayAndInputForStartGame() {
        String menu = """
                1. Bắt đầu chơi
                2. Thoát game
                """;
        System.out.println(menu);
        int playerInput = getIntegerInput(scanner, "Nhập lựa chọn của bạn: ");
        while (playerInput != 1 && playerInput != 2) {
            playerInput = getIntegerInput(scanner, "Nhập lựa chọn của bạn: ");
        }
        return playerInput;
    }

    public int displayAndInputPlayerChoice() {
        String menu = """
                1. Trả lời câu hỏi
                2. Sử dụng quyền trợ giúp
                3. Bỏ cuộc
                """;
        System.out.println(menu);
        int playerInput = getIntegerInput(scanner, "Nhập lựa chọn của bạn: ");
        while (playerInput != 1 && playerInput != 2 && playerInput != 3) {
            playerInput = getIntegerInput(scanner, "Nhập lựa chọn của bạn: ");
        }
        return playerInput;
    }

    public int displayAndInputLifeLineChoice() {
        String menu = """
                1. 50:50
                2. Hỏi ý kiến khán giả
                3. Gọi điện thoại cho người thân
                4. Thoát
                """;
        System.out.println(menu);
        int playerInput = getIntegerInput(scanner, "Nhập lựa chọn của bạn: ");
        while (playerInput != 1 && playerInput != 2 && playerInput != 3 && playerInput != 4) {
            playerInput = getIntegerInput(scanner, "Nhập lựa chọn của bạn: ");
        }
        return playerInput;
    }

    public int displayAndInputGiveUpChoice() {
        String menu = """
                1. Có
                2. Không
                """;
        System.out.println(menu);
        int playerInput = getIntegerInput(scanner, "Nhập lựa chọn của bạn: ");
        while (playerInput != 1 && playerInput != 2) {
            playerInput = getIntegerInput(scanner, "Nhập lựa chọn của bạn: ");
        }
        return playerInput;
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayMessage(String message, long prize) {
        System.out.println(message + displayPrizeFormat(prize));
    }

    public char displayAndInputAnswerChoice(Question currentQuestion) {
        return getCharInput(scanner, "Nhập đáp án của bạn: ", currentQuestion);
    }

    public void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Không thể xoá màn hình.");
        }
    }

    public String displayPrizeFormat(long prize) {
        return drawFrame.formatPrize(prize);
    }
}

class DrawFrame {
    public final int frameWidth = 100;

    // In khung của câu hỏi
    public void drawBox(String questionContent, List<Answer> answers, long prize, int currentQuestionNumber) {
        String prizeToString = formatPrize(prize);
        printLine('╔', '═', '╗');
        int padding = frameWidth - 2 - prizeToString.length();
        int paddingLeft = padding / 2;
        int paddingRight = padding - paddingLeft;
        String leftPaddingStr = " ".repeat(paddingLeft);
        String rightPaddingStr = " ".repeat(paddingRight);
        System.out.println("║" + leftPaddingStr + prizeToString + rightPaddingStr + "║");
        printLine('╠', '═', '╣');

        List<String> wrappedQuestionLines = wrapText(questionContent, frameWidth - 2);
        for (String line : wrappedQuestionLines) {
            printText(line, currentQuestionNumber);
        }
        printText("");
        for (Answer answer : answers) {
            printText(answer.getOptionIdentifier() + ". " + answer.getAnswerContent());
        }
        printLine('╚', '═', '╝');
    }
    
    public void printLine(char left, char middle, char right) {
        System.out.print(left);
        for (int i = 1; i <= frameWidth - 2; i++) {
            System.out.print(middle);
        }
        System.out.println(right);
    }

    public void printText(String text) {
        System.out.printf("║ %-" + (frameWidth - 4) + "s ║%n" , text);
    }

    public void printText(String text, int currentQuestion) {
        int stringMustMinus = 7;
        if (currentQuestion >= 10) {
            stringMustMinus = 8;
        }
        int padding = frameWidth - text.length() - 4 - stringMustMinus;
        String rightPaddingStr = " ".repeat(padding);
        System.out.printf("║ Câu %d: " + text + rightPaddingStr + " ║%n" , currentQuestion, text);
    }

    // In khung lời chào và luật chơi
    public void drawBox() {
        String welcomeMessage = "CHÀO MỪNG BẠN ĐẾN VỚI AI LÀ TRIỆU PHÚ💸";
        String gameRule = """
                Luật chơi như sau:
                    1. Mục tiêu:
                        - Trả lời chính xác 15 câu hỏi trắc nghiệm để giành chiến thắng.
                    2. Cách chơi:
                        - Bạn sẽ lần lượt trả lời 15 câu hỏi với độ khó tăng dần.
                        - Mỗi câu hỏi có 4 phương án (A, B, C, D) và chỉ có 1 đáp án đúng.
                        - Bạn sẽ có 3 mốc quan trọng cần vượt qua là Câu 5, Câu 10, và Câu 15. Nếu trả lời sai,
                            bạn sẽ ra về với tiền thưởng của mốc gần nhất.
                        - Bạn có thể dừng cuộc chơi bất cứ lúc nào để bảo toàn số tiền thưởng hiện tại.
                    3. Quyền trợ giúp:
                        - Bạn có 4 quyền trợ giúp, mỗi quyền chỉ được dùng MỘT LẦN:
                            + 50:50: Máy tính sẽ loại bỏ 2 phương án sai.
                            + Gọi điện thoại cho người thân: Hỏi ý kiến một người bạn.
                            + Hỏi ý kiến khán giả: Xem biểu đồ bình chọn của khán giả.
                Chúc bạn may mắn!
                """;
        printLine('╔', '═', '╗');
        int padding = frameWidth - 2 - welcomeMessage.length() + 1;
        int paddingLeft = padding / 2;
        int paddingRight = padding - paddingLeft;
        String leftPaddingStr = " ".repeat(paddingLeft);
        String rightPaddingStr = " ".repeat(paddingRight);
        System.out.println("║" + leftPaddingStr + welcomeMessage + rightPaddingStr + "║");
        printLine('╠', '═', '╣');
        String[] ruleLines = gameRule.split("\n");

        for (String line : ruleLines) {
            printText(line);
        }
        printText("");
        printLine('╚', '═', '╝');
    }

    public List<String> wrapText(String text, int lineLength) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();
        for (String word : words) {
            if (currentLine.length() + word.length() + 1 <= lineLength) {
                if (!currentLine.isEmpty()) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            } else {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            }
        }
        lines.add(currentLine.toString());
        return lines;
    }

    public String formatPrize(long prize) {
        String prizeAsString = String.valueOf(prize);
        StringBuilder prizeFormated = new StringBuilder();
        int digitCounter = 0;
        for (int i = prizeAsString.length() - 1; i >= 0; i--) {
            char digit = prizeAsString.charAt(i);
            prizeFormated.insert(0, digit);
            digitCounter++;
            if (digitCounter % 3 == 0 && i > 0) {
                prizeFormated.insert(0, '.');
            }
        }
        return prizeFormated.toString();
    }

}