package com.Tirax.plasma;

public class Tools {
	public static char byteToChar(byte i){
		return (char) (i&0xff);
	}
	public static byte charToByte(char i){
		int v=(int)i;
		return (byte) v;
	}
//	public static int getTemp(int i,int settemp,int time){
//		int result;
//		Log.e("GETTEMP","real temp:"+i+" settemp "+settemp+" time"+time);
//		if(time>=STABILITY_TIME)
//			result = settemp;
//		else
//			result = Math.round(i+(settemp-i)*time/STABILITY_TIME);
//		return result;
//	}
//	public static int getInvTemp(int i){
//		return (int) Math.round((i-B)/A);
//	}

}
