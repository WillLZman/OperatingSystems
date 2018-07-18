package OS;

/**
 * Created by William Zimmerman on 3/5/2017.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Loader
{
    Conversions conversions;
    private JointMemory diskMemory;
    public Loader ()
    {
        //readFile(PCB);
    }

    public ControlBlock[] readFile() throws IOException
    {
        //ControlBlock[] vPCB, JointMemory diskMem
        ControlBlock[] vPCB = new ControlBlock[30];
        String file = "Program-File.txt";
        FileReader F = new FileReader(file);
        BufferedReader b = new BufferedReader(F);

        this.diskMemory = OSystem.getInstance().getDisk();

        int diskCount = 0;
        int pcbCount = 0;
        //boolean jobVdata = false; //to keep track of Job or Data: false for Job, true for Data


        for (String s = b.readLine(); (s != null) && !(s.equals("")); s = b.readLine())
        {
            //if (s.startsWith("0x")) { //should be either "//" or "0x----------"
            String[] splited = s.split("\\s+");
            //System.out.println("splited array: ");
            for (int k = 0; k < splited.length; k++){
                System.out.println(splited[k] + " ");
            }
            System.out.println("______________________________________________________________________________________");
            if(s.contains("//"))
            {
                //current = s.next(); //should be either "JOB" or "DATA" or "END"
                if(s.contains("JOB"))
                {
                    ControlBlock ph = new ControlBlock();
                    vPCB[pcbCount] = ph;

                    //current = s.next(); //programCounter
                    vPCB[pcbCount].setInstructionLocationOnDisk(diskCount);

                    int result = conversions.hexToDec(splited[2]);
                    vPCB[pcbCount].setProgramID(result);
                    //pcb[0].getProgramID.get
                    System.out.println(result);
                    result = conversions.hexToDec(splited[3]);
                    vPCB[pcbCount].setCodeSize(result);

                    result = conversions.hexToDec(splited[4]);
                    vPCB[pcbCount].setPriority(result);

                    //jobVdata = false;
                }
                else if(s.contains("Data"))
                {
                    int result = conversions.hexToDec(splited[2]);
                    vPCB[pcbCount].setInputBuffer(result);
                    System.out.println(result);
                    result = conversions.hexToDec(splited[3]);
                    vPCB[pcbCount].setOutputBufferSize(result);

                    result = conversions.hexToDec(splited[4]);
                    vPCB[pcbCount].setTempBufferSize(result);
                    //System.out.println("hex" +result);
                    //jobVdata = true;
                }
                else //"END"
                {
                    OSystem.getInstance().getJobQueue().push(vPCB[pcbCount]);
                    pcbCount++;
                }

            }
            else
            {
                System.out.println("Testing String s: " + diskCount);
                diskMemory.writeToMem(diskCount,s.substring(2).toString().trim()); //revert back
                System.out.println("Testing String s: " + s);
                diskCount++;

            }
        }
        return vPCB;
    }


    //used to convert hex to byte (Created by Wei 2/9)
    public static String hexToByte(String hex)
    {
        long i = Long.parseLong(hex, 16);
        String bin = Long.toBinaryString(i);
        while(bin.length() != 32)
        {
            bin = "0" + bin;
        }
        return bin;
    }

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


