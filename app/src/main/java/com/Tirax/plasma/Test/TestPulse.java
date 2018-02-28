package com.Tirax.plasma.Test;

import com.Tirax.plasma.SerialPortsHardware.DataProvider;

import main.activity.Test.TestResult;

/**
 * Created by MHSaadatfar on 4/14/2017.
 */
public class TestPulse {
    public void Run() {
        TestResult.setLog("Ready...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataProvider.setRegister(DataProvider.RPWR, (char) 50);
        DataProvider.setRegister(DataProvider.MTTI, (char) 4);
        DataProvider.setRegister(DataProvider.MTB1, (char) 1);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            return;
        }

        TestResult.setLog("Test Done.");
    }
}
