package test;

import manager.FileBackedTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class FileBackedTaskManagerTest {
    @org.junit.jupiter.api.Test
        //@BeforeAll
    void serializeDeserializeTest() throws IOException {
        TaskManager taskManager = null;
        try {
            var file = File.createTempFile("test", "0");
            System.out.println(file.getPath());
            taskManager = FileBackedTaskManager.loadFromFile(file.getPath());
            System.out.println(taskManager);
        } catch (IOException e) {
            throw new IOException(e);
        }
        Assertions.assertNotNull(taskManager);
        taskManager.deleteAllTasks();

    }

    @org.junit.jupiter.api.Test
        //@BeforeAll
    void saveTasksTest() throws IOException {
        TaskManager taskManager = null;
        try {
            var file = File.createTempFile("test", "0");
            System.out.println(file.getPath());
            taskManager = FileBackedTaskManager.loadFromFile(file.getPath());
            Assertions.assertNotNull(taskManager);
            var epic = new Epic("Эпик 1", "Эпик 1", 0, TaskStatus.NEW, new ArrayList<>());
            taskManager.createEpic(epic);
            Assertions.assertEquals(taskManager.getAllEpics().size(), 1);

            taskManager.createTask(new Task("Задача 1", "Задача1", 1, TaskStatus.NEW));
            taskManager.createTask(new Task("Задача 2", "Задача2", 2, TaskStatus.NEW));
            taskManager.createTask(new Task("Задача 3", "Задача2", 3, TaskStatus.NEW));
            Assertions.assertEquals(taskManager.getAllTasks().size(), 3);

            taskManager.createSubtask(new Subtask("подЗадача 3", "подЗадача2", 4, TaskStatus.NEW, epic.getId()));
            taskManager.createSubtask(new Subtask("подЗадача 3", "подЗадача2", 5, TaskStatus.NEW, epic.getId()));
            Assertions.assertEquals(taskManager.getAllSubtasks().size(), 2);
        } catch (IOException e) {
            throw new IOException(e);
        }


        //taskManager.deleteAllTasks();

    }
}