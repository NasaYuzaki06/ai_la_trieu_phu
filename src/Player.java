package src;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private long currentPrize;
    private int currentQuestionNumber;
    private int currentLevel;
    private List<LifeLine> availableLifelines;

    public Player(String name) {
        this.name = name;
        this.currentPrize = 0;
        this.currentLevel = 5;
        this.availableLifelines = new ArrayList<>();
        this.availableLifelines.add(new FiftyFifty());
        this.availableLifelines.add(new AskTheAudience());
        this.availableLifelines.add(new PhoneAFriend());
        this.currentQuestionNumber = 1;
    }

    public String getName() {
        return name;
    }

    public long getCurrentPrize() {
        return currentPrize;
    }

    public List<LifeLine> getAvailableLifelines() {
        return availableLifelines;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentPrize(long currentPrize) {
        this.currentPrize = currentPrize;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public void setCurrentQuestionNumber(int currentQuestionNumber) {
        this.currentQuestionNumber = currentQuestionNumber;
    }


}