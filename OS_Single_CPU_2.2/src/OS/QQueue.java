package OS;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
/**
 * Created by William Zimmerman on 3/9/2017.
 */
public class QQueue {
   // private Queue<ControlBlock> queue;
    private ControlBlock.Status status;
    private LinkedList<ControlBlock> queue;

    public QQueue(ControlBlock.Status status){
        this.status = status;
        this.queue = new LinkedList<ControlBlock>();
    }

    public void priority() {
        Collections.sort(queue, (ControlBlock job1, ControlBlock job2) -> {
            return job1.getPriority() - job2.getPriority();
        });
    }
    public void FIFO() {
        Collections.sort(queue, (ControlBlock job1, ControlBlock job2) -> {
            return job1.getCpuID() - job2.getCpuID();
        });
    }

    public void push(ControlBlock controlBlock) {
        System.out.println("What are you pushing???? " + controlBlock.getCodeSize());
        controlBlock.setStatus(status);
        queue.addLast(controlBlock);
    }

    /* LinkedList<Integer> s = new LinkedList<>();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);

        System.out.println(s);
        s.pollLast();
        System.out.println(s);

        [4, 3, 2, 1]
    [4, 3, 2]
    addLast
    [1, 2, 3, 4]
[1, 2, 3]
pollFirst
    */
    public ControlBlock popTheQueue() {
        ControlBlock conTemp = null;
        System.out.println("How do you Pop?");
        if (queue.peek() == null){
            System.out.println("You are trying to pop a null value");
            return conTemp;
        } return queue.pollFirst();
    }

    public ControlBlock peekInQueue(){
        return queue.peek();
    }

    public ControlBlock getIndex(int index) {
        return queue.get(index);
    }

    public int size() {
        return queue.size();
    }

}
