package manager;

import org.w3c.dom.Node;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import  java.util.HashMap;
public class InMemoryHistoryManager implements HistoryManager {
    private static class node{
        Task task;
        node next;
        node prev;
        node( node prev, Task task, node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }
    
    private node first;
    private  node last;
    private int size;

    private final HashMap<Integer, node> viewedTasksIndex = new HashMap<>();

    private void linkLast(Task task){
        if (task == null){ return;}
        removeNode(viewedTasksIndex.get(task.getId()));
        viewedTasksIndex.remove(task.getId());

        node new_node = new node(last, task,null);
        if (last != null) {
            last.next = new_node;
        } else{
            first = new_node;
        }
        new_node.prev = last;
        last = new_node;
        viewedTasksIndex.put(task.getId(), new_node);
    }

    private void removeNode(node node) {
        if (node != null){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node = null;
        }
    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> retList = new ArrayList<>();
        node currentNode = first;
        while ( currentNode != null ){
            retList.add(currentNode.task);
            currentNode = currentNode.next;
        }
        return retList;
    }
    @Override
    public void add(Task task){
        this.linkLast(task);
    }
    @Override
    public void remove(int id){
     this.removeNode(viewedTasksIndex.get(id));
     viewedTasksIndex.remove(id);
    }


}
