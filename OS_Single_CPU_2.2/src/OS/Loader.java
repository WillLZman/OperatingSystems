package OS;

/**
 * Created by William Zimmerman on 3/5/2017.
 */

import java.io.*;
import java.util.*;

public class Loader
{
    Conversions conversions;
    private JointMemory diskMemory;
    public Loader ()
    {
        //readFile(PCB);
    }

    public void readFile() throws IOException
    {
        String file = "Program-File.txt";
        FileReader F = new FileReader(file);
        BufferedReader b = new BufferedReader(F);

        //check, do we need this
        this.diskMemory = OSystem.getInstance().getDisk();

        int diskCount = 0;
        //boolean jobVdata = false; //to keep track of Job or Data: false for Job, true for Data

        //String ph = b.readLine();
        //String[] splited = ph.split("\\s+");

        //ControlBlock pcb = new ControlBlock(conversions.hexToDec(splited[2]), conversions.hexToDec(splited[3]), conversions.hexToDec(splited[4]));
       // System.out.println("PCB 1: " + pcb);
        //needs to set diskLocation here////////////////////////
        //pcb.setInstructionLocationOnDisk(diskCount);
        ////////////////////////////////////////////////////////
        ArrayList<ControlBlock> pcbArrayList = new ArrayList<>();
        ControlBlock pcb = new ControlBlock();

       // OSystem oSystem = OSystem.getInstance();
        QQueue jobQueue= OSystem.getInstance().getJobQueue();
       // System.out.println("JobQueue1 : " + OSystem.getInstance().getJobQueue().peekInQueue());
        for (String s = b.readLine(); (s != null) && !(s.equals("")); s = b.readLine())
        {
            //if (s.startsWith("0x")) { //should be either "//" or "0x----------"
            String[] splited = s.split("\\s+");

            if(s.contains("//")) {

                //current = s.next(); //should be either "JOB" or "DATA" or "END"
                if (s.contains("JOB")) {
                    /*
                    //current = s.next(); //programCounter
                    vPCB[pcbCount].setInstructionLocationOnDisk(diskCount);
                    //ph.setInstructionLocationOnDisk(diskCount);

                    int result = conversions.hexToDec(splited[2]);
                    vPCB[pcbCount].setProgramCounter(result);

                    System.out.println(result);
                    result = conversions.hexToDec(splited[3]);
                    vPCB[pcbCount].setCodeSize(result);

                    result = conversions.hexToDec(splited[4]);
                    vPCB[pcbCount].setPriority(result);
                   */

                    //jobVdata = true;
                    //Loader code:

                    pcb = new ControlBlock(conversions.hexToDec(splited[2]), conversions.hexToDec(splited[3]), conversions.hexToDec(splited[4]));
                    System.out.println("JobSize RIght NOw: "  + conversions.hexToDec(splited[3])); // this correct :)

                    System.out.println("JobSize RIght NOw Again I guess: "  + pcb.getCodeSize()); // this correct :)


                    //System.out.println("JobQueueN: " + pcb);
                    //needs to set diskLocation here
                    pcb.setInstructionLocationOnDisk(diskCount);
                   // OSystem.getInstance().getJobQueue().push(pcb);
                    jobQueue.push(pcb);

                    //System.out.println("Size of the job queue" + OSystem.getInstance().getJobQueue().size());
                    System.out.println("Size of the job queue" + jobQueue.size());

                    for (int i = 0; i<jobQueue.size(); i++) {
                        //System.out.println("What is the JobQueue at JOB????: " + OSystem.getInstance().getJobQueue().getIndex(i).getCodeSize());
                        System.out.println("What is the JobQueue at JOB????: " + jobQueue.getIndex(i).getCodeSize());
                    }

                    System.out.println("What is this pCb??? " + pcb.getCodeSize());

                }
                else if(s.contains("Data"))
                {
                    int result = conversions.hexToDec(splited[2]);
                    //vPCB[pcbCount].setInputBuffer(result);
                    pcb.setInputBuffer(result);

                    result = conversions.hexToDec(splited[3]);
                    //vPCB[pcbCount].setOutputBufferSize(result);
                    pcb.setOutputBufferSize(result);

                    result = conversions.hexToDec(splited[4]);
                    //vPCB[pcbCount].setTempBufferSize(result);
                    pcb.setTempBufferSize(result);

                    System.out.println("Data CheckPLZ Work: " + (conversions.hexToDec(splited[2]) +conversions.hexToDec( splited[3]) + conversions.hexToDec(splited[4])));
                    //jobVdata = false;
                }
                else //"END"
                {
                    //OSystem.getInstance().getJobQueue().push(pcb); // jobQueue is fine, look at screenshot LoaderJobQueue for Proof
                    //pcbCount++;
                }
                //OSystem.getInstance().getJobQueue().size();
                jobQueue.size();
                //System.out.println("What is last???? " + OSystem.getInstance().getJobQueue().getIndex(0).getCodeSize());
                System.out.println("What is last???? " + jobQueue.getIndex(0).getCodeSize());

            }
            else
            {
                //if(jobVdata == true) {
                    diskMemory.writeToMem(diskCount, s.substring(2).toString().trim()); //revert back
                    diskCount++;
               //}
            }
            //System.out.println("JobQueueSize: " + OSystem.getInstance().getJobQueue().size());
        }
        for (int i = 0; i <2027; i++) { //707 is last instruciton in Disk
            System.out.println("EveryThing in Disk PLZ: " + OSystem.getInstance().getDisk().read(i));

        }System.out.println("EveryThing in Disk Code??? " + jobQueue.getIndex(10).getCodeSize());
        OSystem.getInstance().setJobQueue(jobQueue);
        System.out.println("Plz Work I cry everytime feelsBadMan: " + OSystem.getInstance().getJobQueue().getIndex(0).getCodeSize());
    }


    //used to convert hex to byte (Created by Wei 2/9)
    /*public static String hexToByte(String hex)
    {
        long i = Long.parseLong(hex, 16);
        String bin = Long.toBinaryString(i);
        while(bin.length() != 32)
        {
            bin = "0" + bin;
        }
        return bin;
    }*/

/*
    public static int hexToDec(String hex)
    {
        String digits = "0123456789ABCDEF";
        hex = hex.toUpperCase();
        int val = 0;
        for(int i = 0; i < hex.length(); i++)
        {
            char c = hex.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }*/

                    //public DiskMemory getDiskMemory() {
                    // return diskMemory;
                    //}

                    //public void setDiskMemory(DiskMemory diskMemory) {
                    //this.diskMemory = diskMemory;
                    // }
                } //end of class


