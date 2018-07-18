package OS;

import java.util.ArrayList;

/**
 * Created by wei on 2/22/2017.
 */
public class ControlBlock implements Comparable<ControlBlock>{
    private int cpuID;               //information the assigned CPU (for multiprocessor
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
        //Takes string of HEX
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
        this.processCompletionTime = processCompleTime - OSystem.getInstance().getSimStartTime();
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

    public void setBaseRegister(int baseRegister) {
        this.inMemory = true;
        this.baseRegister = baseRegister;
        this.inputBufferSpace = getInputBufferSpace();
        this.outputBufferSpace = getOutputBufferSpace();
        this.tempBufferSpace = getTempBufferSpace();
    }
    public void increaseProgramCounter() {
        this.programID += 1;
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
        return programID;
    }

    public void setProgramID(int programID) {
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


    /**
     * set current index as the start location to store INSTRUCTION on Disk
     * @param startInstructionIndex
     */
    public void setInstructionLocationOnDisk(int startInstructionIndex) {
        instructionLocOnDisk = startInstructionIndex;
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
        this.dataLocOnDisk = dataIndex;
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



