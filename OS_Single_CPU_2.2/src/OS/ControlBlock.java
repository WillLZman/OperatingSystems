package OS;

import java.util.ArrayList;

/**
 * Created by wei on 2/22/2017.
 */
public class ControlBlock implements Comparable<ControlBlock>{

    private  int cpuID;               //information the assigned CPU (for multiprocessor
    public   int programID ;         //JOB ID
    public   int codeSize;                //JOB  size
    public   int priority;                    //JOB level

    public   int inputBuffer;                //DATA input buffer size
    public   int outputBufferSize;        //DATA  output buffer size
    public   int tempBufferSize;        //DATA  temporary buffer size
    private ArrayList<Integer> JobLineLocList = new ArrayList<>();
    private ArrayList<Integer> DataLineLocList = new ArrayList<>();
    private int sizeOfRAM;

    private int baseRegister;
    private int inputBufferSpace;
    private int outputBufferSpace;
    private int tempBufferSpace;

    private int physicalAddinRAM;
    private int physicalAddinRAMBegin;
    private int physicalAddinRAMEnd;

    private int programCounter =0;

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public ControlBlock(Integer ID, Integer codeSize, Integer priority) {
        programCounter = 0; //This is counter
        setProgramID(ID); //this is ID
        setCodeSize(codeSize);
        setPriority(priority);
    }


    public ArrayList<Integer> getDataLineLocList() {
        return DataLineLocList;
    }

    public void setDataLineLocList(int atDtataLine) {
        DataLineLocList.add(atDtataLine);
    }

    private int reg0; //accumlator
    private int reg1; //Zero Register
    private int reg2; // this is base-reg
    private int reg3; // this is S-reg1
    private int reg4;  // this is S-reg2
    private int reg5; // content of reg into address
    private int reg6;  // load address to this reg
    private int reg7;  // Used a reg in MOV from
    private int reg8;// this is D-reg
    private int reg9;   // Used a reg in MOV to
    private int reg10; //this is used for ops 0B onward  till 0F
    private int reg11; // end of RAM instruction --> go to LongTermSheduler
    private int reg12;
    private int reg13;
    private int reg14;
    private int reg15;


    private Integer instructionLocOnDisk; // the location of the instruction on disk
    private Integer dataLocOnDisk;// the location of the data on disk

    private Integer instructionLocOnMemory;
    private Integer dataLocOnMemory;// the location of the data on ram
    private Integer lastInstructionLocOnMemory;
    private boolean inMemory = false;
    private double PercenetageofRamUsed;

    //time measurement
    private int ioOperationCount = 0;
    private long timeExecutionStarted;
    private long timeExecutionFinished;
    private long timeAddedToReadyQueue;
    private long timeAddedToWaitQueue;

    private long processWaitTime;  // milli sec
    private long processCompletionTime; //milli sec
    private long processAvgTime;  // an average of the wait and completion time

    public void setPercenetageofRamUsed(double percenetageofRamUsed) {
        PercenetageofRamUsed = percenetageofRamUsed;
    }

    public double getPercenetageofRamUsed() {
        return PercenetageofRamUsed;
    }

    public long getProcessWaitTime() {
        return processWaitTime;
    }

    public void setProcessWaitTime(long processWaitTime) {
        this.processWaitTime = processWaitTime;
    }

    public long getProcessCompletionTime() {
        return processCompletionTime;
    }

    public void setProcessCompletionTime(long processCompleTime) {
        this.processCompletionTime = processCompleTime - OSystem.getInstance().getSystemStartTime();
        processAvgTime = (processCompletionTime + processWaitTime)/2;
    }

    public long getProcessAvgTime() {
        return processAvgTime;
    }


    private Long[] pcbState;

    protected Status status;
    private int NEW;
    private int WAIT;
    private int READY;
    private int TERMINATED;


    public ControlBlock() {
        this.cpuID = 0;
        this.programID = 0;
        this.codeSize = 0;
        this.priority = 0;
        this.inputBuffer = 0;
        this.outputBufferSize = 0;
        this.tempBufferSize = 0;
        this.NEW = 0;
        this.WAIT = 1;
        this.READY = 2;
        this.TERMINATED = 3;

        this.reg1 = 0;
        this.reg2 = 0;
        this.reg3 = 0;
        this.reg4 = 0;
        this.reg5 = 0;
        this.reg6 = 0;
        this.reg7 = 0;
        this.reg8 = 0;
        this.reg9 = 0;
        this.reg10 = 0;
        this.reg11 = 0;
        this.reg12 = 0;
        this.reg13 = 0;
        this.reg14 = 0;
        this.reg15 = 0;
        this.reg0 = 0;
    }

    public int getBaseRegister() {
        return baseRegister;
    }


   public void setBaseRegister(int baseRegister){
       this.baseRegister = baseRegister;
   }
   /*
   public void setBaseRegister(int baseRegister) {
       this.inMemory = true;
       this.baseRegister = baseRegister;
       this.inputBufferSpace = baseRegister + codeSize;
       this.outputBufferSpace = inputBufferSpace + inputBuffer;
       this.tempBufferSpace = outputBufferSpace + outputBufferSize;
   }*/
    public void increaseProgramCounter() {
        this.programCounter += 1;
    }
    public void decreaseProgramCounter(){
        this.programCounter -= 1;
    }
    public int getInputBufferSpace() {
        return baseRegister + codeSize;
    }

    public void setInputBufferSpace(int inputBufferSpace) {
        this.inputBufferSpace = inputBufferSpace;
    }

    public int getOutputBufferSpace() {
        return getInputBufferSpace()+inputBuffer;
    }

    public void setOutputBufferSpace(int outputBufferSpace) {
        this.outputBufferSpace = outputBufferSpace;
    }

    public int getTempBufferSpace() {
        return getOutputBufferSpace() + outputBufferSize;
    }

    public void setTempBufferSpace(int tempBufferSpace) {
        this.tempBufferSpace = tempBufferSpace;
    }


    public int getSizeOfRAM() {
        return sizeOfRAM;
    }

    public void setSizeOfRAM(int sizeOfRAM) {
        this.sizeOfRAM = sizeOfRAM;
    }
    public int getCpuID() {
        return cpuID;
    }

    public void setCpuID(int cpuID) {
        this.cpuID = cpuID;
    }

    public int getPhysicalAddinRAM() {
        return physicalAddinRAM;
    }

    public void setPhysicalAddinRAM(int physicalAddinRAM) {
        this.physicalAddinRAM = physicalAddinRAM;
    }

    public int getPhysicalAddinRAMBegin() {
        return physicalAddinRAMBegin;
    }

    public void setPhysicalAddinRAMBegin(int physicalAddinRAMBegin) {
        this.physicalAddinRAMBegin = physicalAddinRAMBegin;
    }

    public int getPhysicalAddinRAMEnd() {
        return physicalAddinRAMEnd;
    }

    public void setPhysicalAddinRAMEnd(int physicalAddinRAMEnd) {
        this.physicalAddinRAMEnd = physicalAddinRAMEnd;
    }

    public ArrayList<Integer> getJobLineLocList() {
        return JobLineLocList;
    }

    public void setJobLineLocList(int atJobLine) {
        JobLineLocList.add(atJobLine);
    }

    public Integer getProgramID(){
        System.out.println("getProgramID: " + programID);
        return programID;
    }

    public void setProgramID(Integer programID) {
        System.out.println("SETPC 123: " + programID);
        this.programID = programID; //this is original

    }
    public void setProgramID(int programID){ //this is extra AAAAA
        this.programID = programID;
    }


    public int getCodeSize() {
        return codeSize;
    }

    public void setCodeSize(int codeSize) {
        this.codeSize = codeSize;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getInputBuffer() {
        return inputBuffer;
    }

    public void setInputBuffer(int inputBuffer) {
        this.inputBuffer = inputBuffer;
    }

    public int getOutputBufferSize() {
        return outputBufferSize;
    }

    public void setOutputBufferSize(int outputBufferSize) {
        this.outputBufferSize = outputBufferSize;
    }

    public int getTempBufferSize() {
        return tempBufferSize;
    }

    public void setTempBufferSize(int tempBufferSize) {
        this.tempBufferSize = tempBufferSize;
    }

    public int getReg1() {
        return reg1;
    }

    public void setReg1(int reg1) {
        this.reg1 = reg1;
    }

    public int getReg2() {
        return reg2;
    }

    public void setReg2(int reg2) {
        this.reg2 = reg2;
    }

    public int getReg3() {
        return reg3;
    }

    public void setReg3(int reg3) {
        this.reg3 = reg3;
    }

    public int getReg4() {
        return reg4;
    }

    public void setReg4(int reg4) {
        this.reg4 = reg4;
    }

    public int getReg5() {
        return reg5;
    }

    public void setReg5(int reg5) {
        this.reg5 = reg5;
    }

    public int getReg6() {
        return reg6;
    }

    public void setReg6(int reg6) {
        this.reg6 = reg6;
    }

    public int getReg7() {
        return reg7;
    }

    public void setReg7(int reg7) {
        this.reg7 = reg7;
    }

    public int getReg8() {
        return reg8;
    }

    public void setReg8(int reg8) {
        this.reg8 = reg8;
    }

    public int getReg9() {
        return reg9;
    }

    public void setReg9(int reg9) {
        this.reg9 = reg9;
    }

    public int getReg10() {
        return reg10;
    }

    public void setReg10(int reg10) {
        this.reg10 = reg10;
    }

    public int getReg11() {
        return reg11;
    }

    public void setReg11(int reg11) {
        this.reg11 = reg11;
    }

    public int getReg12() {
        return reg12;
    }

    public void setReg12(int reg12) {
        this.reg12 = reg12;
    }

    public int getReg13() {
        return reg13;
    }

    public void setReg13(int reg13) {
        this.reg13 = reg13;
    }

    public int getReg14() {
        return reg14;
    }

    public void setReg14(int reg14) {
        this.reg14 = reg14;
    }

    public int getReg15() {
        return reg15;
    }

    public void setReg15(int reg15) {
        this.reg15 = reg15;
    }

    public int getReg0() {
        return reg0;
    }

    public void setReg0(int reg0) {
        this.reg0 = reg0;
    }
    public void setRegisters(int reg, int regNum){
        switch(regNum){
            case 1:
                this.reg1 = reg;
                break;
            case 2:
                this.reg2 = reg;
                break;
            case 3:
                this.reg3 = reg;
                break;
            case 4:
                this.reg4 = reg;
                break;
            case 5:
                this.reg5 = reg;
                break;
            case 6:
                this.reg6 = reg;
                break;
            case 7:
                this.reg7 = reg;
                break;
            case 8:
                this.reg8 = reg;
                break;
            case 9:
                this.reg9 = reg;
                break;
            case 10:
                this.reg10 = reg;
                break;
            case 11:
                this.reg11 = reg;
                break;
            case 12:
                this.reg12 = reg;
                break;
            case 13:
                this.reg13 = reg;
                break;
            case 14:
                this.reg14 = reg;
                break;
            case 15:
                this.reg15 = reg;
                break;
            case 16:
                this.reg0 = reg;
                break;
        }
    }
    /**
     * set current index as the start location to store INSTRUCTION on Disk
     * @param startInstructionIndex
     */
    public void setInstructionLocationOnDisk(int startInstructionIndex) {
        this.instructionLocOnDisk = startInstructionIndex;
    }

    public int getInstructionLocationOnDisk() {
        //System.out.println("Our Biggest Problem " + instructionLocOnDisk);
        return instructionLocOnDisk;
    }

    /**
     * set current index as the start location to store DATA on Disk
     * @param dataIndex
     */
    public void setDataLocationOnDisk(int dataIndex) {
        dataLocOnDisk = dataIndex;
    }

    public int getDataLocationOnDisk() {
        return dataLocOnDisk;
    }



    /**
     * set the index that starts storing INSTRUCTION on RAM
     * @param instructionLocationMemory
     */
    public void setInstructionLocationInMemory(int instructionLocationMemory) {
        instructionLocOnMemory = instructionLocationMemory;
    }

    /*public int getInstructionLocationInMemory() {
        return instructionLocOnMemory;
    }*/

    /**
     * get the index that at the end of the DATA location on RAM
     * @return
     */
    public int getDataLocationInMemory() {
        return dataLocOnMemory;
    }

    public void setDataLocationInMemory(int dataLocationInMemory) {
        dataLocOnMemory = dataLocationInMemory;
    }

    /**
     * get the index that at the end of INSTRUCTION location on RAM
     * @return
     */
    public int getLastInstructionLocationInMemory() {

        return lastInstructionLocOnMemory;
    }

    public void setLastInstructionLocationInMemory(int lastInstructionLocationInMemory) {
        this.lastInstructionLocOnMemory = lastInstructionLocationInMemory;
        System.out.println("TEST OF setLastInstruct " + lastInstructionLocationInMemory);
    }

    /**
     * the status to check if the PROCESS is already in the RAM
     * @param inmemory
     */
    public void setInMemory(boolean inmemory) {
        inMemory = inmemory;
    }

    public boolean isInMemory() {
        return inMemory;
    }

    /**
     * return the Process size, that is INSTRUCTION size + INPUT size + OUTPUT size + TEMP size
     * @return
     */
    public int getProcessSize() {
        return codeSize + inputBuffer + outputBufferSize + tempBufferSize;
    }

    //for counting io operations
    public void ioOperationMade() {
        ioOperationCount++;
    }

    public int getioOperationMade(){
        return ioOperationCount;
    }

    public void setExecutionFinishedTime() {
        if (timeExecutionFinished == 0) {
            timeExecutionFinished = System.currentTimeMillis();
        }
    }

    public void setExecutionStartingTime() {
        timeExecutionStarted = System.currentTimeMillis();
    }

    public void setInWaitingQueueTime() {
        timeAddedToWaitQueue = System.currentTimeMillis();
    }

    public void setInReadyQueueTime() {
        timeAddedToReadyQueue = System.currentTimeMillis();
    }

    //?
    public Long[] getPCBState() {
        return pcbState;
    }

    public void setPCBState(Long[] processState) {
        this.pcbState = processState;
    }

    public ControlBlock.Status getStatus(){
        return status;
    }

    protected enum Status {NEW, READY, RUNNING, WAITING, TERMINATED}

    public void setStatus(ControlBlock.Status status) {
        this.status = status;
    }

    @Override
    public int compareTo(ControlBlock pcb){
        return (this.getPriority() - pcb.getPriority());
    }
}



