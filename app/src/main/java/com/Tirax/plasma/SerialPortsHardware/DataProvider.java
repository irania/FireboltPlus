package com.Tirax.plasma.SerialPortsHardware;

import com.Tirax.plasma.Enums.VersionTypes;
import com.Tirax.plasma.Errors.*;
import com.Tirax.plasma.Errors.Error;

import java.util.ArrayList;

public class DataProvider extends ReadWriteSerialPort {

	public static final VersionTypes VERSION = VersionTypes.CAVITATION;
	//Registers
	public static char RMER = 0;//Micro Error
	public static char RUER = 1;//UI Error
    public static char MTB1 = 2;//Micro Test Bit 1
	public static char MTTI = 3;//Micro Test Time
	public static char MVE = 4;//Micro version
	public static char RMST = 10;//Micro Status
	public static char RUST = 11;//UI Status
	public static char RMKY = 12;//Micro Keys
	public static char RTYP0 = 20;//Type0
	public static char RTYP1 = 21;//Type1
	public static char RFRQ = 22;//Freq. (Freq/100)
	public static char RPWR = 23;//Power
	public static char RPFRQ = 24;//Pulse Freq.
	public static char RPLEN = 25;//Pulse Length (Pulse/10)ms
	public static char RSNUM = 26;//Shots Num.
	public static char RVCLV = 27;//Vacu. Level
	public static char RLPGO = 28;//on time ms/10
	public static char RLPGF = 29;//off time ms/10
	public static char RDPTH = 30;//Firebolt length
	public static char RFDLY = 32;//Firebolt delay
	public static char RFRSHt = 33;//Firebolt shot
	public static char RCRN = 40;//Current (Current/50)mA
	public static char RTMP = 50;//Temperature
	public static char RPRS = 53;//Pressure

	public static String[] registersName = new String[]{"RMER", "RUER", "MTB1","MTTI","MVE","5","6","7","8","9","RMST","RUST","RMKY","13","14","15","16","17","18","19","RTYP0","RTYP1","RFRQ","RPWR","RPFRQ","RPLEN","RSNUM","RVCLV","RLPGO","RLPGF","RDPTH","31","RFDLY","RFRSHT","34","35","36","37","38","39","RCRN1","RCRN2","RCRN3","43","44","45","46","47","48","49","RTMP1","RTMP2","RTMP3","RPRS"};
	public static boolean[] registersUseful = new boolean[]{true, true, true,true,true,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,true,false,true,true,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,true,true,true,true};
	//Bits
	public static char RST_ON= 0;//OFF/ON
	public static char RST_BUSY= 1;//Busy

	public static char RTYP0_HF= 0;//HF
	public static char RTYP0_LF= 1;//LF
	public static char RTYP0_VACUM= 2;//Vaccum
	public static char RTYP0_FRAC= 3;//Frac

	public static char RTYP1_CNT= 0;//Cnt/Pulse
	public static char RTYP1_AUTO= 1;//Auro/Pedal

	public static char RMKY_PAUSE= 0;//Cnt/Pulse

	public static char MTB1_SPW=0;

	public static ArrayList<com.Tirax.plasma.Errors.Error> errors;
	private static boolean beforeMicroErr =false;
	private static boolean beforeUIErr= false;

	public static int powerBase = 25000;

	//set registers

	public static void initialize_errors(){
		errors = new ArrayList<Error>();
		errors.add(new Error124());
	}

	public static void zeroAllRegisters(){
		for (char i=0;i<REGISTERS_NUMBER;i++){
			setRegister(i,(char)0);
		}
	}
	public static boolean isMicroError() {
		if(getRegister(RMER)>0){
			beforeMicroErr = true;
			return true;
		}

		beforeMicroErr = false;
		return false;
	}

	public static String getMicroError(){
		int error_num = getRegister(RMER);
		return (error_num+100)+"";
	}



	public static boolean isNotInMicroError() {
		return !beforeMicroErr;
	}


	public static String getRegisterName(int num){
		if(num<registersName.length){
			return registersName[num];
		}
		else
			return "Aut of Range Register";
	}

	public static boolean getPedalisActive() {
		return getBit(RMKY, (char) 1);
	}


	public static boolean isInUIError() {
		return !beforeUIErr;
	}

	public static boolean isUIError() {
		if(getRegister(RUER)>0){
			beforeUIErr = true;
			return true;
		}

		beforeUIErr = false;
		return false;
	}

	public static String getUIError() {
		int error_num = getRegister(RUER);
		return (error_num+200)+"";
	}
}
