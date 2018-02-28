package com.Tirax.plasma;

import android.util.Log;

import com.Tirax.plasma.Enums.SecurityType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.CRC32;


/**
 * Created by Emertat on 3/8/2017.
 */
public class License {


    private static final int  SERIAL_SIZE = 6;


    //TODO test it
    public static boolean licenceIsOk(){
        try {

            //1st compare time
            int usedTime = tryParse(SecurityFile.load(SecurityType.TIME));
            int validTime =  tryParse(SecurityFile.load(SecurityType.VALID_TIME));

            //TODO test it
            if(validTime!=-1 && usedTime>validTime )
                return false;

            //2nd compare date
            SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd");
            String vd = SecurityFile.load(SecurityType.VALID_DATE);

            Date validDate =  format.parse(vd);
            Date nowDate = new Date();

            if(validDate.compareTo(nowDate) <=0)
                return false;

            return true;
        } catch (Exception e) {
            //TODO get a big error
            e.printStackTrace();
            return false;
        }
    }

    private static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    //TODO test it
    public static void generateReqCode() {
        try {
            //TODO serial==null?
            String serial = SecurityFile.load(SecurityType.SERIAL);

            int workedTime = tryParse(SecurityFile.load(SecurityType.TIME))/10;

            String requestCode = "not generated";

            if(serial!=null)
                requestCode = serial + Integer.toHexString(workedTime);

            SecurityFile.save(SecurityType.REQUEST_CODE,requestCode);

            Log.e("TIRAX6","Request code: "+requestCode+" time: "+workedTime);
        } catch (Exception e) {
            //TODO exception for it
            e.printStackTrace();
        }
    }


    public static String getActivateCode(){
        return "";
    }
    //TODO test it
    public static void UpdateValidTime(String activeCode) throws Exception {

        if(activeCode.substring(0,6).equals("999999")) {
            SecurityFile.save(SecurityType.VALID_TIME, "-1");
        }
        else{
            String currentTime = SecurityFile.load(SecurityType.TIME);
            String newValidTime = (decodeTime(activeCode) + tryParse(currentTime)) + "";
            SecurityFile.save(SecurityType.VALID_TIME, newValidTime);
        }


    }
    //TODO test it
    public static void UpdateValidDate(String activeCode) throws Exception {


        if(activeCode.substring(0,6).equals("999999")) {
            SecurityFile.save(SecurityType.VALID_DATE, "99/09/11");
        }
        else {
            //get cuurent date
            DateFormat df = new SimpleDateFormat("yy/MM/dd");
            Date dateobj = new Date();
            String currentDate = df.format(dateobj);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, decodeMonth(activeCode));
            cal.add(Calendar.DATE, decodeDay(activeCode));

            String newValidDate = df.format(cal.getTime());
            SecurityFile.save(SecurityType.VALID_DATE, newValidDate);

        }
    }
    private static Integer decodeMonth(String key) {
        String validMonth = key.substring(3, 5);
        return tryParse(validMonth);

    }
    private static Integer decodeDay(String key) {
        String validDay = key.substring(5, 6);
        return tryParse(validDay)*3;

    }

    private static Integer decodeTime(String key) {
        String validTime = key.substring(0,3);
        return
                tryParse(validTime)*60;

    }

    public static boolean isTrueKey(String key){

        try {
            String validTime = key.substring(0, SERIAL_SIZE);
            String reqCode = null;

            reqCode = SecurityFile.load(SecurityType.REQUEST_CODE);

            String keyHash = key.substring(SERIAL_SIZE);
            String trueHash = validTime + reqCode + SecurityFile.PublicKey1 + SecurityFile.PublicKey2;


            CRC32 crc = new CRC32();
            crc.update(trueHash.getBytes());
            if(crc.getValue()<10000)
                trueHash = "1111";
            else
                trueHash  = crc.getValue()%10000+"";

            Log.e("TIRAX6", "True hash: " + trueHash + " Key Hash: "+keyHash);
            if(trueHash.equals(keyHash))
                return true;
            else
                return false;

        } catch (Exception e) {
            return false;
        }
    }


    public static void initializeTime(){

        SecurityFile.save(SecurityType.VALID_TIME, "0");
        DateFormat df = new SimpleDateFormat("yy/MM/dd");
        SecurityFile.save(SecurityType.VALID_DATE, df.format(new Date()));

    }


    public static boolean isTrial() throws Exception {
        DateFormat df = new SimpleDateFormat("yy/MM/dd");
        Calendar cal = Calendar.getInstance();


        cal.setTime(df.parse(SecurityFile.load(SecurityType.VALID_DATE)));
        //TODO seven it
        cal.add(Calendar.DATE, 0);

        Date validDate =  cal.getTime();
        Date nowDate = new Date();


        if(validDate.compareTo(nowDate) <=1)
            return false;

        return true;

    }
}
