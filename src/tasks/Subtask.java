package tasks;

public class Subtask extends Task {
    //private Epic epic;
    private int epic;

    public Subtask(String name, String description, int id, TaskStatus status, int epic) {
        super(name, description, id, status);
        this.epic = epic;
    }

    public int getEpic() {
        return epic;
    }

    public void setEpic(int epicId) {
        this.epic = epicId;
    }

    @Override
    public String toString() {
        return super.getId() + ","
                + "SUBTASK" + ","
                + super.getName() + ","
                + super.getStatus() + ","
                + super.getDescription() + ","
                + this.epic;
    }
}
