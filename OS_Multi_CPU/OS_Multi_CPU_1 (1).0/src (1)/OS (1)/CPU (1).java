package OS;
//import OS.RamMemory;

/**
 * Created by William Zimmerman on 2/7/2017.
 */

//**** Anything Marked MCPU = MulitCpu implementation
public class CPU extends threadAsymmetric{ //need PCB for fetching the PC

    private String [] CPUCache =  new String[300]; //MCPU
    private int cacheUsed;

    public String[] bits;
    String OPcode;
    int Sreg1;
    int Sreg2;
    int Dreg;
    int Breg;
    private int reg1;
    private int reg2;
    public ControlBlock actualCB;
    private static int posInArray = 0;
    private  int cpuID;
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

    private int effectiveAddress(int offSet) {
        return actualCB.getBaseRegister() + (offSet / 4);
    }

    private int indirectAddress(int regOffset, int offSet) {
        return actualCB.getBaseRegister() + ((regOffset + offSet) / 4);
    }
    private void JMPER() {
        // Call DMA for read method
        int location = effectiveAddress(address);
        actualCB.setProgramCounter(location);
    }
    public ControlBlock getActualCB(){
        return actualCB;
    }
    public ControlBlock setActualCB(){
        return actualCB;
    }
    public boolean isCpuIsAsleep() {
        return cpuIsAsleep;
    }

    public void setCpuIsAlseep(boolean cpuIsAlseep) {
        this.cpuIsAsleep = cpuIsAlseep;
    }

    public boolean isCpuDead(){
        return cpuDead;
    }

    public boolean setcpuDead(boolean b){
        return cpuDead = b;
    }


    @Override
    public void run(){
    //public void runCPUThread() throws InterruptedException {

        Thread.currentThread().setPriority(cpuID + 1);
        OSystem oSystem = OSystem.getInstance();
        /*
        refer to pg 211 in the book
         */
        while ((!Thread.currentThread().isInterrupted())){
            try {
                while (cpuDead == true){
                    if (oSystem.getReadyQueue().isEmpty() && oSystem.getJobQueue().isEmpty()){
                        int numberOfThreads = Thread.activeCount();
                        System.out.println("Number of Threads: " + numberOfThreads);
                        System.out.println("Thread Number ID: " + Thread.currentThread().getId());

                        cpuNotavailabile = true;
                        end();
                        cpuDead = false;
                    }else {
                        Thread.sleep(20);
                    }
                }

                if (oSystem.getReadyQueue().isEmpty()){
                    cpuDead = true;
                }else {
                    oSystem.getDispatcher().dispatchCPU(this);
                    ComputeOnly();
                }
            }catch (Exception e){
                System.out.println("Error in runCPUThread");
            }
        }
    }
    public void Fetch() {
        OSystem oSystem = OSystem.getInstance();
        if (actualCB.getStatus() != ControlBlock.Status.RUNNING) {
            throw new IllegalArgumentException(" Job not ready for CPU");
        }
        int pc = actualCB.getProgramCounter();
        int bReg= actualCB.getBaseRegister();
        hexAddr = oSystem.getRam().read(pc -1 + bReg);
        if (hexAddr==null){
            hexAddr="00000000";
        }

    }


    public void Arithmetic(){
        for(int i = 0; i < 2; i++){
            if (( bits[0].equals("0")) && ( bits[1].equals("0"))){
                String codeString = "";
                for(int i1 = 2; i1 <8; i1++){
                    codeString = codeString + bits[i1];
                }
                this.OPcode = codeString;
                codeString = "";
                for(int i2 = 8; i2 <12; i2++){
                    codeString = codeString + bits[i2];

                    Sreg1=Integer.parseInt(codeString);
                    actualCB.setReg3(Sreg1);
                }
                //this.Sreg1 = codeString;
                codeString = "";
                for(int i3 = 12; i3 < 16; i3++){
                    codeString = codeString + bits[i3];

                    Sreg2=Integer.parseInt(codeString);
                    actualCB.setReg4(Sreg2);
                }
                //this.Sreg2 = codeString;
                codeString = "";
                for(int i4 = 16; i4 < 20; i4++){
                    codeString = codeString + bits[i4];

                    Dreg=Integer.parseInt(codeString);
                    actualCB.setReg8(Dreg);
                }
                //this.Dreg = codeString;
                codeString = "";
                for(int i5 = 20; i5 < 32; i5++){
                    codeString = codeString + bits[i5];

                    address = Integer.parseInt(codeString);
                }

            }
        }
    }
    public void ConditionalImmediate(){
        for(int i = 0; i < 2; i++){
            if (( bits[0].equals("0")) && ( bits[1].equals("1"))){
                String codeString = "";
                for(int i1 = 2; i1 < 8; i1++){
                    codeString = codeString + bits[i1];
                }
                this.OPcode = codeString;
                codeString = "";
                for(int i2 = 8; i2 < 12; i2++){
                    codeString = codeString + bits[i2];

                    Breg=Integer.parseInt(codeString);
                    actualCB.setReg2(Breg);
                }
                //this.Breg = codeString;
                codeString = "";
                for(int i3 = 12; i3 < 16; i3++){
                    codeString = codeString + bits[i3];
                    Dreg=Integer.parseInt(codeString);
                    actualCB.setReg8(Dreg);
                }
                //this.Dreg = codeString;
                codeString = "";
                for(int i4 = 16; i4 < 32; i4++){
                    codeString = codeString + bits[i4];
                    address = Integer.parseInt(codeString);
                }

            }
        }
    }
    public void Unconditional(){
        for(int i = 0; i < 2; i++){
            if (( bits[0].equals("1")) && ( bits[1].equals("0"))){
                String codeString = "";
                for(int i1 = 2; i1 < 8; i1++){
                    codeString = codeString + bits[i1];
                }
                this.OPcode = codeString;
                codeString = "";
                for(int i2 = 8; i2 < 24; i2++){
                    codeString = codeString + bits[i2];
                    address = Integer.parseInt(codeString);
                }

            }
        }
    }
    public void InputOutput(){
        for(int i = 0; i < 2; i++){
            if (( bits[0].equals("1")) && ( bits[1].equals("1"))){
                String codeString = "";
                for(int i1 = 2; i1 < 8; i1++){
                    codeString = codeString + bits[i1];
                }
                this.OPcode = codeString;
                for(int i2 = 8; i2 < 12; i2++){
                    codeString = codeString + bits[i2];
                    reg1 = Integer.parseInt(codeString);
                   // setRegisters(Integer.parseInt(bits[i2]), 1);
                }
                for(int i3 = 12; i3 < 16; i3++){
                    codeString = codeString + bits[i3];
                    reg2 = Integer.parseInt(codeString);
                    //setRegisters(Integer.parseInt(bits[i3]), 2);
                }
                for(int i4 = 16; i4 < 32; i4++){
                    codeString = codeString + bits[i4];
                    address = Integer.parseInt(codeString);
                }

                codeString="";
            }
        }
    }


    public String Decode(){
        //hexAddr =ramMemory.readValueFromAddress(actualCB.getProgramCounter()); //Read from Memory //possible error

        Conversions conversions = null;
        System.out.println("hexAddr: " + hexAddr );
        String binAddr;
        binAddr=conversions.HexToBinary(hexAddr);

        char[] cBits = binAddr.toCharArray();
        this.bits = new String[cBits.length];
        String[] bits2 = new String[cBits.length/2];
        String b = "";
        bits = new String[32];
        for (int i = 0; i < 32; i++) {
            bits[i] = Character.toString(cBits[i]);
        }
        for (int j = 0; j < 1; j++) {
            bits2[j] = Character.toString(cBits[j]);
            bits2[j+1] = Character.toString(cBits[j+1]);
            b = bits2[j] + bits2[j+1];
            System.out.println("b is: " + b);
        }

        //for (int i = 0; i < bits.length; i++) {

        //}
        switch(b){
            case "00":
                Arithmetic();
                break;
            case "01":
                ConditionalImmediate();
                break;
            case "10":
                Unconditional();
                break;
            case "11":
                InputOutput();
                break;
        }
        return b;}
    //int newReg;

    //for some registers in the CB we need to specify which is the InputBuffer,OutputBuffer
    //for this to work
    //writeValueToAddress() --> this is the setter for OS.CPU to change
    int loc;
    public void ReadIPtoAccumulator() throws Exception {

        //0000 0000
        //accumulator = Reg0
        if(OPcode.equals("000000")){
            //newReg= setReg1(getInputBuffer(),));
            //actualCB.setReg0(actualCB.getInputBuffer());
            //actualCB.setRegisters(actualCB.getInputBuffer(),getReg0());
            // ramMemory.writeValueToAddress(Integer.toString(actualCB.getInputBuffer()), actualCB);
            loc = effectiveAddress(newReg[reg2]);

        }else {
            loc = effectiveAddress(address);
        }
        newReg[reg1] = OSystem.getInstance().getDma().readDMA(actualCB, loc);
        System.out.println("ReadIPtoAccumulator");
    }

    public void AcculumatortoOP() throws Exception {
        //0000 0001
        if(OPcode.equals("000001")){
            // newReg=setReg2(getReg1(),getReg2());
            //actualCB.setOutputBufferSize(actualCB.getReg0());
            // ramMemory.writeValueToAddress(Integer.toString(actualCB.getReg1()), actualCB);
            loc = effectiveAddress(address);
            OSystem.getInstance().getDma().writeDMA(actualCB,loc,newReg[reg1]);
            System.out.println("AcculumatortoOP");
        }
    }
    public void StoreRegtoAdd() throws Exception {
        //0000 0010
        if(OPcode .equals("000010")){
            //address = Integer.toString(actualCB.getReg5());
            //this.newReg = Integer.parseInt(address);
            loc = effectiveAddress(newReg[Dreg]);
            OSystem.getInstance().getDma().writeDMA(actualCB,loc,newReg[Breg]);
            System.out.println("Issue at StoreRegtoAdd");
        }
    }
    public void LoadAddresstoReg() throws Exception {
        //0000 0011
        if(OPcode.equals("000011")){
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
        if(OPcode.equals("000100")){
            //actualCB.setReg9(actualCB.getReg7());
            //newReg = this.newReg;
            newReg[Sreg1] = newReg[Sreg2];
            System.out.println("Issue Transfer");
        }
    }
    public void Add(){
        //0000 0101
        if(OPcode.equals("000101")){
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
        if(OPcode.equals ("000110")){
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
        if(OPcode.equals ("000111")){
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
        if(OPcode.equals("001000")){
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
        if(OPcode.equals("001001")){
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
        if(OPcode .equals("001010")){
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
        if(OPcode.equals ("001011")){
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
        if(OPcode.equals ("001100")){
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
        if(OPcode .equals ("001101")){
            //int dataString = actualCB.getInputBuffer();
            //actualCB.setReg10(actualCB.getReg10()*dataString);
            //newReg= data * newReg;
            newReg[Dreg] *= address;
            System.out.println(" Issue MultiplyDatatoReg");
        }
    }
    public void DivideDatatoReg(){
        //0000 1110
        if(OPcode .equals ("001110")){
            //newReg= data / newReg;
            //int dataString = actualCB.getInputBuffer();
            // actualCB.setReg10(actualCB.getReg10()/dataString);
            newReg[Dreg] = newReg[Dreg]/address;
            System.out.println(" Issue DivideDatatoReg");
        }
    }
    public void LoadDirReg(){
        //0000 1111
        if(OPcode .equals ("001111")){
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

        if(OPcode .equals ("010000")){
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

        if(OPcode .equals ("010001")){
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
    public void end(){
        //0001 0010
        if(OPcode .equals("010010")){
            System.out.println("Issue end");
            programEnd = true;
            // System.exit(1);
            //return;
        }
    }
    public void DoNothing(){
        //0001 0011
        if(OPcode.equals ("010011")){
            System.out.println("DoNothing");
        }
    }
    // not sure how to determine jump destination
    public void Jump(){
        //0001 0100
        if(OPcode.equals ("010100")){
            //pc = pc++;
            JMPER();
            System.out.println("Issue JMP");
        }
    }
    //unsure about branching
    public void BEQ(){
        //0001 0101
        if(OPcode .equals("010101")){
            if(newReg[Breg] == newReg[Dreg]){
                JMPER();
                System.out.println("Issue BEQ");
            }
        }
    }
    public void BNE(){
        //0001 0110
        if(OPcode .equals ("010110")){
            if((newReg[Breg] != newReg[Dreg])){
                JMPER();
                System.out.println("Issue BNE");
            }
        }
    }
    public void BEZ(){
        //0001 0111
        if(OPcode .equals ("010111")){
            if(newReg[Breg] == 0){
                JMPER();
                System.out.println("Issue BEZ");
            }
        }
    }
    public void BNZ(){
        //0001 1000
        if(OPcode.equals ("011000")){
            if(newReg[Breg]!=0){
                JMPER();
                System.out.println("Issue BNZ");

            }
        }
    }
    public void BGZ(){
        //0001 1001
        if(OPcode.equals("011001")){
            if(newReg[Breg]>0){
                JMPER();
                System.out.println("Issue BGZ");

            }
        }
    }
    public void BLZ(){
        //0001 1010
        if(OPcode.equals("011010")){
            if(newReg[Breg]<0){
                JMPER();
                System.out.println("Issue BLZ");

            }
        }
    }
    public void Execute() throws Exception {


        while (pc>0){
            System.out.println("fghgfs");

            System.out.println("DSDSADDA");
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
            pc++;
            System.out.println("HI");
            switch (OPcode){
                case "000000": //Reads content of I/P buffer into a accumulator
                    ReadIPtoAccumulator();
                    break;
                case "000001": //Writes the content of accumulator into O/P buffer
                    AcculumatortoOP();
                    break;
                case "000010": //Stores content of a reg.  into an address
                    StoreRegtoAdd();
                    break;
                case "000011": //Loads the content of an address into a reg.
                    LoadAddresstoReg();
                    break;
                case "000100": //Transfers the content of one register into another
                    Transfer();
                    break;
                case "000101"://Adds content of two S-regs into D-reg
                    Add();
                    break;
                case "000110": //Subtracts content of two S-regs into D-reg
                    Subtract();
                    break;
                case "000111": //Multiplies content of two S-regs into D-reg
                    Multiply();
                    break;
                case "001000": //Divides content of two S-regs into D-reg
                    Divide();
                    break;
                case "001001": //Logical AND of two S-regs into D-reg
                    AND();
                    break;
                case "001010"://Logical OR of two S-regs into D-reg
                    OR();
                    break;
                case "001011"://Transfers address/data directly into a register
                    TransferDirtoReg();
                    break;
                case "001100"://Adds a data value directly to the content of a register
                    AddDatatoReg();
                    break;
                case "001101"://Multiplies a data value directly with the content of a register
                    MultiplyDatatoReg();
                    break;
                case "001110"://Divides a data directly to the content of a register
                    DivideDatatoReg();
                    break;
                case "001111"://Loads a data/address directly to the content of a register
                    LoadDirReg();
                    break;
                case "010000"://Sets the D-reg to 1 if  first S-reg is less than the B-reg; 0 otherwise
                    SetDRegLessBReg();
                    break;
                case "010001"://Sets the D-reg to 1 if  first S-reg is less than a data; 0 otherwise
                    SetDRegLessData();
                    break;
                case "010010"://Logical end of program
                    end();
                    break;
                case "010011"://Does nothing and moves to next hexAddr
                    continue;
                case "010100"://Jumps to a specified location
                    Jump();
                    break;
                case "010101"://Branches to an address when content of B-reg = D-reg
                    BEQ();
                    break;
                case "010110"://Branches to an address when content of B-reg <> D-reg
                    BNE();
                    break;
                case "010111"://Branches to an address when content of B-reg = 0
                    BEZ();
                    break;
                case "011000"://Branches to an address when content of B-reg <> 0
                    BNZ();
                    break;
                case "011001"://Branches to an address when content of B-reg > 0
                    BGZ();
                    break;
                case "011010"://Branches to an address when content of B-reg < 0
                    BLZ();
                    break;

            }
            System.out.println("DoAny>?");
            pc--;
        }
        //for (int i = 0; i < CPUCache.length;i++){
        //    CPUCache[i] = hexAddr;
        //}


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
                timeOfProcess = System.currentTimeMillis() - OSystem.getInstance().getSystemStartTime();
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


