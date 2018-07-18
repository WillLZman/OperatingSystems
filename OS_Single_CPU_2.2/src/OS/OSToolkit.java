/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OS;

/**
 *
 * @author Alex
 */
/**
 * Created by wei on 2/9/2017.
 */
public class OSToolkit {

    private OSToolkit(){

    }

    //used to convert hex to byte
    public static String hexToByte(String hex){

        long i = Long.parseLong(hex, 16);
        String bin = Long.toBinaryString(i);

        while(bin.length() != 32)
        {
            bin = "0" + bin;
        }
        return bin;
    }
}
