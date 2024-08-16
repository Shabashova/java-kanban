package test;

import manager.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    @Test
    void getAllTasks() {
        Task task = new Task("Задача 1", "Пэрвий", 1, TaskStatus.NEW);
        taskManager.createTask(task);
        assertEquals(taskManager.getAllTasks().getFirst(), task);
    }

    @Test
    void testTaskEqualityById() {
        Task task1 = new Task("Task1", "Task 1", 1, TaskStatus.NEW);
        Task task2 = new Task("Task1", "Task 1", 1, TaskStatus.NEW);
        assertEquals(task1.getName(), task2.getName());
        assertEquals(task1.getDescription(), task2.getDescription());
        assertEquals(task1.getStatus(), task2.getStatus());
    }

}
