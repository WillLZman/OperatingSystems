package OS;
//import OS.RamMemory;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by William Zimmerman on 2/7/2017.
 */

//**** Anything Marked MCPU = MulitCpu implementation
public class CPU extends threadAsymmetric { //need PCB for fetching the PC

    private String[] CPUCache = new String[300]; //MCPU
    private int cacheUsed;
    public char[] bits;
    //public String[] bits;
    //String OPcode;
    int OPcode;
    int Sreg1;
    int Sreg2;
    int Dreg;
    int Breg;
    private int reg1;
    private int reg2;
    public ControlBlock actualCB;
    private static int posInArray = 0;
    private int cpuID;
    int[] newReg;

    private boolean programEnd; // All process ended

    //private int address;  //16-24
    private String hexAddr;

    private int address;

    int pc;

    public boolean cpuDead = false;
    public boolean cpuIsAsleep = false; // only put asleep when waiting

    public boolean outOfProcesses = false;
    private boolean cpuNotavailabile = false; //turn off


    public CPU() {
        cpuID = posInArray;
        posInArray++;
        newReg = new int[16];
        newReg[1] = 0; //Zero register set

    }

    private int directAddress(int offSet) {
        return actualCB.getBaseRegister() + (offSet / 4);
    }

    private int indirectAddress(int regOffset, int offSet) {
        return actualCB.getBaseRegister() + ((regOffset + offSet) / 4);
    }

    private void JMPER() {
        // Call DMA for read method
        int location = directAddress(address);
        actualCB.setProgramID(location);
    }

    public ControlBlock getActualCB() {
        return actualCB;
    }

    public ControlBlock setActualCB() {
        return actualCB;
    }

    public boolean isCpuIsAsleep() {
        return cpuIsAsleep;
    }

    public void setCpuIsAlseep(boolean cpuIsAlseep) {
        this.cpuIsAsleep = cpuIsAlseep;
    }

    public boolean isCpuDead() {
        return cpuDead;
    }

    public boolean setcpuDead(boolean b) {
        return cpuDead = b;
    }


    @Override
    public void run() {
        //public void runCPUThread() throws InterruptedException {

        Thread.currentThread().setPriority(cpuID + 1);
        OSystem oSystem = OSystem.getInstance();
        /*
        refer to pg 211 in the book
         */
        while ((!Thread.currentThread().isInterrupted())) {
            try {
                while (cpuDead == true) {
                    if (oSystem.getReadyQueue().isEmpty() && oSystem.getJobQueue().isEmpty()) {
                        int numberOfThreads = Thread.activeCount();
                        System.out.println("Number of Threads: " + numberOfThreads);
                        System.out.println("Thread Number ID: " + Thread.currentThread().getId());

                        cpuNotavailabile = true;
                        end();
                        cpuDead = false;
                    } else {
                        Thread.sleep(20);
                    }
                }

                if (oSystem.getReadyQueue().isEmpty()) {
                    cpuDead = true;
                } else {
                    oSystem.getDispatcher().dispatchCPU(this);
                    ComputeOnly();
                }
            } catch (Exception e) {
                System.out.println("Error in runCPUThread");
            }
        }
    }

    public void Fetch() {
        OSystem oSystem = OSystem.getInstance();
        if (actualCB.getStatus() != ControlBlock.Status.RUNNING) {
            throw new IllegalArgumentException(" Job not ready for CPU");
        }
        int pc1 = actualCB.getProgramCounter();
        int bReg = actualCB.getBaseRegister();
        pc1 = pc;
        hexAddr = oSystem.getRam().read(pc1 + bReg);

    }


    public void Arithmetic(){
        OPcode = conversions.charToInt(Arrays.copyOfRange(bits,2,8));
        Sreg1 = conversions.charToInt(Arrays.copyOfRange(bits,8,12));
        Sreg2= conversions.charToInt(Arrays.copyOfRange(bits,12,16));
        address =conversions.charToInt(Arrays.copyOfRange(bits,16,32));
    }

    public void ConditionalImmediate(){
        OPcode = conversions.charToInt(Arrays.copyOfRange(bits,2,8));
        Breg = conversions.charToInt(Arrays.copyOfRange(bits,8,12));
        Dreg = conversions.charToInt(Arrays.copyOfRange(bits,12,16));
        address =conversions.charToInt(Arrays.copyOfRange(bits,16,32));
    }


    public void Unconditional(){
        OPcode = conversions.charToInt(Arrays.copyOfRange(bits,2,8));
        address =conversions.charToInt(Arrays.copyOfRange(bits,8,32));

    }
    public void InputOutput(){
        OPcode = conversions.charToInt(Arrays.copyOfRange(bits,2,8));
        reg1 = conversions.charToInt(Arrays.copyOfRange(bits,8,12));
        reg2 = conversions.charToInt(Arrays.copyOfRange(bits,12,16));
        address =conversions.charToInt(Arrays.copyOfRange(bits,16,32));

    }


    Conversions conversions = new Conversions();
    public void Decode(){
        //hexAddr =ramMemory.readValueFromAddress(actualCB.getProgramCounter()); //Read from Memory //possible error


        System.out.println("Hex Address: " + hexAddr );
        String binAddr = null;
        //String binAddr;
        //while (hexAddr != null){
        if (hexAddr!=null) {
            binAddr = conversions.HexToBinary(hexAddr);
        }

        System.out.println("Binary Address " + binAddr);
        char[] cBits = binAddr.toCharArray();
        bits = cBits;
        int ir = conversions.charToInt(Arrays.copyOfRange(cBits,0,2));

        //for (int i = 0; i < bits.length; i++) {

        //}
        switch(ir){
            //00
            case 0:
                Arithmetic();
                break;
            //01
            case 1:
                ConditionalImmediate();
                break;
            //10
            case 2:
                Unconditional();
                break;
            //11
            case 3:
                InputOutput();
                break;
        }
    }//return b;

    //int newReg;

    //for some registers in the CB we need to specify which is the InputBuffer,OutputBuffer
    //for this to work
    //writeValueToAddress() --> this is the setter for OS.CPU to change
    int loc;
    public void ReadIPtoAccumulator() throws Exception {

        //0000 0000
        //accumulator = Reg0
        if(OPcode==0){
            //newReg= setReg1(getInputBuffer(),));
            //actualCB.setReg0(actualCB.getInputBuffer());
            //actualCB.setRegisters(actualCB.getInputBuffer(),getReg0());
            // ramMemory.writeValueToAddress(Integer.toString(actualCB.getInputBuffer()), actualCB);
            loc = directAddress(newReg[reg2]);
           // System.out.println("LOC WhereRU? " + loc);

        }else {
            loc = directAddress(address);
            //System.out.println("WhatareLOC? " + loc);
        }
        newReg[reg1] = OSystem.getInstance().getDma().readDMA(actualCB, loc);
        System.out.println("ReadIPtoAccumulator");
    }

    public void AcculumatortoOP() throws Exception {
        //0000 0001
        if(OPcode==1){
            // newReg=setReg2(getReg1(),getReg2());
            //actualCB.setOutputBufferSize(actualCB.getReg0());
            // ramMemory.writeValueToAddress(Integer.toString(actualCB.getReg1()), actualCB);
            loc = directAddress(address);
            OSystem.getInstance().getDma().writeDMA(actualCB,loc,newReg[reg1]);
            System.out.println("AcculumatortoOP");
        }
    }
    public void StoreRegtoAdd() throws Exception {
        //0000 0010
        if(OPcode==2){
            //address = Integer.toString(actualCB.getReg5());
            //this.newReg = Integer.parseInt(address);
            loc = directAddress(newReg[Dreg]);
            OSystem.getInstance().getDma().writeDMA(actualCB,loc,newReg[Breg]);
            System.out.println("Issue at StoreRegtoAdd");
        }
    }
    public void LoadAddresstoReg() throws Exception {
        //0000 0011
        if(OPcode==3){
            //int addressString = Integer.parseInt(address);
            // actualCB.setReg6(addressString);
            //newReg = Integer.parseInt(address,2);
            loc = indirectAddress(newReg[Breg],address);
            newReg[Dreg] = OSystem.getInstance().getDma().readDMA(actualCB, loc);
            System.out.println("Issue LoadAddresstoReg");
        }
    }
    public void Transfer(){
        //0000 0100 MOV
        if(OPcode==4){
            //actualCB.setReg9(actualCB.getReg7());
            //newReg = this.newReg;
            newReg[Sreg1] = newReg[Sreg2];
            System.out.println("Issue Transfer");
        }
    }
    public void Add(){
        //0000 0101
        if(OPcode==5){
            int SregTemp1 = newReg[Sreg1];
            int SregTemp2 = newReg[Sreg2];
            int addition = SregTemp1 + SregTemp2;
            //actualCB.setReg8(addition);
            newReg[Dreg] = addition;
            System.out.println("Issue Add");
        }
    }
    public void Subtract(){
        //0000 0110
        if(OPcode==6){
            int SregTemp1 = newReg[Sreg1];
            int SregTemp2 = newReg[Sreg2];
            int subtraction = SregTemp1 - SregTemp2;
            //actualCB.setReg8(subtraction);
            newReg[Dreg] = subtraction;
            System.out.println("Issue Subtract");
        }
    }
    public void Multiply(){
        //0000 0111
        if(OPcode==7){
            int SregTemp1 = newReg[Sreg1];
            int SregTemp2 = newReg[Sreg2];
            int multiply = SregTemp1 * SregTemp2;
            //actualCB.setReg8(multiply);
            newReg[Dreg] =multiply;
            System.out.println("Issue Multiply");
        }
    }
    public void Divide(){
        //0000 1000
        if(OPcode==8){
            int SregTemp1 = newReg[Sreg1];
            int SregTemp2 = newReg[Sreg2];
            int divide = SregTemp1 / SregTemp2;
            //actualCB.setReg8(divide);
            newReg[Dreg] = divide;
            System.out.println("Issue Divide");
        }
    }
    public void AND(){
        //0000 1001
        if(OPcode==9){
            int SregTemp1 = newReg[Sreg1];
            int SregTemp2 = newReg[Sreg2];
            int AND = SregTemp1 & SregTemp2;
            //actualCB.setReg8(AND);
            newReg[Dreg] = AND;
            System.out.println("Issue AND");
        }
    }
    public void OR(){
        //0000 1010
        if(OPcode==10){
            int SregTemp1 = newReg[Sreg1];
            int SregTemp2 = newReg[Sreg2];
            int OR = SregTemp1 | SregTemp2;
            //actualCB.setReg8(OR);
            newReg[Dreg] = OR;
            System.out.println("Issue OR");
        }
    }
    public void TransferDirtoReg(){
        //0000 1011
        if(OPcode==11){
            //int addressString = Integer.parseInt(address);
            //  int dataString = actualCB.getInputBuffer();
            // actualCB.setReg10(addressString);
            //  actualCB.setReg10(dataString);
            newReg[Dreg] = address;
            System.out.println("Issue TransferDirtoReg");
        }
    }
    //need to specify what is data, im not exatcly sure
    public void AddDatatoReg(){
        //0000 1100
        if(OPcode==12){
            //int addressString = Integer.parseInt(address);
            // int dataString = actualCB.getInputBuffer();
            //actualCB.setReg10(actualCB.getReg10()+dataString);
            //newReg= data + newReg;
            newReg[Dreg] += address;
            System.out.println("Issue AddDatatoReg");
        }
    }
    public void MultiplyDatatoReg(){
        //0000 1101
        if(OPcode==13){
            //int dataString = actualCB.getInputBuffer();
            //actualCB.setReg10(actualCB.getReg10()*dataString);
            //newReg= data * newReg;
            newReg[Dreg] *= address;
            System.out.println(" Issue MultiplyDatatoReg");
        }
    }
    public void DivideDatatoReg(){
        //0000 1110
        if(OPcode==14){
            //newReg= data / newReg;
            //int dataString = actualCB.getInputBuffer();
            // actualCB.setReg10(actualCB.getReg10()/dataString);
            newReg[Dreg] = newReg[Dreg]/address;
            System.out.println(" Issue DivideDatatoReg");
        }
    }
    public void LoadDirReg(){
        //0000 1111
        if(OPcode==15){
            //int dataString = actualCB.getInputBuffer();
            //actualCB.setReg10(dataString);
            if (address > 0) {
                newReg[Dreg] = address;
            }else {
                newReg[Dreg] = newReg[Breg];
            }
            System.out.println("Issue LoadDirReg");
        }
    }
    public void SetDRegLessBReg(){
        //0001 0000
        System.out.println(" Issue SetDRegLessBReg");

        if(OPcode==16){
            //if(actualCB.getReg3()<actualCB.getReg2()){
            //   actualCB.setReg8(1);
            //}else {
            // actualCB.setReg8(0);
            //  }
            if (newReg[Sreg1]<newReg[Sreg2]){
                newReg[Dreg] = 1;
            }else {
                newReg[Dreg] = 0;
            }
        }
    }
    public void SetDRegLessData(){
        //0001 0001

        if(OPcode==17){
            //if(actualCB.getReg3()>actualCB.getReg10()){
            //   actualCB.setReg8(1);
            //}else {
            //    actualCB.setReg8(0);
            // }
            if (newReg[Sreg1]<address){
                newReg[Dreg] = 1;
            }else {
                newReg[Dreg] = 0;
            }
        }
        System.out.println("Issue SetDRegLessData");
    }
    public void End(){
        //0001 0010
        if(OPcode==18){
            System.out.println("Issue End");
            programEnd = true;
            // System.exit(1);
            //return;
        }
    }
    public void DoNothing(){
        //0001 0011
        if(OPcode==19){
            System.out.println("DoNothing");
        }
    }
    // not sure how to determine jump destination
    public void Jump(){
        //0001 0100
        if(OPcode==20){
            //pc = pc++;
            JMPER();
            System.out.println("Issue JMP");
        }
    }
    //unsure about branching
    public void BEQ(){
        //0001 0101
        if(OPcode==21){
            if(newReg[Breg] == newReg[Dreg]){
                JMPER();
                System.out.println("Issue BEQ");
            }
        }
    }
    public void BNE(){
        //0001 0110
        if(OPcode==22){
            if((newReg[Breg] != newReg[Dreg])){
                JMPER();
                System.out.println("Issue BNE");
            }
        }
    }
    public void BEZ(){
        //0001 0111
        if(OPcode==23){
            if(newReg[Breg] == 0){
                JMPER();
                System.out.println("Issue BEZ");
            }
        }
    }
    public void BNZ(){
        //0001 1000
        if(OPcode==24){
            if(newReg[Breg]!=0){
                JMPER();
                System.out.println("Issue BNZ");

            }
        }
    }
    public void BGZ(){
        //0001 1001
        if(OPcode==25){
            if(newReg[Breg]>0){
                JMPER();
                System.out.println("Issue BGZ");

            }
        }
    }
    public void BLZ(){
        //0001 1010
        if(OPcode==26){
            if(newReg[Breg]<0){
                JMPER();
                System.out.println("Issue BLZ");

            }
        }
    }
    public void Execute() throws Exception {


        //while (pc>0){
             /*
               loop
                   ir : = Fetch(memory[map(PC)]);// fetch hexAddr at RAM address – mapped PC
                   Decode(ir, oc, addrptr); // part of decoding of the hexAddr in instr reg (ir),
                   returning the opcode (oc) and a pointer to a list of
                   significant addresses in ‘ir’ – saved elsewhere
                   PC := PC + 1; // ready for next hexAddr, increase PC by 1 (word)
                   Execute(oc) {
                   case 0: // corresponding code using addrptr of operands
                   case 1: // corresponding code or send interrupt
                   …
               }
               end; // loop*/
        //pc++;
        switch (OPcode){
            case 0: //Reads content of I/P buffer into a accumulator
                ReadIPtoAccumulator();
                break;
            case 1: //Writes the content of accumulator into O/P buffer
                AcculumatortoOP();
                break;
            case 2: //Stores content of a reg.  into an address
                StoreRegtoAdd();
                break;
            case 3: //Loads the content of an address into a reg.
                LoadAddresstoReg();
                break;
            case 4: //Transfers the content of one register into another
                Transfer();
                break;
            case 5://Adds content of two S-regs into D-reg
                Add();
                break;
            case 6: //Subtracts content of two S-regs into D-reg
                Subtract();
                break;
            case 7: //Multiplies content of two S-regs into D-reg
                Multiply();
                break;
            case 8: //Divides content of two S-regs into D-reg
                Divide();
                break;
            case 9: //Logical AND of two S-regs into D-reg
                AND();
                break;
            case 10://Logical OR of two S-regs into D-reg
                OR();
                break;
            case 11://Transfers address/data directly into a register
                TransferDirtoReg();
                break;
            case 12://Adds a data value directly to the content of a register
                AddDatatoReg();
                break;
            case 13://Multiplies a data value directly with the content of a register
                MultiplyDatatoReg();
                break;
            case 14://Divides a data directly to the content of a register
                DivideDatatoReg();
                break;
            case 15://Loads a data/address directly to the content of a register
                LoadDirReg();
                break;
            case 16://Sets the D-reg to 1 if  first S-reg is less than the B-reg; 0 otherwise
                SetDRegLessBReg();
                break;
            case 17://Sets the D-reg to 1 if  first S-reg is less than a data; 0 otherwise
                SetDRegLessData();
                break;
            case 18://Logical end of program
                End();
                break;
            case 19://Does nothing and moves to next hexAddr
                break;
            case 20://Jumps to a specified location
                Jump();
                break;
            case 21://Branches to an address when content of B-reg = D-reg
                BEQ();
                break;
            case 22://Branches to an address when content of B-reg <> D-reg
                BNE();
                break;
            case 23://Branches to an address when content of B-reg = 0
                BEZ();
                break;
            case 24://Branches to an address when content of B-reg <> 0
                BNZ();
                break;
            case 25://Branches to an address when content of B-reg > 0
                BGZ();
                break;
            case 26://Branches to an address when content of B-reg < 0
                BLZ();
                break;

        }
        //System.out.println("DoAny>?");
        //pc--;
        //actualCB.decreaseProgramCounter();
    }
    //push process from ram to cache
    public void pushToCache(){
        synchronized (OSystem.getInstance().getRam()){
            int ramLoc = actualCB.getPhysicalAddinRAM();
            int totalSizeOfProcess = actualCB.getProcessSize();
            cacheUsed = 0;
            actualCB.setBaseRegister(cacheUsed);
            while (cacheUsed < totalSizeOfProcess){
                CPUCache[cacheUsed] =OSystem.getInstance().getRam().read(ramLoc);
                //OSystem.getInstance().getRam().writeToMem(ramLoc,CPUCache[i]);
                ramLoc++;
                cacheUsed++;
            }
        }
    }
    //push process from cache back to ram, as cache is finite will run out of room
    public void pushToRAMFromCache(){
        synchronized (OSystem.getInstance().getRam()){
            int ramLoc = actualCB.getPhysicalAddinRAM();
            int totalSizeOfProcess = actualCB.getProcessSize();
            for (int i = 0; i<cacheUsed; i++){
                OSystem.getInstance().getRam().writeToMem(ramLoc,CPUCache[i]);
                ramLoc++;
            }
        }
    }
    private void endOfProcess() {
        actualCB.setProcessCompletionTime(System.currentTimeMillis());
        actualCB.setPercenetageofRamUsed(OSystem.getInstance().getRam().percentageOfRAMspaceUsed());
        // Legal end of Program
        pushToCache();
        synchronized (OSystem.getInstance().getTerminatedQueue()){
            OSystem.getInstance().getTerminatedQueue().push(actualCB);
        }
        //OSystem.getInstance().getTerminatedQueue().push(actualCB);
        // end program and change PCB Status
        //OSystem.getInstance().getScheduler().setRamtoDisk(actualCB);
        if (OSystem.getInstance().getJobQueue().isEmpty() && OSystem.getInstance().getReadyQueue().isEmpty() ){
            cpuNotavailabile = true; // end a CPU
            cpuDead = true;

            System.out.println("-----------------------------");
            System.out.println("CPU " + cpuID + "is finished");
            System.out.println("Job Queue size: " + OSystem.getInstance().getJobQueue().size());
            System.out.println("Ready Queue size: " + OSystem.getInstance().getReadyQueue().size());
            System.out.println("Terminated Queue size " + OSystem.getInstance().getTerminatedQueue().size());
            System.out.println("-----------------------------");
        }
    }

    public void ComputeOnly(){
        try{
            programEnd = false;
            //actualCB = OSystem.getInstance().getDispatcher().getCurrentProcess(cpuID);
            synchronized (actualCB) {
                pushToRAMFromCache();
                long timeOfProcess;
                timeOfProcess = System.currentTimeMillis() - OSystem.getInstance().getSimStartTime();
                actualCB.setProcessWaitTime(timeOfProcess);
                while (programEnd == false) {
                    Fetch();
                    Decode();
                    actualCB.increaseProgramCounter();
                    Execute();
                }
            }
        }catch (Exception error){
            error.printStackTrace();
        }finally {
            endOfProcess();
            System.out.println("");
        }
    }
}


