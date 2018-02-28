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
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataProvider.setRegister(DataProvider.RPWR, (char) 50);
        DataProvider.setRegister(DataProvider.MTTI, (char) 9);

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
        TestResult.setLog("Test Done.");
    }
}
