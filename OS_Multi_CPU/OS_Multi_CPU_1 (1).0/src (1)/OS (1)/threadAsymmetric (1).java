package OS;

/**
 * Created by William Zimmerman on 3/10/2017.
 * References:
 * https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html
 * https://www.tutorialspoint.com/java/java_multithreading.htm
 */
public abstract class threadAsymmetric extends Thread{
    Thread thread = new Thread(){
        @Override
        public void run(){
            threadAsymmetric.this.run();
        }
    };

    private boolean availiability; // boolean value for mutex lock


    public abstract void run();

    public void start(){
        thread.start();
        availiability = true;
    }

   // public void run(){
     //   thread.run();
     //   availiability = true;
  //  }

    public void end(){
        thread.interrupt();
        availiability = false;
    }

    public void Sleep(long millis) throws InterruptedException { // Sleep/wait are same, maybe create a sleep all CPU function?
        thread.sleep(millis);
    }

    public boolean isAvailiability() {
        return availiability;
    }

    public void setAvailiability(boolean availiability) {
        this.availiability = availiability;
    }
}
