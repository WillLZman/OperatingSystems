package OS;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by William Zimmerman on 3/8/2017.
 */
public class Conversions {
/*
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
    }*/
private static String hexBinaryHelper(char[] hexArray) {
    String str = "";
    if (hexArray.length == 0) {
        return str;
    } else {
        char value = hexArray[0];
        switch (value) {
            case '0':
                str = "0000";
                break;
            case '1':
                str = "0001";
                break;
            case '2':
                str = "0010";
                break;
            case '3':
                str = "0011";
                break;
            case '4':
                str = "0100";
                break;
            case '5':
                str = "0101";
                break;
            case '6':
                str = "0110";
                break;
            case '7':
                str = "0111";
                break;
            case '8':
                str = "1000";
                break;
            case '9':
                str = "1001";
                break;
            case 'A':
                str = "1010";
                break;
            case 'B':
                str = "1011";
                break;
            case 'C':
                str = "1100";
                break;
            case 'D':
                str = "1101";
                break;
            case 'E':
                str = "1110";
                break;
            case 'F':
                str = "1111";
                break;
            default:
                System.out.print("There was an error in the Hex to Binary Conversion Switch");
                System.out.println("See TOOL: hexBinaryHelper()");
                Thread.currentThread().getStackTrace();
        }

        return str + hexBinaryHelper(Arrays.copyOfRange(hexArray, 1, hexArray.length));
    }

}

    public static String HexToBinary(String s) {
        char[] hexArray = s.toCharArray();
        return hexBinaryHelper(hexArray);
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
    public static  Integer hexToDec (String s){
        int dec = Integer.valueOf(s, 16).intValue();
        return dec;
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
