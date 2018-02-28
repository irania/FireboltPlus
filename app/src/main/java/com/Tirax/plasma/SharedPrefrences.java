package com.Tirax.plasma;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.Tirax.plasma.SerialPortsHardware.DataProvider;

import java.util.ArrayList;


public class SharedPrefrences {

    public static Activity mainActivity;
    private static String mainKey = "TIRAX_PLASMA";
    private static String resetKey = "RESET_KEY";
    private static String timeKey="TIME_KEY";
    private static String timeOfWorksKey = "TIMEOFWORKS_KEY";
    private static String securityKey = "ACNE_POWER";
    private static String hashKey="BLEPHARO_POWER";
    private static String privateKey="SCAR_POWER";

    public static boolean getReseted(){
        SharedPreferences resetInfo = mainActivity.getSharedPreferences(mainKey, Context.MODE_PRIVATE);
        return resetInfo.getBoolean(resetKey, false);
    }
    public static void setReseted(boolean status){

        SharedPreferences resetInfo= mainActivity.getSharedPreferences (mainKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_info=resetInfo.edit();
        editor_info.putBoolean(resetKey, status);
        editor_info.apply();

    }
    public static ArrayList<Character> getRegisters(){
        SharedPreferences regInfo= mainActivity.getSharedPreferences (mainKey, Context.MODE_PRIVATE);
        ArrayList<Character> ch = new ArrayList<Character>();
        for(int i=0;i< DataProvider.REGISTERS_NUMBER;i++){
            ch.add((char)regInfo.getInt("REGISTER"+i,0));
            Log.e("ERROR", "REGISTERS i " + i + "" + regInfo.getInt("REGISTER" + i, 0));
        }
        return ch;
    }
    public static void setRegisters(ArrayList<Character> ch){
        SharedPreferences regInfo= mainActivity.getSharedPreferences (mainKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_info=regInfo.edit();
        for(int i=0;i<DataProvider.REGISTERS_NUMBER;i++){
            editor_info.putInt("REGISTER" + i, ch.get(i));
        }
        editor_info.apply();
    }

    public static int getTime() {
        SharedPreferences info = mainActivity.getSharedPreferences(mainKey, Context.MODE_PRIVATE);
        return info.getInt(timeKey, 0);
    }

    public static void setTime(int time){

        SharedPreferences resetInfo= mainActivity.getSharedPreferences (mainKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_info=resetInfo.edit();
        editor_info.putInt(timeKey, time);
        editor_info.apply();

    }


    public static String getSecurityItem(int index){
        SharedPreferences resetInfo = mainActivity.getSharedPreferences(mainKey, Context.MODE_PRIVATE);
        return resetInfo.getString(securityKey+index, null);
    }

    public static void setSecurityItem(int index,String item){

        SharedPreferences resetInfo= mainActivity.getSharedPreferences (mainKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_info=resetInfo.edit();
        editor_info.putString(securityKey+index, item);
        editor_info.apply();

    }

    public static String getSecurityHash(int index){
        SharedPreferences resetInfo = mainActivity.getSharedPreferences(mainKey, Context.MODE_PRIVATE);
        return resetInfo.getString(hashKey+index, null);
    }

    public static void setSecurityHash(int index,String item){

        SharedPreferences resetInfo= mainActivity.getSharedPreferences (mainKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_info=resetInfo.edit();
        editor_info.putString(hashKey+index, item);
        editor_info.apply();

    }

    public static String getPrivateKey() {
        SharedPreferences resetInfo = mainActivity.getSharedPreferences(mainKey, Context.MODE_PRIVATE);
        return resetInfo.getString(privateKey, null);
    }

    public static void setPrivateKey(String item) {

        SharedPreferences resetInfo= mainActivity.getSharedPreferences (mainKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_info=resetInfo.edit();
        editor_info.putString(privateKey, item);
        editor_info.apply();
    }
}
