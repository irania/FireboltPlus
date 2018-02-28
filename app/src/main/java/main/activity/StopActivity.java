package main.activity;



import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.Tirax.plasma.*;
import com.Tirax.plasma.Enums.SecurityType;
import com.Tirax.plasma.SerialPortsHardware.DataProvider;

import com.Tirax.plasma.Storage.Values;
import com.friendlyarm.AndroidSDK.HardwareControler;
import com.Tirax.plasma.R;

import main.activity.Auto.AutoActivity;

public class StopActivity extends MyActivity implements OnClickListener {


	private Handler timerHandler = new Handler();
	private Handler UIHandler = new Handler();
	public boolean firstUI=false;

	public  int time=10;
	public static int firstTime;

	private TextView power;
	private TextView powerReal;
	private TextView timetext;
	private TextView vacuum;

	private int backpressed;
	public boolean finished=true;
	private boolean mute = false;
	private boolean pause = false;
	public int PedalWasActive=0;
	private ImageButton stopButton;

	private Handler repeatUpdateHandler = new Handler();
	public int powerValue;
	public int seekBarProgress = 0;

	private Mode op;

	private int powerBase = DataProvider.powerBase;
	private Integer pedalTime=0;
	private boolean isLocked=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stop);
		initialTextViews();
		finished=false;
		firstTime=time;
		backpressed=0;
		//declaring main menu buttons
		setButtons();

		initialSeekBar();

		timerHandler.postDelayed(TimerRunnable, 0);
		UIHandler.postDelayed(UIreportsRunnable, 0);
	}

	private void initialSeekBar() {
		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
		seekBar.setProgress(powerValue);
		seekBarProgress = powerValue;

		seekBar.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (PedalWasActive > 0)
					return true;
				else
					return false;
			}
		});
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (progress < 10) {
					seekBar.setProgress(10);
					seekBarProgress = 10;
				} else
					seekBarProgress = progress;
				power.setText(seekBarProgress + "%");
				powerReal.setText((int)((seekBarProgress*op.powerMultiplyer+op.powerAdder)*powerBase/100) + " pulse/sec");


			}


			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				power.setText(seekBarProgress + "%");
				powerReal.setText((int)((seekBarProgress*op.powerMultiplyer+op.powerAdder)*powerBase/100) + " pulse/sec");
				DataProvider.setRegister(DataProvider.RPWR, (char) (seekBarProgress * op.powerMultiplyer+op.powerAdder));

			}

		});
	}

	private void setButtons() {
		stopButton =(ImageButton) findViewById(R.id.btn_stop);
		stopButton.setOnClickListener(this);

		Button incrPower = (Button) findViewById(R.id.btn_stop_power_up);
		Button decrPower = (Button) findViewById(R.id.btn_stop_power_down);
		Button back=(Button) findViewById(R.id.btn_stop_back);

		back.setOnClickListener(this);
		incrPower.setOnClickListener(this);
		decrPower.setOnClickListener(this);

		ImageButton settingsBtn =(ImageButton) findViewById(R.id.btn_stop_settings);
		settingsBtn.setOnClickListener(this);
	}


	private void initialTextViews() {
		//read temp datas and show
		power = (TextView) findViewById(R.id.txt_stop_power);
		powerReal = (TextView) findViewById(R.id.txt_stop_powerEqual);
		timetext = (TextView) findViewById(R.id.txt_time_stop);


		op = Manager.getType();

		TextView mode_text = (TextView) findViewById(R.id.txt_stop_autoMode);
		mode_text.setText(op.autoMode);

		power.setText(op.power + "%");
		powerReal.setText((int)((op.power*op.powerMultiplyer+op.powerAdder)*powerBase/100) + " pulse/sec");

		powerValue=op.power;



		time = 0;


	}

	private void addSeekbarPower(int value) {

		seekBarProgress += value;
		seekBarProgress = (seekBarProgress/5)*5;
		if(seekBarProgress> 100 || seekBarProgress<5)
			seekBarProgress -=value;
		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
		seekBar.setProgress(seekBarProgress);
		power.setText(seekBarProgress + "%");
		powerReal.setText((int)((seekBarProgress*op.powerMultiplyer+op.powerAdder)*powerBase/100) + " pulse/sec");
		DataProvider.setRegister(DataProvider.RPWR, (char) (seekBarProgress * op.powerMultiplyer+op.powerAdder));

	}
	@Override
	public void onClick(View arg0) {
		//starting corresponding intents

		Log.e("TIRAXDebug","----click on touch "+arg0.getId()+"pedal value "+PedalWasActive);
		if(PedalWasActive<=0) {
			if (arg0.getId() == R.id.btn_stop) {

				Values.power = seekBarProgress;
				finishFunction();
				Intent int_auto = new Intent(StopActivity.this, StartActivity.class);
				startActivity(int_auto);
			}

			if (arg0.getId() == R.id.btn_stop_settings) {
				Intent int_settings = new Intent(StopActivity.this, EnterPassActivity.class);
				startActivity(int_settings);
			}

			if(arg0.getId() == R.id.btn_stop_power_up){
				addSeekbarPower(5);

			}

			if(arg0.getId() == R.id.btn_stop_power_down){
				addSeekbarPower(-5);

			}
			if (arg0.getId()==R.id.btn_stop_back){
				finishFunction();

			}
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		}

	}

	private static Integer tryParse(String text) {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return 0;
		}
	}


	private void finishFunction() {

		if(LogCatEnabler.startStop)
			Log.e("TIRAX3",">>>>>>>>>>>>>>> STOP <<<<<<<<<<<<<<<<<");

		finished=true;
		DataProvider.setRegister(DataProvider.RPWR, (char) 0);

		try {

			SecurityFile.save(SecurityType.TIME, ( tryParse(SecurityFile.load(SecurityType.TIME))+ time)+"");
			SecurityFile.save(SecurityType.PEDAL_TIME, ( tryParse(SecurityFile.load(SecurityType.PEDAL_TIME))+ pedalTime/60000)+"");
		} catch (Exception e) {

			//TODO get a big error
		}

		this.finish();
	}

	 @Override
	 public void onBackPressed() {
		 backpressed++;
		 if(backpressed>3)
		 {
			 Intent int_temps=new Intent(StopActivity.this,ShowTimes.class);
			 startActivity(int_temps);
			 backpressed=0;
			 finished=true;
			 this.finish();
		 }
	 }

	//Stop When Timer Finished
	Runnable TimerRunnable = new Runnable() {

        @Override
        public void run() {

//			SharedPrefrences.setTime(time);
			timetext = (TextView) findViewById(R.id.txt_time_stop);
        	timetext.setText(time+"'");
			if(!pause)
        		time++;
        	if(!finished)
        		timerHandler.postDelayed(TimerRunnable, 60000);
        }
    };

	private void playEndMusic() {
		if(!mute && !finished) {
			for (int i = 0; i < 2; i++) {
				HardwareControler.PWMPlay(2000);
				android.os.SystemClock.sleep(1000);
				HardwareControler.PWMStop();

			}
		}

	}



	private int inrunnable=0;
	Runnable UIreportsRunnable = new Runnable() {

		@Override
		public void run() {

			ImageButton powerRange = (ImageButton)findViewById(R.id.btn_stop_power_range);
			if(DataProvider.getPedalisActive()) {
				powerRange.getLayoutParams().height = (10 - (int)Math.ceil((double)(seekBarProgress * op.powerMultiplyer) / 10.0)) * 20;
			}
			else
				powerRange.getLayoutParams().height = (10)*20;

			if(DataProvider.getPedalisActive()){
				if(PedalWasActive<=0)
					lock();
				PedalWasActive=100;
				pedalTime++;

			}
			else{
				PedalWasActive--;
			}
			if(PedalWasActive<=0 && isLocked)
				unlock();
			timetext.setText((time-1)+"'");
			if(!finished)
				UIHandler.postDelayed(UIreportsRunnable, 1);
		}

	};

	private void lock() {


		//Lock device
		// Enable the Administrator mode.

		DevicePolicyManager deviceManger = (DevicePolicyManager) getSystemService(
				Context.DEVICE_POLICY_SERVICE );
		ActivityManager activityManager = (ActivityManager) getSystemService(
				Context.ACTIVITY_SERVICE );
		ComponentName compName = new ComponentName( getApplicationContext( ),
				MyDeviceAdminReciver.class);
		Intent device_policy_manager_Int = new Intent( DevicePolicyManager
				.ACTION_ADD_DEVICE_ADMIN );
		device_policy_manager_Int.putExtra( DevicePolicyManager.EXTRA_DEVICE_ADMIN,
				compName );
		device_policy_manager_Int.putExtra( DevicePolicyManager.EXTRA_ADD_EXPLANATION,
				"Additional text explaining why this needs to be added." );
		startActivityForResult(device_policy_manager_Int, 1);

		// Check if the Administrator is enabled.
		boolean active = deviceManger.isAdminActive(compName);

		if ( active ) {
			Log.i("isAdminActive", "Admin enabled!");

			// If admin is enable - Lock device.
			deviceManger.lockNow( );
		}

		isLocked = true;
	}

	private void unlock(){
		//Unlock
		KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		final KeyguardManager.KeyguardLock kl = km .newKeyguardLock("MyKeyguardLock");
		kl.disableKeyguard();

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
		wakeLock.acquire();
		isLocked=false;
	}




}
