package test;

import manager.*;
import org.junit.jupiter.api.Test;
import tasks.*;
import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    @Test
    //AlexeyCheuzov yesterday
    // Теперь объект T можно протестировать как TaskManager
    //
    // вот ну вообще не поняла, что здесь надо сделать. TaskManager - это интерфейс,
    // инстранциировать напрямую объект типа T невозможно, фабрики для него нет.
    // М.б., и так сойдёт?
    void getAllTasks() {
        TaskManager taskManager = Managers.getDefault();
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
