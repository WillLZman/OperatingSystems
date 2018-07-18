/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OS;

/**
 * Created by wei on 2/14/2017.
 */

public class Dispatcher{

    ControlBlock[] runningQueue;
    //QQueue runningQueue;
    public void multiCPU (int numCPU){
        runningQueue = new ControlBlock[numCPU];
    }

    public void dispatchCPU(){

        ControlBlock controlBlock = OSystem.getInstance().getReadyQueue().popTheQueue(); //there is a null in the readyQueue at the very end look at screenshot "DispatcherReadyQueue
        if (controlBlock == null) {
            System.out.println("There is a null in the queue check setDisktoRAM");
        }

        //System.out.println("Current ready queue popped" + controlBlock.getProgramCounter());
       // System.out.println("running QQQ" + runningQueue);
        //runningQueue =OSystem.getInstance().getRunningQueue();
       // runningQueue.push(controlBlock);
        runningQueue[0] = controlBlock;
        controlBlock.setStatus(ControlBlock.Status.RUNNING);

    }

    protected ControlBlock getCurrentProcess (int ID){
        return  runningQueue[ID];
    }
    /*
    private ArrayList<Integer> readyQueue = new ArrayList<>();
    private  ControlBlock[] pcb;
    private int programCounterDispatched;
    private JointMemory ramMemory;
    private int readyQueueProcess;


    public Dispatcher(){
        this.pcb = ram.getPCBListOnRam();
        this.ramMemory = ram;
    }

    public int dispatchProgramCounter() {
        for (int i = 0; i < pcb.length; i++) {
            programCounterDispatched = pcb[i].getProgramCounter();
            System.out.println("Prog Count from Dispatch: " + programCounterDispatched);
            System.out.println(ramMemory.read(programCounterDispatched));
            readyQueue.add(programCounterDispatched);
            readyQueueProcess = readyQueue.get(i);
        }
        return readyQueueProcess;
    }

    public ControlBlock[] getPcb() {
        return pcb;
    }

    public JointMemory getRamMemory() {
        return ramMemory;
    }

    /*
    private CPU cpu;

    //public Dispatcher(CPU cpu){
      //  this.cpu = cpu;
  //  }

    public ControlBlock dispatchProcessToCPU(LinkedList<ControlBlock> readyQueue) {
        if (readyQueue.isEmpty()) {
            return null;
        }

        ControlBlock selectedProcess = readyQueue.popTheQueue();

        loadPCBStateIntoRegisters(selectedProcess);
        // cpu.setSelectedProcess(sendProcess); // need a setter from CPU to set this process as current process(for later use)get me !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        return selectedProcess;
    }

    private void loadPCBStateIntoRegisters(ControlBlock pcb) {
        if (pcb.getPCBState() == null) {
            return;
        }

        // long[] registers = cpu.getRegisters(); //need a getter that return the list of registers get!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        long[] registers = {};

        //store each process into CPU's register's list
        for (int i = 0; i < registers.length - 1; i++) {
            registers[i] = pcb.getPCBState()[i];
        }
    }

    public void contextSwitch(ControlBlock offCPU, LinkedList<ControlBlock> readyQueue) {
        loadRegisterStateIntoPCB(offCPU);
        readyQueue.add(offCPU);

        dispatchProcessToCPU(readyQueue);
    }

    //used for context switching
    private void loadRegisterStateIntoPCB(ControlBlock pcb) {
        // long[] registers = cpu.getRegisters();get!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        long[] registers = {};
        Long[] pcbState;

        if (pcb.getPCBState() != null) {
            pcbState = pcb.getPCBState();
        } else {
            pcbState = new Long[registers.length];
            pcb.setPCBState(pcbState);
        }

        for (int i = 0; i < registers.length - 1; i++) {
            pcbState[i] = registers[i];
        }
    }
*/
}

