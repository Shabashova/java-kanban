package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.*;
import java.util.ArrayList;

public class FileBackedTaskManager extends InMemoryTaskManager {
    enum taskTypes {
        TASK,
        EPIC,
        SUBTASK
    }
    String fileName;
    public FileBackedTaskManager( String fileName ){
        this.fileName = fileName;
    }

    public void save() {
        try (FileWriter writer = new FileWriter( fileName )){
            super.getAllTasks().forEach( t-> {
                try {
                    writer.write (t.toString() + System.lineSeparator());
                } catch (IOException e) {
                    throw new ManagerSaveException();
                }
            });
            super.getAllSubtasks().forEach( t-> {
                try {
                    writer.write(t.toString()+ System.lineSeparator());
                } catch (IOException e) {
                    throw new ManagerSaveException();
                }
            });
            super.getAllEpics().forEach( t-> {
                try {
                    writer.write(t.toString()+ System.lineSeparator());
                } catch (IOException e) {
                    throw new ManagerSaveException();
                }
            });
        } catch (IOException e) {
            throw new ManagerSaveException();
        }

    }

    public static <T extends Task> T fromString(String value) {
        var params = value.splitWithDelimiters(",", 5);
        if (params.length > 4) {
            switch ( params[1]) {
                case ("TASK"):
                    return (T) new Task(params[2], params[4], Integer.parseInt(params[0]), TaskStatus.valueOf(params[3]));
                case ("SUBTASK"):
                    return (T) new Subtask(params[2], params[4], Integer.parseInt(params[0]), TaskStatus.valueOf(params[3]), Integer.parseInt(params[5]));
                case ("EPIC"):
                    return (T) new Epic(params[2], params[4], Integer.parseInt(params[0]), TaskStatus.valueOf(params[3]), new ArrayList<>());
            }
        }
        throw new ManagerSaveException();
    }
    public static <T extends Task> FileBackedTaskManager loadFromFile(String filename){
        var taskManager = new FileBackedTaskManager(filename);
        try ( BufferedReader bReader = new BufferedReader(new FileReader( filename))){
            while (bReader.ready()){
                T tsk = fromString(bReader.readLine());
                if (tsk instanceof Subtask){
                    taskManager.createSubtask((Subtask) tsk);
                } else if ( tsk instanceof  Epic){
                    taskManager.createEpic((Epic) tsk);
                } else {
                    taskManager.createTask(tsk);
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
        return new FileBackedTaskManager(filename);
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
}
