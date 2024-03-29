package manager;

import tasks.Task;

import java.util.List;

public abstract interface HistoryManager {
    void addViewedTasks(Task task);

    List<Task> getHistory();
}
