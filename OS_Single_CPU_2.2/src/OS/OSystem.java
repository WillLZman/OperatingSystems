package OS;

/**
 * Created by William Zimmerman on 3/9/2017.
 */

public class OSystem {
    private static OSystem instance = new OSystem();
    private CPU cpu;
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

    public OSystem(){
         cpu = new CPU();
       // ram = new RamMemory();
        ram = new JointMemory(1024);
        loader = new Loader();
        scheduler = new Scheduler();
        dispatcher = new Dispatcher();
       // disk = new DiskMemory();
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

    public CPU getCpu() {
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

    public void setJobQueue(QQueue jobQueue) {
        this.jobQueue = jobQueue;
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

    public void assignCPU() {  // First in first out
        PCB currentJob = OperatingSystem.getInstance().getReadyQueue().popTheQueue();
        runningQueue[0] = currentJob;
        currentJob.setStatus(PCB.Status.RUNNING);
    }

    protected PCB getCurrentJob(int cpuID) {
        return runningQueue[cpuID];
    }

}

 */