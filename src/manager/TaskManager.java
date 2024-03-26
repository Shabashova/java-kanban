package manager;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import tasks.Task;
import tasks.Subtask;
import tasks.Epic;
import tasks.TaskStatus;

public class TaskManager {
    private Map<Integer, Task> tasksMap = new HashMap<>();
    private Map<Integer, Epic> epicsMap = new HashMap<>();

    private Map<Integer, Subtask> subtasksMap = new HashMap<>();


    private int taskIdCounter = 1; // Поле-счетчик для генерации идентификаторов задач


    public List<Task> getAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        allTasks.addAll(tasksMap.values());
        return allTasks;
    }

    public List<Subtask> getAllSubtasks() {
        List<Subtask> allSubtasks = new ArrayList<>();
        allSubtasks.addAll(subtasksMap.values());
        return allSubtasks;
    }

    public List<Epic> getAllEpics() {
        List<Epic> allEpics = new ArrayList<>();
        allEpics.addAll(epicsMap.values());
        return allEpics;
    }


    public void deleteAllTasks() {
        tasksMap.clear();
    }

    public void deleteAllSubasks() {
        subtasksMap.clear();
    }

    public void deleteAllEpics() {
        epicsMap.clear();
    }


    public Task getTaskById(int id) {
        Task taskById = tasksMap.get(id);
        return taskById;
    }

    public Subtask getSubtaskById(int id) {
        Subtask subtaskById = subtasksMap.get(id);
        return subtaskById;
    }

    public Epic getEpicById(int id) {
        Epic epicById = epicsMap.get(id);
        return epicById;
    }

    private void updateEpicStatus(Epic epic) {
        List<Integer> subtasks = epic.getSubtasks();
        boolean allDone = true;
        boolean allNew = true;
        for (Integer subtask : subtasks) {
            if (getSubtaskById(subtask).getStatus() != TaskStatus.DONE) {
                allDone = false;
                break;
            }
        }
        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            for (Integer subtask : subtasks) {
                if (getSubtaskById(subtask).getStatus() != TaskStatus.NEW) {
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

    public void createEpic(Epic epic) {
        epic.setId(taskIdCounter); // Присваиваем уникальный идентификатор задачи
        epicsMap.put(epic.getId(), epic);
        List<Integer> subtasks = new ArrayList<>();
        epic.setSubtasks(subtasks);
        taskIdCounter++;
    }


    public void createTask(Task task) {
        task.setId(taskIdCounter); // Присваиваем уникальный идентификатор задачи
        tasksMap.put(task.getId(), task);
        taskIdCounter++; // Увеличиваем счетчик для следующей задачи
    }

    public void createSubtask(Subtask subtask) {
        subtask.setId(taskIdCounter); // Присваиваем уникальный идентификатор задачи
        subtasksMap.put(subtask.getId(), subtask);

        getEpicById(subtask.getEpic()).getSubtasks().add(taskIdCounter);// находим эпик по подздаче, находим список эпика, добавляем подзадачу в список
        updateEpicStatus(getEpicById(subtask.getEpic()));
        taskIdCounter++; // Увеличиваем счетчик для следующей задачи

    }


    public void updateTask(Task task) {
        int tastId = task.getId();
        if (tasksMap.containsKey(tastId)) {
            tasksMap.put(tastId, task);
        }
    }

    public void updateSubtask(Subtask subtask) {
        int subtaskId = subtask.getId();
        if (subtasksMap.containsKey(subtaskId)) {
            tasksMap.put(subtaskId, subtask);
        }
    }

    public void updateEpic(Epic epic) {
        int epicId = Epic.getId();
        if (epicsMap.containsKey(epicId)) {
            epicsMap.put(epicId, epic);
        }
    }


    public void deleteTaskById(int deleteId) {
        if (tasksMap.containsKey(deleteId)) {
            tasksMap.remove(deleteId);
        }
    }

    public void deleteSubtaskById(int id) {
        if (subtasksMap.containsKey(id)) {
            for (Integer subtask : getEpicById(getSubtaskById(id).getEpic()).getSubtasks()) {
                if (subtask == id) {
                    getEpicById(getSubtaskById(id).getEpic()).getSubtasks().remove(subtask);
                    break;
                }
            }
            updateEpicStatus(getEpicById(getSubtaskById(id).getEpic()));
            subtasksMap.remove(id);
        }
    }


    public void deleteEpicById(int id) {
        if (epicsMap.containsKey(id)) {

            for (Integer subtask : getEpicById(id).getSubtasks()) {
                subtasksMap.remove(subtask);
            }
        }
    }


    public List<Integer> getSubtasksByEpic(Epic epic) {
        return epic.getSubtasks();
    }


}