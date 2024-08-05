package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final HashMap<Integer, Node<Task>> viewedTasksIndex = new HashMap<>();
    private Node<Task> first;
    private Node<Task> last;

    private void linkLast(Task task) {
        Node<Task> newNode = new Node<>(last, task, null);
        if (last != null) {
            last.next = newNode;
        } else {
            first = newNode;
        }
        newNode.prev = last;
        last = newNode;
        viewedTasksIndex.put(task.getId(), newNode);
    }

    private void removeNode(Node<Task> node) {
        if (node != null) {
            if (node == first && node == last) {
                first = null;
                last = null;
            } else if (node == first) {
                first = node.next;
                first.prev = null;
            } else if (node == last) {
                last = node.prev;
                last.next = null;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> retList = new ArrayList<>();
        Node<Task> currentNode = first;
        while (currentNode != null) {
            retList.add(currentNode.item);
            currentNode = currentNode.next;
        }
        return retList;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        removeNode(viewedTasksIndex.get(task.getId()));
        viewedTasksIndex.remove(task.getId());
        this.linkLast(task);
    }

    @Override
    public void remove(int id) {
        this.removeNode(viewedTasksIndex.get(id));
        viewedTasksIndex.remove(id);
    }

    @Override
    public void clearHistory() {
        viewedTasksIndex.clear();
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E item, Node<E> next) {

            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }


}
