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
        return new ArrayList<>(tasksMap.values());
    }

    public List<Subtask> getAllSubtasks() {
        return  new ArrayList<>(subtasksMap.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epicsMap.values());
    }

    public void deleteAllTasks() {
        tasksMap.clear();
    }

    public void deleteAllSubtasks(){
        subtasksMap.clear();
        List<Epic> epicList = getAllEpics();
        for (int i = 0; i< epicList.size(); i++){
            epicList.get(i).setSubtasks( new ArrayList<Integer>());
            epicList.get(i).setStatus( TaskStatus.NEW);
        }
    }

    public void deleteAllEpics() {
        epicsMap.clear();
        subtasksMap.clear();
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

    public void updateEpicStatus(Epic epic) {
        List<Integer> subtasks = epic.getSubtasks();
        boolean allDone = true;
        boolean allNew = true;
        for (Integer subtask : subtasks) {
            if (getSubtaskById(subtask).getStatus() != TaskStatus.DONE) {
                allDone = false;
            }
            if (getSubtaskById(subtask).getStatus() != TaskStatus.NEW) {
                allNew = false;
            }
            if ( !(allDone || allNew)){
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
            updateEpic(getEpicById( subtask.getEpic() ));
        }
    }

    public void updateEpic(Epic epic) {
        int epicId = Epic.getId();
        if (epicsMap.containsKey(epicId)) {
            epicsMap.put(epicId, epic);
        }
    }


    public void deleteTaskById(int deleteId) {
        tasksMap.remove(deleteId);
    }

    public void deleteSubtaskById(int id) {
        if (subtasksMap.containsKey(id)) {
         Integer subtask = id;
         getEpicById(getSubtaskById(id).getEpic()).getSubtasks().remove(subtask);
         updateEpicStatus(getEpicById(getSubtaskById(id).getEpic()));
         subtasksMap.remove(id);
        }
    }



    public void deleteEpicById(int id) {
        if (epicsMap.containsKey(id)) {

            for (Integer subtask : getEpicById(id).getSubtasks()) {
                subtasksMap.remove(subtask);
            }
            epicsMap.remove(id);
        }
    }


    public List<Integer> getSubtasksByEpic(Epic epic) {
        return epic.getSubtasks();
    }


}