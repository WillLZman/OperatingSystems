package OS;

/**
 * Created by William Zimmerman on 3/9/2017.
 */
public class JointMemory {
    private String [] memory;
    private int usedMem;
    private int maxSize;

    public JointMemory(int sizeOfMemory){
        usedMem = 0;
        memory = new String[sizeOfMemory];
        maxSize = sizeOfMemory;
    }
    ControlBlock controlBlock;
    public String read(int index){

        return memory[index];
    }
    public void writeToMem(int index, String string){
        memory[index] = string;
        usedMem++;

//        controlBlock.ioOperationMade();
    }
    public void deleteMem (int index){
        if (usedMem <= 1){
            usedMem = 0;
        }
        memory[index] = null;
        usedMem--;
    }

    public void overwriteToMem (int index, String string){
        memory[index] = string;
    }

    public boolean isFull(){
        return usedMem == memory.length;
    }


    public int getMaxSize(){
        return memory.length;
    }// 1024

    public int getUsedMem(){
        System.out.println("GetUsed RAM " +usedMem);
        return usedMem;
    }

    public int getSize(){
        int size = getMaxSize()-getUsedMem();
        return size;
    }

    public void setUsedMem(int usedMem){
        this.usedMem = usedMem;
    }

    public double percentageOfRAMspaceUsed(){
        System.out.println("RAM used in Joint: " + usedMem/maxSize);
        double percentOfRam = (double)usedMem/(double)maxSize;
        return percentOfRam;
    }
}
