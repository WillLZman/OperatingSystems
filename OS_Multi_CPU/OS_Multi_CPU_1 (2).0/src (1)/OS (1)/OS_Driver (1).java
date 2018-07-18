/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OS;

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
        //OS.getDispatcher().multiCPU(2);
        OS.getLoader().readFile();
        System.out.println("Before FIFO");
        //OS.getJobQueue().FIFO();
        OS.getJobQueue().priority();
        OS.getScheduler().setMDisktoRAM();



        OS.start();
        //OS.getScheduler().turnOn();
        OS.getScheduler().start();



/*
        int totJobs = OS.getJobQueue().size();
        int count = 0;
        System.out.println("Before WhileDriver");
        while (OS.getTerminatedQueue().size() != totJobs && count < 4){
            System.out.println("AFter While");
            //OS.getScheduler().setDisktoRAM();
            System.out.println("AftersettDISK");
           // OS.getDispatcher().dispatchCPU();
            OS.getCpu()[count].ComputeOnly();
            count++;
        }*/

        //Scheduler L2 = new Scheduler(disk,ram);
        /*
        long etime = System.currentTimeMillis();
        System.out.println("#####################################");
        System.out.println("############## END  ##############");
        System.out.println("#####################################");
        System.out.println("Time: " + (etime - OS.getSystemStartTime()));
        System.out.println("Job Queue: " + OS.getJobQueue().size());
        System.out.println("Ready Queue: " + OS.getReadyQueue().size());
        System.out.println("Termination Queue: " + OS.getTerminatedQueue().size());
*/
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
            System.out.println("Percent Ram Used: " + controlBlock.getPercenetageofRamUsed());
            System.out.println("");

        }

    }


}
