package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
 private List<Task> viewedTasks = new ArrayList<>();
    @Override
    public void addViewedTasks(Task task) {
        viewedTasks.add(task);
        if (viewedTasks.size() > 10) {
            viewedTasks.removeFirst();
        }
    }

    @Override
    public List<Task> getHistory() {
        return viewedTasks;
    }
}
