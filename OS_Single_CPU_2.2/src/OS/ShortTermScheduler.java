package OS;
/**
 * Created by wei on 2/17/2017.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/*
public class ShortTermScheduler {
    private Dispatcher dispatcher;

    public ShortTermScheduler(Dispatcher dispatcher){
        this.dispatcher = dispatcher;
    }

    public void FIFOShortTermScheduler(){
        dispatcher.dispatchProgramCounter();
    }

/*
    private LinkedList<ControlBlock> readyQueue = new LinkedList<ControlBlock>();
    //protected CPU[] cpus;
    private Dispatcher dispatcher;
    protected RamMemory ram;
    private ControlBlock currentProcess;
    private final int READY = 2;
    private final int FINISHED = 3;

    //public ShortTermScheduler(RamMemory ram, Dispatcher dispatcher, CPU[] cpus) {
    public ShortTermScheduler(RamMemory ram, Dispatcher dispatcher) {
        this.ram = ram;
        this.dispatcher = dispatcher;
        //this.cpus = cpus;
    }

    public void FIFOShortTermScheduler() {
        clearFinishedProcesses();
        FIFOScheduleHelper(ram.getPCBListOnRam());

        if (currentProcess == null || currentProcess.getStatus() == FINISHED) {
            currentProcess = dispatcher.dispatchProcessToCPU(readyQueue);
            //currentProcess.setExecutionStartingTime();// **********FOR TIME RECORD**********
        }
        System.out.println("I am here!!!!");
    }

    public void PriorityShortTermScheduler() {
        clearFinishedProcesses();
        try {
            PriorityScheduleHelper(ram.getPCBListOnRam());
        } catch (NullPointerException E) {
            //X
        }

        if (currentProcess == null || currentProcess.getStatus() != FINISHED) {//X
            currentProcess = dispatcher.dispatchProcessToCPU(readyQueue);//X
            try{
                currentProcess.setExecutionStartingTime();//X FOR TIME RECORD
            }catch (NullPointerException N){

            }
        }
    }


    //private void FIFOScheduleHelper(ControlBlock[] pcbList, List<ControlBlock> currentProcesses) {
    private void FIFOScheduleHelper(ControlBlock[] pcbList) {
        for (ControlBlock pcb : pcbList) {
            if (pcb != null&& pcb.isInMemory() &&
                    !readyQueue.contains(pcb) &&
                    !pcb.equals(currentProcess)&&
                    // !currentProcesses.contains((pcb))&&
                    pcb.getStatus() != FINISHED) {
                addPCBToReadyQueue(pcb);
            }
        }
    }

    //private void FIFOScheduleHelper(ControlBlock[] pcbList, List<ControlBlock> currentProcesses) {
    private void PriorityScheduleHelper(ControlBlock[] pcbList){
        for (ControlBlock pcb : pcbList) {
            if (pcb != null&& pcb.isInMemory() &&
                    !readyQueue.contains(pcb) &&
                    !pcb.equals(currentProcess)&&
                    // !currentProcesses.contains((pcb))&&
                    pcb.getPriority() <= currentProcess.getPriority()) {
                addPCBToReadyQueue(pcb);
            }
        }
    }

    public void clearFinishedProcesses() {
        ControlBlock[] PCBList = ram.getPCBListOnRam();

        for (ControlBlock pcb: PCBList) {
            //Remove from memory if finished
            if (pcb != null && pcb.isInMemory() && pcb.getStatus() == FINISHED) {// NEED TO SET PCB STATUS TO "FINISHED" IN CPU RUN!!!!!!!!!!!!!!!!!!!!!!!!!
                //ram.removePCBFromMemory(pcb.getProgramCounter());
            }
        }
    }

    public void addPCBToReadyQueue(ControlBlock pcb){
        readyQueue.add(pcb);
        pcb.setInReadyQueueTime();// used to record arrival time
        pcb.setStatus(READY);
    }
}
}*/
