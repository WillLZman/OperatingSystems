package OS;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by William Zimmerman on 3/8/2017.
 */
public class Conversions {
    public static int charToInt(char[] binaryArray) {
        int total = 0;
        if (binaryArray.length == 0)
            return total;
        else {
            if (binaryArray[0] == '1') {
                total += (int) Math.pow(2, binaryArray.length - 1);
            }
            return total + charToInt(Arrays.copyOfRange(binaryArray, 1, binaryArray.length));
        }
    }

    public String HexToBinary(String s){
        String binary = new BigInteger(s,16).toString(2);
        if(binary.length() < 32){
            int diff = 32 - binary.length();
            String pad = "";
            for(int i = 0; i < diff; ++i){
                pad = pad.concat("0");
            }
            binary = pad.concat(binary);
        }else {
            System.out.println("error with HexToBinary");
        }
        return binary;
    }


    public String BinaryToHex(String s){
        String s1 = "";
        if (s.length()==0){
            return s1;
        }else if (s.length() >= 4){
            int decimal = Integer.parseInt(s,2);
        }else {
            System.out.println("Error with BinaryToHex");
        }
        return s1;
    }
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


    public static String decToHex(Integer integer){
       String hex = Integer.toHexString(integer);
       return hex;
    }

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


}
