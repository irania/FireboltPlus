package com.Tirax.plasma.SerialPortsHardware;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.Tirax.plasma.LogCatEnabler;
import com.Tirax.plasma.SharedPrefrences;

public class ReadWriteSerialPort extends AsyncTask<Void, Void, Void>{

	public static int Run=1;

	private final static Integer RESENDTIME = 300;

	public final static Integer REGISTERS_NUMBER = 54;

	private final static char WRITE_STARTBIT =255;
	private final char ACK_STARTBIT =254;
	
	private final int WAIT_START = 0;
	private final int START_RECIEVED = 1;
	private final int WAIT_VALUE=2;
	private final int WAIT_ACK=3;
	
	private static ArrayList<Character> registers;
	private static ArrayList<Character> changedRegisters = new ArrayList<Character>();

	
	private int writeState = 0;
	private int ackState = 0;
	private char writeReg;
	private char writeData;
	private static char waited_ack;
	
	private static int resendTimer = 0;
	private static int resetTimer=30;
	private Handler ResendRunnableHandler=new Handler();


	public final static char regMachineState = 7;
	@Override
	protected Void doInBackground(Void... voids) {
		try {

			initialRegisters();
			ResendRunnableHandler.postDelayed(ResendRunnable, 0);
			while (true) {
				if (Run==1)
					readSerialPort();
			}
		} catch (Exception ex) {
			Log.e("TIRAX ERROR", "ReadWriteSerialPort doInBackground error accured " + ex);
			ex.printStackTrace();
		}

		return null;
	}
	private void senAllRegisters() {
		if(LogCatEnabler.resetActivated)
			Log.e("TIRAX", "******************************send registers***********************************");
		for(int i=0;i<REGISTERS_NUMBER;i++) {
			if(DataProvider.registersUseful[i])
				addToChangedRegisters((char) i);
		}
	}
	protected boolean reset() {
		//TODO fix it for reset time
		return false;
	}
	private void initialRegisters() {
		registers = new ArrayList<Character>(REGISTERS_NUMBER);
		if(SharedPrefrences.getReseted()){

				registers = SharedPrefrences.getRegisters();
				SharedPrefrences.setReseted(false);
				logRegisters();
		}else {
			//TODO reset

			for (int i = 0; i < REGISTERS_NUMBER; i++) {
				registers.add((char) 0);
			}
			//setBit(DataProvider.RUST, DataProvider.RST_ON,true);


		}

	}
	protected void readSerialPort(){

		if(SerialPort.isData()){
			char c= SerialPort.read();

			if(c==WRITE_STARTBIT){
				writeState = START_RECIEVED;
				ackState = WAIT_START;
			}else if(writeState==START_RECIEVED){
				writeReg = c;
				writeState = WAIT_VALUE;
			}else if(writeState==WAIT_VALUE){
				writeData = c;
				writeState = WAIT_ACK;
			}else if(writeState==WAIT_ACK){
				if (c==SerialComHelper.calcAckCode(writeReg, writeData)) {
					if (writeReg < REGISTERS_NUMBER) {
						registers.set(writeReg, writeData);
						checkForSendAllRegisters(writeReg);
						if (LogCatEnabler.registerReceivedInline) {
							Log.e("TIRAX4", "Micro: " + (int)writeData+ " => " +DataProvider.getRegisterName((int)writeReg) );
						}
					}
					else {
						writeState = WAIT_START;
						Log.e("TIRAX Error", "ReadWriteSerialPort readSerialPort Reisters Out of bound exception.");
					}

					sendAck(SerialComHelper.calcAckCode(writeReg, writeData));
					if (LogCatEnabler.accAccepted) {
						//if(DataProvider.registersUseful[(int)changedRegisters.get(0)])
						Log.e("TIRAX4", "UI: Ack => "+DataProvider.getRegisterName((int)writeReg));
					}
				}
				writeState = WAIT_START;
			}
			if(c==ACK_STARTBIT){
				ackState = START_RECIEVED;
				writeState = WAIT_START;
			}else if(ackState == START_RECIEVED){
				if(waited_ack == c){
					waited_ack=0;
					if (LogCatEnabler.accAccepted) {
						//if(DataProvider.registersUseful[(int)changedRegisters.get(0)])
							Log.e("TIRAX4", "Mircro: Ack => "+DataProvider.getRegisterName((int)changedRegisters.get(0)));
					}
					if(changedRegisters.size()>0)
						changedRegisters.remove(0);
					resendTimer= 0;
					resetTimer =30;


					sendRegister();
					ackState = WAIT_START;
				}
			}
		}
	}

	private void checkForSendAllRegisters(char writeReg) {
		if(writeReg == DataProvider.RMST && getBit(DataProvider.RMST,(char)0)){
			senAllRegisters();
		}
	}

	private void sendAck(char code){
		SerialPort.sendByte(ACK_STARTBIT);
		SerialPort.sendByte(code);


	}
	private static void sendRegister(){
		if(changedRegisters.size()==0)
			return;
		char Register=changedRegisters.get(0);
		char Data=registers.get(Register);
		SerialComHelper.sendRegister(Register,Data);
		if (LogCatEnabler.registerSendInline) {
			Log.e("TIRAX4", "UI: " +(int)Data+" => "+ DataProvider.getRegisterName((int) Register));
		}

		waited_ack = SerialComHelper.calcAckCode(Register, Data);
		resendTimer=RESENDTIME;
	}
	
	public static void setRegister(char number, char value){
		if(number<REGISTERS_NUMBER) {
			registers.set(number, value);
			addToChangedRegisters(number);
		}
	}
	
	private static void addToChangedRegisters(char number) {

			changedRegisters.add(number);
			if (changedRegisters.size() == 1)
				sendRegister();

	}
	
	public static char getRegister(char number){
		return registers.get(number);	
	}
	
	public static boolean getBit(char number,char p){
		return (registers.get(number) & 1<<p) > 0;
	}
	
	public static void setBit(char number,char p, boolean v){
		if(number<REGISTERS_NUMBER) {
			if (v) {
				registers.set(number, (char) (registers.get(number) | 1 << p));
			} else {
				registers.set(number, (char) (registers.get(number) & (~(1 << p))));
			}
			addToChangedRegisters(number);
		}
	}

	public static void setBitNoSend(char number,char p, boolean v){
		if (v){
			registers.set(number, (char) (registers.get(number) | 1<<p));
		}else{
			registers.set(number, (char) (registers.get(number) & (~(1 << p))));
		}
	}

	public static void logRegisters(){
		for(int i=0;i<REGISTERS_NUMBER;i++)
			Log.e("TIRAX4","Register "+i+" Value "+(int)registers.get(i));
	}

	public static ArrayList<Character> getAllRegisters() {
		return registers;
	}

	Runnable ResendRunnable = new Runnable(){

		@Override
		public void run() {


			if(resendTimer==1) {
				if (resetTimer == 1) {

					registers.set(DataProvider.RUER,(char)1);
				}
				else {


					sendRegister();
					resetTimer--;
				}
			}
			if(resendTimer>0)
				resendTimer--;
			ResendRunnableHandler.postDelayed(ResendRunnable, 1);

			
		}
	
	
	};


	public static void setJustBit(char register_num, char bit) {
		if(register_num<REGISTERS_NUMBER) {
			for(char i=0;i<8;i++)
			if (i==bit) {
				registers.set(register_num, (char) (registers.get(register_num) | 1 << i));
			} else {
				registers.set(register_num, (char) (registers.get(register_num) & (~(1 << i))));
			}
			addToChangedRegisters(register_num);
		}
	}
}

