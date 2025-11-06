package src;

public interface LifeLine {
    public void use(Question question, GameUI gameUI, Player player);
    boolean isUsed();
    String getName();
}