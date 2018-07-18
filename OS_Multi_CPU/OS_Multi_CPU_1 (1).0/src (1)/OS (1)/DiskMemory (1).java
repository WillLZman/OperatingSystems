package OS;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
Come Back to later,will try to fix,
to many errors about indexing each spot in Disk,
fresh start in JointMemory
 */
// package OS;

/**
 * Created by wei on 2/8/2017.
 * This is the implementation of the Operating Systems disk.
 * It contains methods that can add and modify data in the disk
 */
/*
public class DiskMemory {

    private String  [] diskOfArray = new String[2048]; // the size of disk is 2048 words * 8 HEX characters/word = 16384
    private ControlBlock[] PCBList = new ControlBlock[30]; // Creates a list of PCB stored on Disk
    private int locationOnDisk = 0;// current position on disk
    private int NEW = 0;

    /**
     * Get the value of the disk
     * @return the char array of the disk
     */
/*
    public DiskMemory(ControlBlock[] PCBList){
        this.PCBList = PCBList;
    }
    public String [] getDiskArray(){
        return diskOfArray;
    }
    /**
     * Writes data onto the Disk
     * @param s a string of hex values that will be written to the disk
     */
/*    public void addToDisk(String s){
        // For example, 0xC050005C

        String hex = s.substring(2);// catch "C050005C"
        System.out.println("Running addToDisk: " + hex);
        for (int i = 0; i < hex.length(); i++){
            diskOfArray[locationOnDisk] = hex;
            locationOnDisk++;
        }
    }

    /**
     * Adds each process's PCB to PCB list on the disk
     * @param processControlBlock The process's PCB will be written to the PCB list
     */
/*    public void addPCBToPCBList(ControlBlock processControlBlock){
      /*  for (int i = 0; i < PCBList.length; i++){
            System.out.println("what is length:????" + PCBList.length);
            if (PCBList[i] == null){
                System.out.println("Weird call in addPCBToPCBList");
                PCBList[i] = processControlBlock;
                PCBList[i].setStatus(NEW);
            }
        }


    }*/

    /**
     * Just for testing
     * Displays the data of the disk
     */
/*    public void displayDisk(){
        for (int i = 0; i < diskOfArray.length; i++ ){
            if (i % 8 == 0){
                System.out.println();
            }
            System.out.print(diskOfArray[i] + " ");
        }
    }

    /**
     * get the corresponding PCB from the PCB list according to its ID
     * @param ID OF PCB
     * @return the specific PCB corresponding to the ID
     */
/*    public ControlBlock searchPCB(int ID) {
        for (int i = 0; i < PCBList.length; i++) {
            //if (PCBList[i].getProgramCounter() == ID) {
                return PCBList[i];
            //}
        }
        return null;// if can't find
    }

    /**
     * @return current location of the data on the disk
     */
/*    public int getPresentLocationOnDisk(){
        return locationOnDisk;
    }

    /**
     * Gets an array of all the processes on disk
     * @return The process array
     */
/*    public ControlBlock[] getPCBListOnDisk(){
        return PCBList;
    }

    public void setEachLocOnDisk(ControlBlock pcb) {

           /* for (ControlBlock pcb : PCBList) {
                int count = 0;
                int instructionStartLoc = pcb.getInstructionLocationOnDisk();//0
                int DataStartLoc = pcb.getDataLocationOnDisk();//
                int instructionSize = pcb.getCodeSize();//23
                int inputSize = pcb.getInputBuffer();
                int outputSize = pcb.getOutputBufferSize();
                int TempSize = pcb.getTempBufferSize();
                int JobEndLoc = DataStartLoc+instructionSize +inputSize+outputSize+TempSize;*/
/*        for (int i = pcb.getInstructionLocationOnDisk(); i < (pcb.getDataLocationOnDisk()+pcb.getCodeSize()+pcb.getInputBuffer()+pcb.getOutputBufferSize()+pcb.getTempBufferSize()); i+=8){
            if (i >= (pcb.getInstructionLocationOnDisk()-8) && i <= (pcb.getInstructionLocationOnDisk()+ pcb.getCodeSize() - 8)) {
                pcb.setJobLineLocList(i);
            }else if (i <= ((pcb.getDataLocationOnDisk()+pcb.getInputBuffer()+pcb.getOutputBufferSize()+pcb.getTempBufferSize()) - 8)){
                pcb.setDataLineLocList(i);
            }
        }

    }
}
*/

