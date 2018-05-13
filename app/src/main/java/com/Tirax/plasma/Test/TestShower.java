package com.Tirax.plasma.Test;

import com.Tirax.plasma.SerialPortsHardware.DataProvider;

import main.activity.Test.TestResult;

/**
 * Created by MHSaadatfar on 4/14/2017.
 */
public class TestShower {
    public void Run() {
        TestResult.setLog("Ready...");
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataProvider.setRegister(DataProvider.RPWR, (char) 50);
        DataProvider.setRegister(DataProvider.RDPTH, (char) 75);
        DataProvider.setRegister(DataProvider.MTTI, (char) 9);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i=0;i<15;i++) {
            TestResult.setLog("Running...  (Min: "+i+")");
            for (int j=0;j<6;j++) {
                DataProvider.setRegister(DataProvider.MTB1, (char) 1);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    return;
                }
                if (Thread.interrupted()) return;
            }
        }

        DataProvider.setRegister(DataProvider.RPWR, (char) 0);
        DataProvider.setRegister(DataProvider.RDPTH, (char) 0);
        DataProvider.setRegister(DataProvider.MTTI, (char) 0);
        TestResult.setLog("Test Done.");
    }
}
