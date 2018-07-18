package OS;

/**
 * Created by William Zimmerman on 3/9/2017.
 */

public class OSystem {
    private static OSystem instance = new OSystem();
    private CPU[] cpu = new CPU[4];
   // private RamMemory ram;
    private JointMemory ram;
    private Loader loader;
    private Scheduler scheduler;
    private Dispatcher dispatcher;
   // private DiskMemory disk;
    private JointMemory disk;
    private QQueue jobQueue;
    private QQueue readyQueue;
    //  Running QQueue moved to dispatcher
    //private QQueue runningQueue;
    private QQueue waitingQueue;
    private QQueue terminatedQueue;
    private DMA dma;
    private long systemStartTime;

    private boolean isRunning; // needed for MCPU to identify if a CPU is running or not, maybe turn off, wake, and run CPUs also?

    public OSystem(){
        for (int i =0; i<4; i++) {
            cpu[i] = new CPU();
        }

        ram = new JointMemory(1024);
        loader = new Loader();
        scheduler = new Scheduler();
        dispatcher = new Dispatcher();

        disk = new JointMemory(2048);
        jobQueue = new QQueue(ControlBlock.Status.NEW);
        readyQueue = new QQueue(ControlBlock.Status.READY);
      //  runningQueue = new QQueue(ControlBlock.Status.RUNNING);
        waitingQueue = new QQueue(ControlBlock.Status.WAITING);
        terminatedQueue = new QQueue(ControlBlock.Status.TERMINATED);
         dma = new DMA();
    }

    //public QQueue getRunningQueue() {
    //    return runningQueue;
   // }

   // public void setRunningQueue(QQueue runningQueue) {
  //      this.runningQueue = runningQueue;
  //  }


    public static OSystem getInstance() {
        return instance;
    }

    public void start() throws InterruptedException {
        setRunning(true);
        for (int i = 0; i<cpu.length; i++){
            cpu[i].start();
            cpu[i].setAvailiability(true);
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean sleepAllCPUs(){
        for (int i = 0; i<cpu.length; i++){
            if (cpu[i].isCpuIsAsleep() == false){
                return false;
            }//else {

            //}
        }  return true;
    }

    public void wakeUpCallAllCPUs(){
        for (int i = 0; i<cpu.length; i++){
            cpu[i].setCpuIsAlseep(false);
        }
    }

    public boolean endCPUs(){
        for (int i = 0; i<cpu.length; i++){
            if (cpu[i].isCpuDead() == false){
                return false;
            }

        }return true;
    }
    public void setRunning(boolean running) {
        isRunning = running;
    }

    public CPU[] getCpu() {
        return cpu;
    }

    public JointMemory getRam() {
        return ram;
    }

    public Loader getLoader() {
        return loader;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public JointMemory getDisk() {
        return disk;
    }

    public QQueue getJobQueue() {
        return jobQueue;
    }

    public QQueue getReadyQueue() {
        return readyQueue;
    }

    public QQueue getWaitingQueue() {
        return waitingQueue;
    }

    public QQueue getTerminatedQueue() {
        return terminatedQueue;
    }

    public DMA getDma() {
        return dma;
    }

    public long getSystemStartTime() {
        return systemStartTime;
    }
    public void StartTheTimer(){
        this.systemStartTime = System.currentTimeMillis();
    }
}
/*
  PCB[] runningQueue;

    public void cpuThreads(int numberOfCPUs) {
        runningQueue = new PCB[numberOfCPUs];
    }

    public void dispatchCPU() {  // First in first out
        PCB currentJob = OperatingSystem.getInstance().getReadyQueue().popTheQueue();
        runningQueue[0] = currentJob;
        currentJob.setStatus(PCB.Status.RUNNING);
    }

    protected PCB getCurrentJob(int cpuID) {
        return runningQueue[cpuID];
    }

}

 */