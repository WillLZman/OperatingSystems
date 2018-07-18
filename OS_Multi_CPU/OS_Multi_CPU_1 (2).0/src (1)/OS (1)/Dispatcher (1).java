/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OS;

/**
 * Created by wei on 2/14/2017.
 */

public class Dispatcher{

    //ControlBlock[] runningQueue;
    //QQueue runningQueue;
    //public void multiCPU (int numCPU){
      //  runningQueue = new ControlBlock[numCPU];
   // }
    /*
    public void dispatchCPU(CPU cpu){
        OSystem oSystem = OSystem.getInstance();
        synchronized (oSystem.getReadyQueue()){
            ControlBlock controlBlock = OSystem.getInstance().getReadyQueue().popTheQueue();
            controlBlock.setStatus(ControlBlock.Status.RUNNING);

            }
    }

    protected ControlBlock getCurrentProcess (int ID){

        return  runningQueue[ID];
    }

}*/
    public void dispatchCPU(CPU cpu) {
       OSystem os = OSystem.getInstance();
        synchronized (os.getReadyQueue()) {
            ControlBlock current = OSystem.getInstance().getReadyQueue().popTheQueue();
            current.setStatus(ControlBlock.Status.RUNNING);
            cpu.actualCB = current;
        }
    }
}
