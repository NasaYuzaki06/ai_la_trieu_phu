package src;

import java.util.ArrayList;
import java.util.List;

public class PrizeManager {
    private List<PrizeLevel> prizeStructure;

    public PrizeManager() {
        prizeStructure = new ArrayList<>();
        initializePrizes();
    }
    private void initializePrizes() {
        prizeStructure.add(new PrizeLevel(0, 0L, false));
        prizeStructure.add(new PrizeLevel(1, 200_000L, false));
        prizeStructure.add(new PrizeLevel(2, 400_000L, false));
        prizeStructure.add(new PrizeLevel(3, 600_000L, false));
        prizeStructure.add(new PrizeLevel(4, 1_000_000L, false));
        prizeStructure.add(new PrizeLevel(5, 2_000_000L, true));
        prizeStructure.add(new PrizeLevel(6, 3_000_000L, false));
        prizeStructure.add(new PrizeLevel(7, 6_000_000L, false));
        prizeStructure.add(new PrizeLevel(8, 10_000_000L, false));
        prizeStructure.add(new PrizeLevel(9, 14_000_000L, false));
        prizeStructure.add(new PrizeLevel(10, 22_000_000L, true));
        prizeStructure.add(new PrizeLevel(11, 30_000_000L, false));
        prizeStructure.add(new PrizeLevel(12, 40_000_000L, false));
        prizeStructure.add(new PrizeLevel(13, 60_000_000L, false));
        prizeStructure.add(new PrizeLevel(14, 85_000_000L, false));
        prizeStructure.add(new PrizeLevel(15, 150_000_000L, false));
    }

    public long getPrizeForQuestion(int questionNumber) {
        if (questionNumber == prizeStructure.get(questionNumber).getQuestionNumber()) {
            return prizeStructure.get(questionNumber).getPrizeValue();
        }
        return 0;
    }

    public long getMilestonePrizeOnFailure(int failedQuestionNumber) {
        for (int i = failedQuestionNumber - 1; i >= 0; i--) {
            PrizeLevel currentLevel = prizeStructure.get(i);
            if (currentLevel.isSafeHaven()) {
                return currentLevel.getPrizeValue();
            }
        }
        return 0L;
    }
}