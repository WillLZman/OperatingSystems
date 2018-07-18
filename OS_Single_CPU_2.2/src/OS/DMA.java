package OS;

/**
 * Created by William Zimmerman on 3/8/2017.
 */

public class DMA {

    //ControlBlock or PCB, chose one or other

    public void writeDMA(ControlBlock current, int location, int data) throws Exception {
        current.ioOperationMade();
        setRamInt(location, data);
    }

    public Integer readDMA(ControlBlock current, int location) throws Exception {
        current.ioOperationMade();
        return getRamInt(location);
    }

    // GetRamInt and SetRam Int interact with memory
    private int getRamInt(int ramAddress) {
        String hex = OSystem.getInstance().getRam().read(ramAddress);
        return Conversions.hexToDec(hex);
    }

    private void setRamInt(int ramAddress, int data) {
        String hexAddr = Conversions.decToHex(data);
        OSystem.getInstance().getRam().overwriteToMem(ramAddress, hexAddr);
    }

}
