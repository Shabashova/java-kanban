import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class TaskManager {
    private Map<Integer, Task> tasksMap = new HashMap<>();
    private Map<Integer, Epic> epicsMap = new HashMap<>();

    private Map<Integer, Subtask> subtasksMap = new HashMap<>();

    private Map<Integer, List<Subtask>> subtasksToEpicMap = new HashMap<>();
    private int taskIdCounter = 1; // Поле-счетчик для генерации идентификаторов задач

    // Методы для задач
    public List<Task> getAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        allTasks.addAll(tasksMap.values());
        allTasks.addAll(epicsMap.values());
        allTasks.addAll(subtasksMap.values());
        return allTasks;
    }

    public void deleteAllTasks() {
        tasksMap.clear();
        epicsMap.clear();
        subtasksMap.clear();
    }

    public Task getTaskById(int id) {
        Task taskById = tasksMap.get(id);
        if (taskById == null) {
            taskById = epicsMap.get(id);
            if (taskById == null) {
                taskById = subtasksMap.get(id);
            }
        }
        return taskById;
    }


    public void createTask(Epic epic) {
        epic.setId(taskIdCounter); // Присваиваем уникальный идентификатор задачи
        epicsMap.put(epic.getId(), epic);
        List<Subtask> subtasks = new ArrayList<>();
        epic.setSubtasks(subtasks);
        subtasksToEpicMap.put(taskIdCounter, epic.getSubtasks());
        taskIdCounter++;
    }


    public void createTask(Task task) {
        task.setId(taskIdCounter); // Присваиваем уникальный идентификатор задачи
        tasksMap.put(task.getId(), task);
        taskIdCounter++; // Увеличиваем счетчик для следующей задачи
    }

    public void createTask(Subtask subtask) {
        subtask.setId(taskIdCounter); // Присваиваем уникальный идентификатор задачи
        subtasksMap.put(subtask.getId(), subtask);
        taskIdCounter++; // Увеличиваем счетчик для следующей задачи
        subtask.getEpic().getSubtasks().add(subtask); // находим эпик по подздаче, находим список эпика, добавляем подзадачу в список
    }


    public void updateTask(int taskId, Task updatedTask) {
        if (tasksMap.containsKey(taskId)) {
            tasksMap.put(taskId, updatedTask);
        } else if (epicsMap.containsKey(taskId)) {
            epicsMap.put(taskId, (Epic) updatedTask);
        } else if (subtasksMap.containsKey(taskId)) {
            Subtask oldTask = subtasksMap.get(taskId);
            subtasksMap.put(taskId, (Subtask) updatedTask);

            // Обновление подзадачи в списке подзадач соответствующего эпика
            Epic epic = oldTask.getEpic();
            int index = epic.getSubtasks().indexOf(oldTask);
            if (index != -1) {
                epic.getSubtasks().set(index, (Subtask) updatedTask);
            }
        } else {
            System.out.println("Задача не найдена");
        }
    }


    public void deleteTaskById(int id) {
        Task taskById = tasksMap.remove(id);

        if (taskById == null) {
            taskById = epicsMap.remove(id);

        }

        if (taskById == null) {
            taskById = subtasksMap.remove(id);
        }
    }

    public List<Subtask> getSubtasksByEpic(Epic epic) {
        List<Subtask> SubtasksByEpic = epic.getSubtasks();
        for (Subtask subtask : SubtasksByEpic) {
            System.out.println(subtask.getName());
        }
        return SubtasksByEpic;
    }


    // Метод для обновления статусов эпиков
    public void updateEpicStatus(Epic epic) {
        List<Subtask> subtasks = epic.getSubtasks();
        boolean allDone = true;
        boolean allNew = true;
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != TaskStatus.DONE) {
                allDone = false;
                break;
            }
        }
        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            for (Subtask subtask : subtasks) {
                if (subtask.getStatus() != TaskStatus.NEW) {
                    allNew = false;
                    break;
                }
            }
            if (allNew) {
                epic.setStatus(TaskStatus.NEW);
            } else {
                epic.setStatus(TaskStatus.IN_PROGRESS);

            }
        }
    }
}