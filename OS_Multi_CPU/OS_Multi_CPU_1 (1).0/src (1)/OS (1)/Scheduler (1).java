package OS;

import java.util.LinkedList;

/**
 * Created by wei on 2/14/2017.
 */
public class Scheduler extends threadAsymmetric{

    /**
     * disk to ram will act as part of the Long Term Scheduler moving jobs(controlBlocks) from the JobQueue
     * used in Loader to the the ReadyQueue
     * Any line with "synchronized" is where a "lock" is used to make sure data is not changed at an incorrect time
     */
    private LinkedList<ControlBlock>inRamMem = new LinkedList<>();
    public void setMDisktoRAM() {
        System.out.println("Test0DiskToRAM");
        OSystem oSystem = OSystem.getInstance();
        synchronized (oSystem.getRam()){
            while (oSystem.getJobQueue().lookInto().getProcessSize() < oSystem.getRam().getSize() && oSystem.getJobQueue().size() >0){
                ControlBlock controlBlock = oSystem.getJobQueue().popTheQueue();

                int totSize = controlBlock.getProcessSize();
                int diskLoc = controlBlock.getInstructionLocationOnDisk();
                int ramLoc = oSystem.getRam().getUsedMem();


                controlBlock.setPhysicalAddinRAM(ramLoc);

                int endLoc = diskLoc + totSize;
                while (diskLoc < endLoc){
                    String data = oSystem.getDisk().read(diskLoc);
                    oSystem.getRam().writeToMem(ramLoc,data);

                    diskLoc++;
                    ramLoc++;
                }
                synchronized (OSystem.getInstance().getReadyQueue()){
                    oSystem.getReadyQueue().push(controlBlock);
                    inRamMem.addLast(controlBlock);
                }
                if (oSystem.getJobQueue().size() == 0){
                    break; //finish
                }
            }
        }
    }

        public void setMRamtoDisk(){
            OSystem oSystem1 = OSystem.getInstance();
            synchronized (oSystem1.getRam()){
                while (!inRamMem.isEmpty()){
                    ControlBlock controlBlock = inRamMem.pop();
                    int totSize = controlBlock.getProcessSize();
                    int ramLoc = controlBlock.getPhysicalAddinRAM();
                    int diskLoc = controlBlock.getDataLocationOnDisk();
                    int processEnd = ramLoc + totSize;

                    while (ramLoc < processEnd){
                        String data = oSystem1.getDisk().read(ramLoc);
                        oSystem1.getRam().writeToMem(diskLoc,data);

                        ramLoc++;
                        diskLoc++;

                    }
                }
            }
    }
    public void mSchedule(){
        if (!inRamMem.isEmpty()){
            setMRamtoDisk();
        }
        setMDisktoRAM();
    }

    @Override
    public void run(){
    //public  void turnOn(){
        OSystem oSystem  = OSystem.getInstance();
        while (!Thread.currentThread().isInterrupted()){
            while (oSystem.sleepAllCPUs()==false){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.out.println("error with sleeping CPUs in turnOn-Scheduler");
                }
            }
            if (oSystem.endCPUs()) {
                setMRamtoDisk();

                for (int i = 0; i < 30; i++) {
                    ControlBlock controlBlock = oSystem.getTerminatedQueue().getIndex(i);
                    System.out.println("Job in Terminated Queue" + controlBlock.getCpuID());
                }
                long currentT = System.currentTimeMillis();
                long endT = currentT - oSystem.getSystemStartTime();
                System.out.println("System Time: " + endT);

                Thread.currentThread().interrupt();
                end();
            }else {
                mSchedule();
                oSystem.wakeUpCallAllCPUs();
            }
        }
    }
}



    /*
        ControlBlock controlBlock = oSystem.getJobQueue().popTheQueue();
        oSystem.getReadyQueue().push(controlBlock);
        int currentDiskLoc = controlBlock.getInstructionLocationOnDisk();
        int currentRamLoc = oSystem.getRam().getUsedMem();
        System.out.println("currentRamLoc" + currentRamLoc);
        System.out.println("Test1DiskToRAM" + oSystem.getRam().getMaxSize());
       try {
           System.out.println("Test2DiskToRAM");
           if ((!controlBlock.isInMemory())  && (controlBlock.getStatus() != ControlBlock.Status.TERMINATED)
                   && (( controlBlock.getProcessSize() < oSystem.getRam().getSize())) ) {
               System.out.println("Test3DiskToRAM");

               //loadProcessIntoMemory(pcb.getProgramCounter());// load each process into RAM
               for (int i = currentDiskLoc; i < 1024;i++){//0  < 1024
                   System.out.println("Test4forDisk" + i);
                   String data = oSystem.getDisk().read(currentDiskLoc);
                   oSystem.getRam().writeToMem(currentRamLoc, data);
                   //Setting the waiting time for each process
                   //pcb.setStatus(READY);

                   currentDiskLoc++;
                   currentRamLoc++;
               }
           }
       }catch (OutOfMemoryError e) {
           System.out.println("Out Of Memory ----Error");
       }



    }

    public void setRamtoDisk(ControlBlock controlBlock){
        int diskLoc= controlBlock.getInstructionLocationOnDisk();
        int ramLoc = controlBlock.getBaseRegister();

        int processEnd =ramLoc+ controlBlock.getProcessSize();
        while(ramLoc<processEnd){
            String data = OSystem.getInstance().getRam().read(ramLoc);
            OSystem.getInstance().getDisk().overwriteToMem(diskLoc,data);

            diskLoc++;
            ramLoc++;
        }
    }
}

/*
    protected JointMemory disk;
    protected JointMemory ram;
    private List<ControlBlock> waitingQueue = new ArrayList<ControlBlock>();
    private int currentPriorityAllowed = 1;
    private int READY = 2;
    private int FINISHED = 3;
    private int sizeOfRAM;
    private int current = 0;
    /*
    public Scheduler(int physicalAddinRAM, int physicalAddinRAMBegin, int physicalAddinRAMEnd) {
        this.physicalAddinRAM = physicalAddinRAM;
        this.physicalAddinRAMBegin = physicalAddinRAMBegin;
        this.physicalAddinRAMEnd = physicalAddinRAMEnd;
    } */
/*
    public Scheduler(JointMemory disk, JointMemory ram) {
        this.disk = disk;
        this.ram = ram;
        System.out.println("asdasdadadTEWST");
    }

    /**
     * Loads the process into RAM
     * @param ID The ID of the process to be added to RAM
     */
 /*   public void loadProcessIntoMemory(int ID){
      //  ram.addPCBToRamByID(, disk);
    }

     //Alternative to using Program Counter
    public ControlBlock[] calcInstructionSize()
    {
        ControlBlock[] PCB = disk.getPCBListOnDisk();
        for(int i = 0; i < 30; i++) {
            if (i == 0)
            {
                PCB[i].setPhysicalAddinRAMBegin(0);//PCB 1 start 0- < 536(char array) 536
                //System.out.println("PCB 1 Loc : " + PCB[i].getPhysicalAddinRAMBegin());
                PCB[i].setReg2(PCB[i].getPhysicalAddinRAMBegin());
                PCB[i].setPhysicalAddinRAMEnd(PCB[i].getPhysicalAddinRAMBegin() + PCB[i].getProcessSize());//536
                //System.out.println("PCB 1 end loc: " + PCB[i].getPhysicalAddinRAMEnd());
                PCB[i].setReg11(PCB[i].getPhysicalAddinRAMEnd());
                ///PCB[i].setSizeOfRAM(PCB[i].getOutputBufferSize() - PCB[i].getInputBuffer());
                //System.out.println("GET SIZE OF RAM " + PCB[i].getSizeOfRAM());
            }
            else
            {
                PCB[i].setPhysicalAddinRAMBegin(PCB[i - 1].getPhysicalAddinRAMEnd());// PCB2: START 536 <
                //System.out.println("PCB " + (i) + " Loc : " + PCB[i].getPhysicalAddinRAMBegin());
                PCB[i].setReg2(PCB[i].getPhysicalAddinRAMBegin());
                PCB[i].setPhysicalAddinRAMEnd(PCB[i].getPhysicalAddinRAMBegin() + PCB[i].getProcessSize());
                //System.out.println("PCB " + (i) + " Loc : " + PCB[i].getPhysicalAddinRAMEnd());
                //PCB[i].setReg11(PCB[i].getPhysicalAddinRAMEnd());
               // PCB[i].setSizeOfRAM(PCB[i].getOutputBufferSize() - PCB[i].getInputBuffer());//536
            }
        }
        return PCB;
    }
    public ControlBlock[] longTermScheduleFromDiskToRAM(){
    for (int i = 0; ((i<calcInstructionSize().length)); i++){
        if (ram.hasSpaceForProcess(calcInstructionSize()[i])){

        ram.addPCBToRamByID(calcInstructionSize()[i],disk);
        //current = i;
        }else {
            ram.removePCBFromMemory(i - 1);
        }
    }
    return calcInstructionSize();
    }
    /**
     * basic scheduling that load the processes into RAM in a given order(FIFO)
     * Precondition: pcb has not been loaded into RAM yet, is not finished executing, and the RAM has enough space to hold its contents
     */
 /*   public void FIFOLongTermScheduler() {
        System.out.println("TestBefore");

        ControlBlock pcb1[];
        pcb1 = disk.getPCBListOnDisk();
        int count = 0;
        for (ControlBlock pcb : pcb1){
        count++;
        System.out.println("pcb number: " + count);

        //for (int i = 0; i < disk.searchPCB(i).getProgramCounter(); i++) {
            System.out.println("disk search pcb program counter: " + disk.searchPCB(count).getProgramCounter());
            System.out.println("TestFor>");
            System.out.println(pcb.isInMemory() + "LongTerm Problem: " + ram.hasSpaceForProcess(pcb) + " ; " + pcb.getStatus());
            if ((!pcb.isInMemory()) && (ram.hasSpaceForProcess(pcb)) && (pcb.getStatus() != FINISHED)) {
                System.out.println("TestIf>");
                pcb.setInWaitingQueueTime();//Setting the waiting time for each process
                //pcb.setStatus(READY);
                loadProcessIntoMemory(pcb.getProgramCounter());// load each process into RAM

            }


        }/*
        //test FIFO (Char ArrayList found on internet)
        //http://stackoverflow.com/questions/9580457/fifo-class-in-java
        ArrayList<ControlBlock> arr = new ArrayList(); // this disk
        int i=2;
        System.out.println("HIIII");
        System.out.println(disk.searchPCB(i));
        System.out.println(disk.searchPCB(i).getProgramCounter());
       while (disk.searchPCB(i).getProgramCounter()<=30){
           arr.add(disk.searchPCB(i));
           System.out.println("Test of DiskFIFO" + arr.get(i));
           i++;

       }
        QQueue<ControlBlock> fifo = new LinkedList<>(); // this is ram

        for (int i1 = 0; i1 < arr.size(); i1++)
            fifo.add(arr.get(i1));

        System.out.print (fifo.remove() + ".");
        while (! fifo.isEmpty())
            System.out.print (fifo.remove());
        System.out.println("Finished/FIFO Test");
        System.out.println("LIST EMPTY? " + fifo);
        //end of FIFO test*/


    /*   public void PriorityLongTermScheduler() {
           //Creating a temporaryPCB for sorting use
           ArrayList<ControlBlock> temporaryPCB = new ArrayList<ControlBlock>(30);
           for (int i = 0; i < disk.getPCBListOnDisk().length; i++) {
               temporaryPCB.add(disk.getPCBListOnDisk()[i]);
           }
           //Sorting the PCB list based on priority
           Collections.sort(temporaryPCB);

           for (ControlBlock pcb : temporaryPCB) {
               if (!pcb.isInMemory() && ram.hasSpaceForProcess(pcb) && pcb.getStatus() != FINISHED) {//**********************************************************************8
                   pcb.setInWaitingQueueTime();
                   loadProcessIntoMemory(pcb.getProgramCounter());
               }
           }
       }

       /**
        * check whether all the processes on the Disk has been
        * @return Whether all processes are finished
        */
 /*   public boolean hasFinishedAllProcesses(){
        for (ControlBlock pcb: disk.getPCBListOnDisk()){
            if (pcb.getStatus() != FINISHED)
                return false;
        }
        return true;
    }
}
*/
