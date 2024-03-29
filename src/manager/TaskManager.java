package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public abstract interface TaskManager {
    List<Task> getAllTasks();

    List<Subtask> getAllSubtasks();

    List<Epic> getAllEpics();

    void deleteAllTasks();

    void deleteAllSubtasks();

    void deleteAllEpics();

    Task getTaskById(int id);

    Subtask getSubtaskById(int id);

    Epic getEpicById(int id);

    void updateEpicStatus(Epic epic);

    void createEpic(Epic epic);

    void createTask(Task task);

    void createSubtask(Subtask subtask);

    void updateTask(Task task);

    void updateSubtask(Subtask subtask);

    void updateEpic(Epic epic);

    void deleteTaskById(int deleteId);

    void deleteSubtaskById(int id);

    void deleteEpicById(int id);

    List<Integer> getSubtasksByEpic(Epic epic);

    public List<Task> getHistory();
}
