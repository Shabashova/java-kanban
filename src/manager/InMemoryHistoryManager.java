package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final HashMap<Integer, Node> viewedTasksIndex = new HashMap<>();
    private Node first;
    private Node last;
  //  private int size;

    private void linkLast(Task task) {
        if (task == null) {
            return;
        }
        removeNode(viewedTasksIndex.get(task.getId()));
        viewedTasksIndex.remove(task.getId());

        Node newNode = new Node(last, task, null);
        if (last != null) {
            last.next = newNode;
        } else {
            first = newNode;
        }
        newNode.prev = last;
        last = newNode;
        viewedTasksIndex.put(task.getId(), newNode);
    }

    private void removeNode(Node node) {
        if (node != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
     //       node = null;
        }
    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> retList = new ArrayList<>();
        Node currentNode = first;
        while (currentNode != null) {
            retList.add(currentNode.task);
            currentNode = currentNode.next;
        }
        return retList;
    }

    @Override
    public void add(Task task) {
        this.linkLast(task);
    }

    @Override
    public void remove(int id) {
        this.removeNode(viewedTasksIndex.get(id));
        viewedTasksIndex.remove(id);
    }

    private static class Node {
        Task task;
        Node next;
        Node prev;

        Node(Node prev, Task task, Node next) {

            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }


}
