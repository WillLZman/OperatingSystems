package OS;

/**
 * Created by William Zimmerman on 3/5/2017.
 * References:
 * https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html
 * https://www.tutorialspoint.com/java/java_multithreading.htm
 */
public abstract class threadAsymmetric extends Thread{ // abstract data type because of 5.8.1 in book?
    private Thread thread = new Thread(){
        @Override
        public void run(){
            threadAsymmetric.this.run();
        }
    };

    private boolean availability; // boolean value for mutex lock


    public abstract void run();

    public void start(){
        thread.start();
        availability = true;
    }

   // public void run(){
     //   thread.run();
     //   availiability = true;
  //  }

    public void end(){
        thread.interrupt();
        availability = false;
    }

    public void Sleep(long millis) throws InterruptedException { // Sleep/wait are same, maybe create a sleep all CPU function?
        thread.sleep(millis);
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailiability(boolean availiability) {
        this.availability = availiability;
    }
}
