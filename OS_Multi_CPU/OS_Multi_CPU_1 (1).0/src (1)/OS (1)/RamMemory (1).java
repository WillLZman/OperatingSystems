package OS;/*
Come Back to later,will try to fix,
to many errors about indexing each spot in Ram,
fresh start in JointMemory



package OS;
/**
 * Created by wei on 2/6/2017.
 * This is the implementation of the Operating System's memory
 * It contains methods that can add and modify data in the memory
 */

/*
public class RamMemory {

    private String[] ramOfArray = new String[1024];// 1024 * 8 = 8192
    private ControlBlock[] PCBList2 = new ControlBlock[30]; // list for ram
    private int locationOnRAM = 0;// current position on RAM

    /**
     * Get the value of the RAM
     * @return the char array of the RAM
     */
/*
    public String [] getRamArray(){
        return ramOfArray;
    }

    /**
     * load instruction and data set of PCB from disk into memory
     * @param PCBList2 ..
     */
/*
    public RamMemory(ControlBlock[] PCBList2){
        this.PCBList2 = PCBList2;
    }
    public void addPCBToRamByID(ControlBlock PCBToAdd, DiskMemory disk){
        System.out.println("InitialaddPCB");
       // ControlBlock PCBToAdd = disk.searchPCB(ID);
        locationOnRAM = searchSpaceForPCB(PCBToAdd);// the empty space that can be used to store the data
        System.out.println("TestbeforeInstructuinset");
        System.out.println("LOCATIONONRAM?????" + locationOnRAM);
       // PCBToAdd.setInstructionLocationInMemory(locationOnRAM);// record the beginning index of the instruction to PCB

        //System.out.println("instruction begins on Disk: " + PCBToAdd.getInstructionLocationOnDisk());
       // System.out.println("instruction ends on Disk: " + (PCBToAdd.getInstructionLocationOnDisk() +PCBToAdd.getCodeSize()) );
        //System.out.println("data starts on Disk: " + PCBToAdd.getDataLocationOnDisk());
       // System.out.println("data ends on Disk: " + (PCBToAdd.getDataLocationOnDisk() + PCBToAdd.getInputBuffer() + PCBToAdd.getOutputBufferSize() + PCBToAdd.getTempBufferSize()));

       // PCBToAdd.setLastInstructionLocationInMemory(locationOnRAM + PCBToAdd.getCodeSize());//record the last index of the instruction to PCB
       // PCBToAdd.setDataLocationInMemory(locationOnRAM + PCBToAdd.getDataLocationOnDisk());// start location of storing data

        /**
         * setInMemory(): set the condition to true.
         */
/*
        PCBToAdd.setInMemory(true);

        String[] thisDisk = disk.getDiskArray();
        int count = 0;
        for(int i = PCBToAdd.getPhysicalAddinRAMBegin(); i < PCBToAdd.getPhysicalAddinRAMEnd(); i++){

            ramOfArray[locationOnRAM++] = thisDisk[i];// store to the available space in RAM(current location in RAM)if (count % 8 = 0){

                System.out.println("TestoframMem" + count);

            count++;
        }
       // addPCBToPCBList(PCBToAdd);
    }


    /**
     * Adds the Process to the PCB List in Ram
     * @param pcb The process we are adding to the list
     */
/*
    public void addPCBToPCBList(ControlBlock pcb){
        /*int i = 0;
        while(i < PCBList2.length){
            if (PCBList2[i] == null) {
                PCBList2[i] = pcb;
                return;
            }
            i++;
        }

    }*/

    /**
     * Fetches the array of PCBs on Ram
     * @return an array of PCBs
     */
    /*
    public ControlBlock[] getPCBListOnRam(){
        return PCBList2;
    }

    /**
     * Fetches the process by ID specified
     * @param ID the ID of the process we want to fetch
     * @return the PCB of the process with the specified ID
     */
/*
    public ControlBlock getPCBByID(int ID){
        int i = 0;
        while(i < PCBList2.length) {
            if (PCBList2[i] != null) {
                if (PCBList2[i].getProgramCounter() == ID) {
                    return PCBList2[i];
                }
            }
            i++;
        }
        return null;
    }

    public int getPresentLocationInMemory(){
        return locationOnRAM;
    }

    /**
     * Writes a value to an address
     * @param value The value to write
     * @param p
     */
  /*
    public void writeValueToAddress(String value){
        int startAddress = p.getInstructionLocationInMemory();


        for (int i = 0; i < value.length(); i++) {
            ramOfArray[startAddress + i] = value.charAt(i);
        }
        p.ioOperationMade();
    }

    /**
     * Reads a value from an address
     * @param programCounter the selected PCB
     * @return The value stored in the address
     */
  /*
    public String readValueFromAddress(Integer programCounter){
        int startAddress = PCBList2[programCounter-1].getPhysicalAddinRAMBegin();
        //int lengthOfProcess = ProcessSize;
        String val = "";
        for (int i = startAddress; i < startAddress + 8; i++) {
            val += ramOfArray[i];//C050005C

        }
        System.out.println(val);
        PCBList2[programCounter].ioOperationMade();
        return val;
    }

    /**
     * Removes a process from the RAM with the specified ID
     * @param ID the ID of the process we wish to remove
     */
  /*
    public void removePCBFromMemory(Integer ID){
        ControlBlock removedPCB = PCBList2[ID];
        removedPCB.setInMemory(false);// set the false statue to the removed PCB in RAM

        int locationBegin = removedPCB.getPhysicalAddinRAMBegin();
        int locationEnd = removedPCB.getProcessSize();

        /**
         * reset the interval of the removed PCB in RAM
         */
 /*
        for (int i = locationBegin; i < locationEnd; i++)
        {
            ramOfArray[i].equals(null); // '\u0000' = unicode for char null
        }
        /**
         * reset the value on the index that holds the removed PCB
         */
  /*      int p = 0;
        while(p < PCBList2.length) {
            if (PCBList2[p] != null)
                if (PCBList2[p].getProgramCounter() == ID) {
                    PCBList2[p] = null;
                    break;
                }
        }

    }

    /**
     * Finds an empty space in Memory where the Process specified will fit. This method throws an
     * out of memory exception when no spaces are found.
     * @param pcb The Process that we need to find a space for.
     * @return an int of the start location where the process will fit
     */
/*    public int searchSpaceForPCB(ControlBlock pcb){
        int beginLocation = 0;
        int availableLocation = 0;
        /**
         * getProcessSize():  instructionSize + inputBuffer size
         *  + outputBuffer size + temporaryBuffer size
         */
 /*       int PCBSize = pcb.getProcessSize();

        for (int i = 0; i < ramOfArray.length; i++){
            if (ramOfArray[i] .equals(null)) { // check if the position holds null character
                availableLocation++;
                if (availableLocation >= PCBSize){
                    return beginLocation;
                }
            }
            else
            {
                beginLocation = i + 1;
                availableLocation = 0;
            }
        }
        throw new OutOfMemoryError();
    }

    /**
     * check if there is enough space to store a given process into memory
     * @param pcb The process to check for
     * @return Whether there is space for it
     */
 /*   public boolean hasSpaceForProcess(ControlBlock pcb){
        try {
            searchSpaceForPCB(pcb);
            System.out.println("Ram has enough memory");
            return true;
        } catch (OutOfMemoryError e) {
            System.out.println("Out Of Memory ----Error");
            return false;
        }
    }

    /**
     * Just for testing
     * Displays the data of the RAM
     */
 /*   public void displayRAM(){
        for (int i = 0; i < ramOfArray.length; i++) {
            if (i % 8 == 0) {
                System.out.println();
            }
            System.out.print(ramOfArray[i]);
        }
    }
    /**
     * Displays process list in memory
     */
  /*  public void printPCBList(){
        for (int i = 0; i < PCBList2.length; i++)
        {
            if (PCBList2[i] != null)
            {
                System.out.println("Process ID: " + PCBList2[i].getProgramCounter());
                System.out.println("Priority: " + PCBList2[i].getPriority());
               // System.out.println("Instruction Start: " + PCBList2[i].getInstructionLocationInMemory());
                System.out.println("InputBuffer Size: " + PCBList2[i].getInputBuffer());
                System.out.println("OutputBuffer Size: " + PCBList2[i].getOutputBufferSize());
                System.out.println("TempBuffer Size: " + PCBList2[i].getTempBufferSize());
                System.out.println("Process Size: " + PCBList2[i].getProcessSize());
                System.out.println("Last Instruction Location: " + PCBList2[i].getLastInstructionLocationInMemory());
                System.out.println();
            }
        }
    }

    public ControlBlock[] getPCBListFromRAM() {
        return PCBList2;
    }

    public void setPCBList2(ControlBlock[] PCBList2) {
        this.PCBList2 = PCBList2;
    }

}
  */