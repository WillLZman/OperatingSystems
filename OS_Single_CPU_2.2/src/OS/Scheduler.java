package OS;

/**
 * Created by wei on 2/14/2017.
 */
public class Scheduler {

    /**
     * disk to ram will act as part of the Long Term Scheduler moving jobs(controlBlocks) from the JobQueue
     * used in Loader to the the ReadyQueue
     */
    public void setDisktoRAM() {

        //System.out.println("Test0DiskToRAM");
        OSystem oSystem = OSystem.getInstance();

        for (int i =0; i<oSystem.getJobQueue().size(); i++){
         System.out.println("Inside the Job Queue: " + oSystem.getJobQueue().getIndex(i).getCodeSize());
        }

        System.out.println("UsedDisk: " +  oSystem.getDisk().getUsedMem());

        ControlBlock controlBlock = OSystem.getInstance().getJobQueue().popTheQueue();
        System.out.println("What are you poppin? " + controlBlock.getCodeSize());
        System.out.println("What is CodeSize: " + controlBlock.getCodeSize());

        OSystem.getInstance().getReadyQueue().push(controlBlock);




        System.out.println("Size of Ready Queue: " + OSystem.getInstance().getReadyQueue().size());
        oSystem.getRam().setUsedMem(0);
        int currentDiskLoc = controlBlock.getInstructionLocationOnDisk();
        System.out.println("what is the CurrentDiskLoc? : " + currentDiskLoc);

        int currentRamLoc = oSystem.getRam().getUsedMem();
        System.out.println("what is the CurrenntRAMLoc? : " + currentRamLoc);

        int totSize = controlBlock.getInputBuffer() + controlBlock.getOutputBufferSize() + controlBlock.getTempBufferSize() + controlBlock.getCodeSize(); //aka Process Size
        System.out.println("totSize in setDiskToRAM: " + totSize);
        if (oSystem.getJobQueue().peekInQueue() == null) {
            System.out.println("There is a null in the queue check setDisktoRAM");
        }
        //System.out.println("currentRamLoc" + currentRamLoc);
       // System.out.println("Test1DiskToRAM" + oSystem.getRam().getMaxSize());
      // try {
           //System.out.println("Test2DiskToRAM");
           //if ((!controlBlock.isInMemory())  && (controlBlock.getStatus() != ControlBlock.Status.TERMINATED) //if controlblock is not in MEM and the Status is not Terminated and process size is less than remaining size of RAM
                 //  && ( ( controlBlock.getProcessSize() < oSystem.getRam().getSize())))//other: ( controlBlock.getProcessSize() < oSystem.getRam().getSize()))//( oSystem.getRam().getSize()<controlBlock.getProcessSize()))
              // {
                    //System.out.println("Test3DiskToRAM");

                    //loadProcessIntoMemory(pcb.getProgramCounter());// load each process into RAM
                    //for (int i = currentDiskLoc; i < 2048;i++){//0  < 1024
                        //  System.out.println("Test4forDisk" + i);

                    controlBlock.setBaseRegister(currentRamLoc);

                    int processEnd =currentDiskLoc + totSize;
                   //int processEnd = controlBlock.getInstructionLocationOnDisk()
                  // System.out.println(processEnd);
                   // while (currentDiskLoc<processEnd){
                   System.out.println("what is processEnd??? " + processEnd);
                   for (int i = currentDiskLoc; i < processEnd ;i++){//0  < 1024

                        String data = oSystem.getDisk().read(currentDiskLoc);
                        oSystem.getRam().writeToMem(currentRamLoc, data);


                        //Setting the waiting time for each process
                        //pcb.setStatus(READY);

                        currentDiskLoc++;
                        currentRamLoc++;
                    }
        System.out.println("this is index of Ram at 0:  " + oSystem.getRam().read(0));
               }

     //  }//catch (OutOfMemoryError e) {
       //    System.out.println("Out Of Memory ----Error");
      // }}







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
    }///////////////////////////////////////
}

     /*   OSystem os = OSystem.getInstance();
        ControlBlock current = OSystem.getInstance().getJobQueue().popTheQueue();
        OSystem.getInstance().getReadyQueue().push(current); // Moves to ready queue


        os.getRam().setUsedMem(0);                   // Set used/ counter to zero
        int totalSize = current.getProcessSize();
        int diskLocation = current.getInstructionLocationOnDisk();
        int ramLocation = os.getRam().getUsedMem();   // Needs Re-DESIGN!!!!


        // Throw error if not enough ram
        if (os.getRam().getSize() < totalSize) {
            throw new IllegalArgumentException("Not enough memory for the program... \n"
                    + "\t-RAM Used: " + os.getRam().getUsedMem() + "\n"
                    + "\t-RAM Free Space: " + os.getRam().getSize() + "\n"
                    + "\t-Program Size " + totalSize);
        }

        // Update PCB and to ready queue and move data
        current.setBaseRegister(ramLocation); // Sets base register from Ram startaddr

        int end = diskLocation + totalSize;
        while (diskLocation < 1024) {
            String data = os.getDisk().read(diskLocation);
            os.getRam().writeToMem(ramLocation, data);

            diskLocation++;
            ramLocation++;
        }

    }

    public void setRamtoDisk(ControlBlock current) {


        int totalSize = current.getProcessSize();
        int ramLocation = current.getBaseRegister();
        int diskLocation = current.getInstructionLocationOnDisk();   // Needs Re-DESIGN!!!!


        int programEnd = ramLocation + totalSize;
        while (ramLocation < programEnd) {
            String data = OSystem.getInstance().getRam().read(ramLocation);
            OSystem.getInstance().getDisk().overwriteToMem(diskLocation, data);

            diskLocation++;
            ramLocation++;
        }
    }
}*/

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
