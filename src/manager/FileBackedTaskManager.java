package manager;

import tasks.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static tasks.TaskTypes.valueOf;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final String fileName;

    public FileBackedTaskManager(String fileName) {
        this.fileName = fileName;
    }

    public static Task fromString(String value) {
        String[] params = value.splitWithDelimiters(",", 5);
        if (params.length > 4) {
            int id = Integer.parseInt(params[0]);
            TaskTypes type = valueOf(params[1]);
            String name = params[2];
            TaskStatus status = TaskStatus.valueOf(params[3]);
            String description = params[4];
            int epicId = Integer.parseInt(params[5]);


            switch (type) {
                case TaskTypes.TASK:
                    return new Task(name, description, id, status);
                case TaskTypes.SUBTASK:
                    return new Subtask(name, description, id, status, epicId);
                case TaskTypes.EPIC:
                    return new Epic(name, description, id, status, new ArrayList<>());
            }
        }
        throw new ManagerSaveException();
    }

    public static FileBackedTaskManager loadFromFile(String filename) {
        var taskManager = new FileBackedTaskManager(filename);
        try (BufferedReader bReader = new BufferedReader(new FileReader(filename))) {
            while (bReader.ready()) {
                Task tsk = fromString(bReader.readLine());
                if (tsk instanceof Subtask) {
                    taskManager.subtasksMap.put(tsk.getId(), (Subtask) tsk);
                } else if (tsk instanceof Epic) {
                    taskManager.epicsMap.put(tsk.getId(), (Epic) tsk);
                } else {
                    taskManager.tasksMap.put(tsk.getId(), tsk);
                }
                taskManager.taskIdCounter = (Math.max(tsk.getId(), taskManager.taskIdCounter)) + 1;
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
        return new FileBackedTaskManager(filename);
    }

    public void save() {
        try (FileWriter writer = new FileWriter(fileName)) {
            super.getAllTasks().forEach(t -> {
                try {
                    writer.write(t.toString() + System.lineSeparator());
                } catch (IOException e) {
                    throw new ManagerSaveException();
                }
            });
            super.getAllSubtasks().forEach(t -> {
                try {
                    writer.write(t.toString() + System.lineSeparator());
                } catch (IOException e) {
                    throw new ManagerSaveException();
                }
            });
            super.getAllEpics().forEach(t -> {
                try {
                    writer.write(t.toString() + System.lineSeparator());
                } catch (IOException e) {
                    throw new ManagerSaveException();
                }
            });
        } catch (IOException e) {
            throw new ManagerSaveException();
        }

    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void deleteTaskById(int deleteId) {
        super.deleteTaskById(deleteId);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        super.updateEpicStatus(epic);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }
}
