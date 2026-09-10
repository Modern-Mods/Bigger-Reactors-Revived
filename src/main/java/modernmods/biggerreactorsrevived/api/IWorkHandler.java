package modernmods.biggerreactorsrevived.api;

public interface IWorkHandler {
    
    int getProgress();
    
    int getGoal();
    
    boolean isFinished();
    
    void increment(int amount);
    
    void decrement(int amount);
    
    void clear();
}
