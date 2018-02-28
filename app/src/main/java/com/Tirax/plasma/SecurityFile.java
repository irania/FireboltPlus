package com.Tirax.plasma;

import android.util.Log;

import com.Tirax.plasma.Enums.SecurityType;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Emertat on 3/10/2017.
 */
public class SecurityFile {

    public static String PublicKey1 = "83bfd00911658";
    public static String PublicKey2 = "a9cb690510021b917eb";
    public static String PrivateKey;

    public static void save(SecurityType st,String value){

        try {

            SharedPrefrences.setSecurityItem(st.getValue(), value);
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] digest = md.digest((value+PrivateKey+PublicKey1+PublicKey2).getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            SharedPrefrences.setSecurityHash(st.getValue(), sb.toString());

            Log.e("TIRAX6", "security value " + value + " hash " + sb.toString());



        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }
    public static  String load(SecurityType st) throws Exception {
        try {

            String value = SharedPrefrences.getSecurityItem(st.getValue());
            if(value==null)
                return null;

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest((value + PrivateKey + PublicKey1 + PublicKey2).getBytes());

            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            String hashValue = SharedPrefrences.getSecurityHash(st.getValue());

            Log.e("TIRAX6", "security value " + value + " hash " + hashValue);

            if(hashValue.equals(sb.toString()))
                return value;




        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        throw  new Exception("not equals exception");
    }


    public static void generatePrivateKey(){
        try {
            if(SharedPrefrences.getPrivateKey()==null)
            {
                SecureRandom random = new SecureRandom();
                PrivateKey = new BigInteger(40, random).toString(32);
                Log.e("TIRAX6", "Private key " + PrivateKey);
                SharedPrefrences.setPrivateKey(PrivateKey);
            }else
            {
                PrivateKey =SharedPrefrences.getPrivateKey();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
