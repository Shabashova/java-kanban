package test;

import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class InMemoryTaskManagerTest {
    @org.junit.jupiter.api.Test
        //@BeforeAll
    void getAllTasks() {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("Задача 1", "Пэрвий", 1, TaskStatus.NEW);
        taskManager.createTask(task);
        assertEquals(taskManager.getAllTasks().get(0), task);
    }


    @org.junit.jupiter.api.Test
    void testTaskEqualityById() {
        Task task1 = new Task("Task1", "Task 1", 1, TaskStatus.NEW);
        Task task2 = new Task("Task1", "Task 1", 1, TaskStatus.NEW);
        assertEquals(task1.getName(), task2.getName());
        assertEquals(task1.getDescription(), task2.getDescription());
        assertEquals(task1.getStatus(), task2.getStatus());
    }


    @org.junit.jupiter.api.Test
    public void testAddTaskToInMemoryTaskManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Task 1", "Task 1", 1, TaskStatus.NEW);
        taskManager.createTask(task);
        assertTrue(taskManager.getAllTasks().contains(task));
    }

    @org.junit.jupiter.api.Test
    public void testUpdateTaskInInMemoryTaskManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Task 1", "Task 1", 1, TaskStatus.NEW);
        taskManager.createTask(task);
        Task updatedTask = new Task("Таск 1", "Task 1", 1, TaskStatus.NEW);
        taskManager.updateTask(updatedTask);
        assertEquals(updatedTask, taskManager.getTaskById(1));
    }

    @org.junit.jupiter.api.Test
    public void testAddSubtaskToEpicInInMemoryTaskManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        List<Integer> subtasks = new ArrayList<>();
        Epic epic = new Epic("Похудеть", "Похудеть к лету на 5 кг", 5, TaskStatus.NEW, subtasks);
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("  Заняться спортом", "Бег, тренажерный зал, растяжка", 1, TaskStatus.DONE, 1);
        taskManager.createSubtask(subtask);
        //subtask.getId();
        Integer kid = subtask.getId();
        // int kid = subtask.getId();
        assertTrue(epic.getSubtasks().contains(kid));

    }


}
