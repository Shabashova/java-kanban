package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasksMap = new HashMap<>();
    private final Map<Integer, Epic> epicsMap = new HashMap<>();

    private final Map<Integer, Subtask> subtasksMap = new HashMap<>();
    private final HistoryManager historyManager = Managers.HistoryManagerGetDefault();
    private int taskIdCounter = 1; // Поле-счетчик для генерации идентификаторов задач

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasksMap.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasksMap.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epicsMap.values());
    }

    @Override
    public void deleteAllTasks() {
        tasksMap.clear();
    }

    @Override
    /*public void deleteAllSubtasks(){
        subtasksMap.clear();
        List<Epic> epicList = getAllEpics();
        for (int i = 0; i< epicList.size(); i++){
            epicList.get(i).setSubtasks( new ArrayList<Integer>());
            epicList.get(i).setStatus( TaskStatus.NEW);
        }
    }*/
    public void deleteAllSubtasks() {
        subtasksMap.clear();

        List<Epic> epicList = getAllEpics();

        for (Epic epic : epicList) {
            epic.getSubtasks().clear();
        }
    }


    @Override
    public void deleteAllEpics() {
        epicsMap.clear();
        subtasksMap.clear();
    }


    @Override
    public Task getTaskById(int id) {
        Task taskById = tasksMap.get(id);
        historyManager.add(taskById);
        return taskById;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtaskById = subtasksMap.get(id);
        historyManager.add(subtaskById);
        return subtaskById;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epicById = epicsMap.get(id);
        historyManager.add(epicById);
        return epicById;
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        List<Integer> subtasks = epic.getSubtasks();
        boolean allDone = true;
        boolean allNew = true;
        for (Integer subtask : subtasks) {
            // if (getSubtaskById(subtask).getStatus() != TaskStatus.DONE)
            if (subtasksMap.get(subtask).getStatus() != TaskStatus.DONE) {
                allDone = false;
            }
            if (getSubtaskById(subtask).getStatus() != TaskStatus.NEW) {
                allNew = false;
            }
            if (!(allDone || allNew)) {
                break;
            }
            if (allDone) {
                epic.setStatus(TaskStatus.DONE);
            } else if (allNew) {
                epic.setStatus(TaskStatus.NEW);
            } else {
                epic.setStatus(TaskStatus.IN_PROGRESS);
            }
        }
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(taskIdCounter); // Присваиваем уникальный идентификатор задачи
        epicsMap.put(epic.getId(), epic);
        List<Integer> subtasks = new ArrayList<>();
        epic.setSubtasks(subtasks);
        taskIdCounter++;
    }


    @Override
    public void createTask(Task task) {
        task.setId(taskIdCounter); // Присваиваем уникальный идентификатор задачи
        tasksMap.put(task.getId(), task);
        taskIdCounter++; // Увеличиваем счетчик для следующей задачи
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtask.setId(taskIdCounter); // Присваиваем уникальный идентификатор задачи
        subtasksMap.put(subtask.getId(), subtask);
        // getEpicById(subtask.getEpic()).getSubtasks().add(taskIdCounter);// находим эпик по подздаче, находим список эпика, добавляем подзадачу в список
        epicsMap.get(subtask.getEpic()).getSubtasks().add(taskIdCounter);
        updateEpicStatus(getEpicById(subtask.getEpic()));
        taskIdCounter++; // Увеличиваем счетчик для следующей задачи

    }


    @Override
    public void updateTask(Task task) {
        int tastId = task.getId();
        if (tasksMap.containsKey(tastId)) {
            tasksMap.put(tastId, task);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        int subtaskId = subtask.getId();
        if (subtasksMap.containsKey(subtaskId)) {
            tasksMap.put(subtaskId, subtask);
            updateEpic(getEpicById(subtask.getEpic()));
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        int epicId = epic.getId();
        if (epicsMap.containsKey(epicId)) {
            epicsMap.put(epicId, epic);
        }
    }


    @Override
    public void deleteTaskById(int deleteId) {
        tasksMap.remove(deleteId);
        historyManager.remove(deleteId);
    }

    /*@Override
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
    } */

    @Override
    public void deleteSubtaskById(int id) {
        if (subtasksMap.containsKey(id)) {
            Integer idInteger = id;
            //getEpicById(getSubtaskById(id).getEpic()).getSubtasks().remove(idInteger);
            epicsMap.get(subtasksMap.get(id).getEpic()).getSubtasks().remove(idInteger);
            updateEpicStatus(getEpicById(getSubtaskById(id).getEpic()));
            subtasksMap.remove(id);
        }
    }


    @Override
    public void deleteEpicById(int id) {
        if (epicsMap.containsKey(id)) {

            for (Integer subtask : getEpicById(id).getSubtasks()) {
                subtasksMap.remove(subtask);
            }
            epicsMap.remove(id);
        }
    }


    @Override
    public List<Integer> getSubtasksByEpic(Epic epic) {
        return epic.getSubtasks();
    }

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
    // 5 sprint


}












