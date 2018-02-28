package com.Tirax.plasma.Test;

import com.Tirax.plasma.SerialPortsHardware.DataProvider;

import main.activity.Test.TestResult;

/**
 * Created by MHSaadatfar on 4/14/2017.
 */
public class TestRun0t100 {
    public void Run(){
        TestResult.setLog("Ready...");
        for (char i=1;i<=20;i++) {
            while (DataProvider.getPedalisActive()==true)
                if (Thread.interrupted()) return;
            char p = (char) (i*5);
            DataProvider.setRegister(DataProvider.RPWR, p);
            TestResult.setLog("Power: " + Integer.toString(p) + "%");
            while (DataProvider.getPedalisActive()==false)
                if (Thread.interrupted()) return;
        }
        TestResult.setLog("Done.");
    }
}
