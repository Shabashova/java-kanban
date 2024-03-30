package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
public class InMemoryHistoryManager implements HistoryManager {
 private List<Task> viewedTasks = new LinkedList<>();
    @Override
    public void addViewedTasks(Task task) {
        viewedTasks.add(task);
        if (viewedTasks.size() > 10) {
            viewedTasks.removeFirst();
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> historyCopy = new ArrayList<>(viewedTasks);
        return historyCopy;
    }


}
