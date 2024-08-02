package tasks;

import java.util.List;

public class Epic extends Task {
    // private List<Subtask> subtasks;
    private List<Integer> subtasks;

    public Epic(String name, String description, int id, TaskStatus status, List<Integer> subtasks) {
        super(name, description, id, status);
        this.subtasks = subtasks;
    }

    public List<Integer> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Integer> subtasks) {
        this.subtasks = subtasks;
    }

    public String toString() {
        return super.getId() + ","
                + "SUBTASK" + ","
                + super.getName() + ","
                + super.getStatus() + ","
                + super.getDescription() + ",";
    }
}

