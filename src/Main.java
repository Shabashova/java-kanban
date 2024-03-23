import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Создаем задачи
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task(" Изучить Java", "Пройти 5 спринт", 1, TaskStatus.NEW);
        Task task2 = new Task(" Сдать работу по 4 спринту", "Написать приложение по треккинг задач", 2, TaskStatus.IN_PROGRESS);
        List<Subtask> subtasks = new ArrayList<>();
        Epic epic1 = new Epic("Похудеть", "Похудеть к лету на 5 кг", 5, TaskStatus.NEW, subtasks);

        Epic epic2 = new Epic("Говорить по англиски", "Говорить по англиски", 5, TaskStatus.NEW, subtasks);
        Subtask subtask3 = new Subtask("  Выучить английкий", "Пройти уровень b2", 1, TaskStatus.NEW, epic2);
        Subtask subtask4 = new Subtask("  Выучить английкий хорошо", "Начать свободно говорить", 2, TaskStatus.IN_PROGRESS, epic2);
        Subtask subtask5 = new Subtask("  Выучить английки совсем хорошо", "Начать свободно говорить", 2, TaskStatus.IN_PROGRESS, epic2);

        // Создаем подзадачи и эпик
        Subtask subtask1 = new Subtask("  Заняться спортом", "Бег, тренажерный зал, растяжка", 3, TaskStatus.DONE, epic1);
        Subtask subtask2 = new Subtask("  Следить за питанием", "Никаких быстрых углеводов", 4, TaskStatus.IN_PROGRESS, epic1);
        Subtask newsubtask2 = new Subtask("  Хочу есть!", "Хочу быстрых углеводов", 4, TaskStatus.IN_PROGRESS, epic1);

        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(epic1);
        taskManager.createTask(epic2);
        taskManager.createTask(subtask1);
        taskManager.createTask(subtask2);
        taskManager.createTask(subtask3);
        taskManager.createTask(subtask4);
        taskManager.createTask(subtask5);

        taskManager.updateEpicStatus(epic1);
        taskManager.updateEpicStatus(epic2);


        System.out.println(" Задача: " + task1.getName() + " - Статус: " + task1.getStatus() + " - ID: " + task1.getId());
        System.out.println(" Задача: " + task2.getName() + " - Статус: " + task2.getStatus() + " - ID: " + task2.getId());
        System.out.println("Эпик: " + epic1.getName() + " - Количество подзадач: " + epic1.getSubtasks().size() + " - Статус: " + epic1.getStatus() +
                " - ID: " + epic1.getId());
        System.out.println("  Подзадача: " + subtask1.getName() + " - Статус: " + subtask1.getStatus() + " - ID: " + subtask1.getId());
        System.out.println("  Подзадача: " + subtask2.getName() + " - Статус: " + subtask2.getStatus() + " - ID: " + subtask2.getId());


        System.out.println("Эпик: " + epic2.getName() + " - Количество подзадач: " + epic2.getSubtasks().size() + " - Статус: " + epic2.getStatus() +
                " - ID: " + epic2.getId());
        System.out.println("  Подзадача: " + subtask3.getName() + " - Статус: " + subtask1.getStatus() + " - ID: " + subtask3.getId());
        System.out.println("  Подзадача: " + subtask4.getName() + " - Статус: " + subtask2.getStatus() + " - ID: " + subtask4.getId());
        System.out.println("  Подзадача: " + subtask5.getName() + " - Статус: " + subtask2.getStatus() + " - ID: " + subtask5.getId());

        System.out.println("К 1 ID найдена задача " + taskManager.getTaskById(1).getName());
        System.out.println("К 2 ID найдена задача " + taskManager.getTaskById(2).getName());
        System.out.println("К 3 ID найдена задача " + taskManager.getTaskById(3).getName());
        System.out.println("К 4 ID найдена задача " + taskManager.getTaskById(4).getName());
        System.out.println("К 5 ID найдена задача " + taskManager.getTaskById(5).getName());
        System.out.println("К 6 ID найдена задача " + taskManager.getTaskById(6).getName());
        System.out.println("К 7 ID найдена задача " + taskManager.getTaskById(7).getName());
        System.out.println("К 8 ID найдена задача " + taskManager.getTaskById(8).getName());
        System.out.println("К 9 ID найдена задача " + taskManager.getTaskById(9).getName());


        //taskManager.deleteAllTasks();
        //System.out.println (taskManager.getAllTasks());
        System.out.println("К 6 ID найдена задача " + taskManager.getTaskById(6).getName());
        taskManager.updateTask(6, (Task) newsubtask2);
        System.out.println("К 6 ID найдена задача " + taskManager.getTaskById(6).getName());
        System.out.println("К 1 ID найдена задача " + taskManager.getTaskById(1).getName());
        System.out.println(taskManager.getAllTasks());
        taskManager.deleteTaskById(1);
        //taskManager.deleteTaskById(3);
        taskManager.deleteTaskById(6);
        System.out.println(taskManager.getAllTasks());
        taskManager.getSubtasksByEpic(epic1);
        taskManager.getSubtasksByEpic(epic2);
    }
}
