package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionRepository {
    // Thuộc tính
    private List<Question> questionsWithDifficultyLevel5;
    private List<Question> questionsWithDifficultyLevel10;
    private List<Question> questionsWithDifficultyLevel15;

//    Hàm khởi tạo
    public QuestionRepository(List<Question> questionsWithDifficultyLevel5,
                              List<Question> questionsWithDifficultyLevel10,
                              List<Question> questionsWithDifficultyLevel15) {
        this.questionsWithDifficultyLevel5 = questionsWithDifficultyLevel5;
        this.questionsWithDifficultyLevel10 = questionsWithDifficultyLevel10;
        this.questionsWithDifficultyLevel15 = questionsWithDifficultyLevel15;
    }

    // Phương thức
    public void loadQuestion() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/questions.txt"))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {

                lines.add(currentLine);
            }
        } catch (IOException ex) {
            System.out.println("Lỗi khi đọc file: " + ex.getMessage());
            return;
        }
        Question currentQuestion;
        for (String line : lines) {
            String[] parts = line.split("\\|");
            String questionContent = parts[0];
            int difficultyLevel = Integer.parseInt(parts[parts.length - 1]);
            List<Answer> options = getAnswers(parts);
            currentQuestion = new Question(questionContent, options, difficultyLevel);
            if (difficultyLevel == 5) {
                questionsWithDifficultyLevel5.add(currentQuestion);
            } else if (difficultyLevel == 10) {
                questionsWithDifficultyLevel10.add(currentQuestion);
            } else {
                questionsWithDifficultyLevel15.add(currentQuestion);
            }
        }
    }

    private static List<Answer> getAnswers(String[] parts) {
        List<Answer> options = new ArrayList<>();
        char currentOptionIdentifier = 'A';
        for (int i = 1; i < parts.length - 1; i++) {
            String rawOptionContent = parts[i];
            boolean isCorrect = false;
            String cleanOptionContent;
            if (rawOptionContent.startsWith("*")) {
                isCorrect = true;
                cleanOptionContent = rawOptionContent.substring(1);
            } else {
                cleanOptionContent = rawOptionContent;
            }
            Answer answer = new Answer(cleanOptionContent, isCorrect, currentOptionIdentifier);
            options.add(answer);
            currentOptionIdentifier++;
        }
        return options;
    }

    public Question getQuestionByLevel(int level) {
        Question currentQuestion;
        Random random = new Random();
        if (level == 5) {
            int randomIndexQuestion = random.nextInt(questionsWithDifficultyLevel5.size());
            currentQuestion = questionsWithDifficultyLevel5.get(randomIndexQuestion);
            questionsWithDifficultyLevel5.remove(currentQuestion);
            return currentQuestion;
        } else if (level == 10) {
            int randomIndexQuestion = random.nextInt(questionsWithDifficultyLevel10.size());
            currentQuestion = questionsWithDifficultyLevel10.get(randomIndexQuestion);
            questionsWithDifficultyLevel10.remove(currentQuestion);
            return currentQuestion;
        } else {
            int randomIndexQuestion = random.nextInt(questionsWithDifficultyLevel15.size());
            currentQuestion = questionsWithDifficultyLevel15.get(randomIndexQuestion);
            questionsWithDifficultyLevel15.remove(currentQuestion);
            return currentQuestion;
        }
    }
}