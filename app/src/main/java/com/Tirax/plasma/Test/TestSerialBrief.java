package com.Tirax.plasma.Test;

import com.Tirax.plasma.SerialPortsHardware.DataProvider;
import com.Tirax.plasma.SerialPortsHardware.SerialComHelper;

import main.activity.Test.TestResult;

/**
 * Created by MHSaadatfar on 4/14/2017.
 */
public class TestSerialBrief {
    public void Run(){
        TestHelper.StopSerialService();
        SerialComHelper.sendRegister(DataProvider.RUST,(char) 2);
        char ack=SerialComHelper.calcAckCode(DataProvider.RUST, (char) 2);
        int d1=TestHelper.WaitForSerial(SerialComHelper.ACK_STARTBIT,1000);
        if (d1==1000){
            TestHelper.StartSerialService();
            TestResult.appendLog("Failed.\n");
            return;
        }
        TestResult.appendLog("Ack Start Bit Received. Delay: " + d1+ "ms\n");
        int d2=TestHelper.WaitForSerial(ack,1000);
        if (d2==1000){
            TestHelper.StartSerialService();
            TestResult.appendLog("Failed.\n");
            return;
        }
        TestHelper.StartSerialService();
        TestResult.appendLog("Ack Received. Delay: " + d2 +"ms\n");
        TestResult.appendLog("Passed.\n");
        return;
    }
}
