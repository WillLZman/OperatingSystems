/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OS;

import java.io.*;

/**
 *
 * @author Alex
 */

/*
 Driver {
 loader();
     loop
         scheduler();
         dispatcher();
         CPU();
         waitforinterrupt();
 endloop;
 }
 */
public class OS_Driver {

    public static void main(String[] args) throws Exception {
        OSystem OS= OSystem.getInstance();
        OS.getDispatcher().multiCPU(1);
        //OS.getLoader().readFile();
        OS.getLoader().readFile();
        System.out.println("Before FIFO");
        OS.getJobQueue().FIFO();
        //OS.getJobQueue().priority();

        int totJobs = OS.getJobQueue().size();
        System.out.println("TOTJobs: " + totJobs);
        System.out.println("Before WhileDriver");
        while (OS.getTerminatedQueue().size() != totJobs){
            System.out.println("AFter While");
            OS.getScheduler().setDisktoRAM();
            System.out.println("AftersettDISK");
            OS.getDispatcher().dispatchCPU();
            OS.getCpu().ComputeOnly();
        }

        //Scheduler L2 = new Scheduler(disk,ram);
        long esttime = System.currentTimeMillis();
        System.out.println("----------------------------------");
        System.out.println("############## END  ##############");
        System.out.println("Time: " + (esttime - OS.getSystemStartTime()));
        System.out.println("Job Queue: " + OS.getJobQueue().size());
        System.out.println("Ready Queue: " + OS.getReadyQueue().size());
        System.out.println("Termination Queue: " + OS.getTerminatedQueue().size());


        System.out.println("----------------------------------");
        int size = OSystem.getInstance().getTerminatedQueue().size();
        for (int i = 0; i < size; i++) {
            System.out.println("");
            ControlBlock controlBlock = OSystem.getInstance().getTerminatedQueue().getIndex(i);
            System.out.println("Job ID: " + controlBlock.getProgramID());
            System.out.println("Priority: " + controlBlock.getPriority());
            System.out.println("Wait Time: " + controlBlock.getProcessWaitTime());
            System.out.println("Completion Time: " + controlBlock.getProcessCompletionTime());
            System.out.println("Average Time: " + controlBlock.getProcessAvgTime());
            System.out.println("I/O operations: " + controlBlock.getioOperationMade());
            System.out.println("Percent Ram Used: " + (float)controlBlock.getPercenetageofRamUsed());
            System.out.println("");

         }

        }
/*
        RamMemory ram = new RamMemory(PCB);
        System.out.println("ControlBlock1: " + PCB[29].getInputBuffer());
       ///loader.fileOpen(disk);
        Scheduler longTerm = new Scheduler(disk, ram);
        //PCB =longTerm.calcInstructionSize();
        ram.setPCBList2(longTerm.longTermScheduleFromDiskToRAM());

        Dispatcher dispatcher = new Dispatcher(ram);
        //ShortTermScheduler shortScheduler = new ShortTermScheduler(dispatcher);
       // shortScheduler.FIFOShortTermScheduler();

        CPU cpu = new CPU(dispatcher);

        System.out.println("ControlBlock2: " + PCB[0].getPhysicalAddinRAMEnd());
        ShortTermScheduler shortTerm = new ShortTermScheduler(dispatcher);

        //L2.FIFOLongTermScheduler();
        //ram.printPCBList();

        for (int i = 0; i < 30; i++) { //PCB.hasNext
            System.out.println("Start Long?");

            System.out.println("StartFIFOLong");
            //longTerm.longTermScheduleFromDiskToRAM();
            System.out.println("Start Short");
            System.out.println("PCB Loc Begin: " + ram.getPCBListFromRAM()[i].getPhysicalAddinRAMBegin());
            System.out.println("PCB Loc End: " + ram.getPCBListFromRAM()[i].getPhysicalAddinRAMEnd());
            System.out.println("PCB Input Buf: " +ram.getPCBListFromRAM()[i].getInputBuffer());
            System.out.println("PCB Output Buf: " + ram.getPCBListFromRAM()[i].getOutputBufferSize());
            //System.out.println("StartFIFOSh");
           // shortTerm.FIFOShortTermScheduler();
            System.out.println("Start Execute");
            //cpu.Execute();



        }
        /*
        ControlBlock actualCB = new ControlBlock();
        CPU cpu1 = new CPU(actualCB);
        DiskMemory disk = new DiskMemory();
        Dispatcher dispatcher =new Dispatcher(cpu1);
        RamMemory ram = new RamMemory();
        ram.printPCBList();
        System.out.println("Start Loader");
        Loader loader = new Loader();
        //loader.(disk);
        loader.fileOpen();
        System.out.println("Start Long");

        for(int i = 0; i<30; i++) {
            Scheduler longTerm = new Scheduler(disk, ram);
            longTerm.FIFOLongTermScheduler();
            System.out.println("Start Short");
            ShortTermScheduler shortTerm = new ShortTermScheduler(ram, dispatcher);
            shortTerm.FIFOShortTermScheduler();
            System.out.println("Start Short1");
            cpu1.Execute();

        }
        */
    }




