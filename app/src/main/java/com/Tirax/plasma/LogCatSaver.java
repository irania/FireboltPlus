package com.Tirax.plasma;

import java.io.File;
import java.io.IOException;

/**
 * Created by a.irani on 12/11/2016.
 */
public class LogCatSaver {

    private static final String LOGCAT_COMMAND = "logcat -v time -f ";
    private static String logFileName = "storage/sd_external/logs.log";
    public static long getVolume()
    {
        File file = new File(logFileName);
        long length = file.length();
        return length;
    }
    public static void getLogCat() {

        File mLogFile;
        String timestamp = Long.toString(System.currentTimeMillis());

        mLogFile = new File( logFileName);
        try {
            mLogFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cmd = LOGCAT_COMMAND + mLogFile.getAbsolutePath();
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
