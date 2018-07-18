package OS;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by William Zimmerman on 3/9/2017.
 */
public class JointMemory {
    private String [] memory;
    private int usedMem;
    private int maxSize;

    private ReadWriteLock mutexLock = new ReentrantReadWriteLock(); // gives sync, sync on mutexLock

    public JointMemory(int sizeOfMemory){
        usedMem = 0;
        memory = new String[sizeOfMemory];
        maxSize = sizeOfMemory;
    }

    public String read(int index){
        try {
            mutexLock.readLock().lock();
            return memory[index];
        }finally {
            mutexLock.readLock().unlock();
        }
    }
    public void writeToMem(int index, String string){
        try{
            mutexLock.writeLock().lock();
            if (memory[index] == null){
                //add data
                memory[index] = string;
                usedMem++;
            }else {
                //newOverwrite
                if (!memory[index].equals(string)){
                    memory[index]=string;
                }
            }
        }finally {
            mutexLock.writeLock().unlock();
        }
    }
    public void deleteMem (int index){
        if (usedMem <= 1){
            usedMem = 0;
        }
        memory[index] = null;
        usedMem--;
    }

    //public void overwriteToMem (int index, String string){
     //   memory[index] = string;
   // }

    public boolean isFull(){
        return usedMem == memory.length;
    }


    public int getMaxSize(){
        return memory.length;
    }// 1024

    public int getUsedMem(){
        return usedMem;
    }

    public int getSize(){
        int size = getMaxSize()-getUsedMem();
        return size;
    }

    public void setUsedMem(int usedMem){
        this.usedMem = usedMem;
    }

    public int percentageOfRAMspaceUsed(){
        return usedMem/maxSize;
    }
}
