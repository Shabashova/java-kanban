package manager;

public class Managers {
    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }
    public static HistoryManager HistoryManagerGetDefault(){
        return new InMemoryHistoryManager();
    }
}
