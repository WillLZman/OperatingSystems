package OS;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by William Zimmerman on 3/9/2017.
 */
public class QQueue {
   // private Queue<ControlBlock> queue;
    private ControlBlock.Status status;
    private LinkedList<ControlBlock> queue;
    private ReadWriteLock mutexLock = new ReentrantReadWriteLock();

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

    public void push(ControlBlock pcb) {
        //if (mutexLock.readLock().tryLock()){
        try {
            mutexLock.writeLock().lock();
            pcb.setStatus(status);
            queue.addLast(pcb);
        }finally {
            mutexLock.writeLock().unlock();
        }
    }
  //  }

    public ControlBlock popTheQueue() {
        //System.out.println("How do you Pop?");
        try {
            mutexLock.readLock().lock();
            return queue.pollLast();
        }finally {
            mutexLock.readLock().unlock();
        }
    }




    public ControlBlock getIndex(int index) {
        try {
            mutexLock.readLock().lock();
            return queue.get(index);
        }finally {
            mutexLock.readLock().unlock();
        }
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty(){
        try {
                mutexLock.readLock().lock();
                return queue.isEmpty();
        }finally {
            mutexLock.readLock().unlock();
        }
    }

    public ControlBlock lookInto(){
        return queue.peek();
    }

}
