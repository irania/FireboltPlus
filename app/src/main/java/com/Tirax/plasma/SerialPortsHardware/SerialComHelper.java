package com.Tirax.plasma.SerialPortsHardware;

/**
 * Created by MHSaadatfar on 4/14/2017.
 */
public class SerialComHelper {
    public final static char WRITE_STARTBIT =255;
    public final static char ACK_STARTBIT =254;

    public static char calcAckCode(char r,char d){
        return (char) ((r ^ d) & 0x7f);
    }
    public static void sendRegister(char r,char d){
        SerialPort.sendByte(WRITE_STARTBIT);
        SerialPort.sendByte(r);
        SerialPort.sendByte(d);
        SerialPort.sendByte(calcAckCode(r, d));
    }
}
