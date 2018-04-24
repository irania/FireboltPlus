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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Tirax.plasma.Enums.ShotTypes;
import com.Tirax.plasma.Manager;
import com.Tirax.plasma.Mode;
import com.Tirax.plasma.MyActivity;
import com.Tirax.plasma.MyDeviceAdminReciver;
import com.Tirax.plasma.SerialPortsHardware.DataProvider;
import com.Tirax.plasma.R;
import com.Tirax.plasma.Storage.Values;
import me.rorschach.library.ShaderSeekArc;


public class MainSettings extends MyActivity implements OnClickListener {

	private boolean shotAutoIncrement = false;
	private boolean shotAutoDecrement = false;
	private boolean pulseAutoIncrement = false;
	private boolean pulseAutoDecrement = false;
	private boolean pulseIsActive = false;
	private boolean shotIsActive = false;
	private Handler repeatUpdateHandler = new Handler();
	private TextView powerText ;
	private TextView LengthText;
	private TextView shotText;
	private TextView pulseText;
	public int powerValue;
	public int lengthValue;
	public int shotValue;
	public int pulseValue;
	public boolean started=false;
	private Integer pedalTime=0;
	private boolean isLocked=false;
	private Handler UIHandler = new Handler();
	public int PedalWasActive=0;
	public boolean finished=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_settings);

		powerText = (TextView) findViewById(R.id.txt_power);
		LengthText = (TextView) findViewById(R.id.txt_length);
		shotText = (TextView) findViewById(R.id.txt_shot);
		pulseText = (TextView) findViewById(R.id.txt_pulse);

		finished=false;
		powerValue =10;
		lengthValue =20;
		shotValue =10;
		pulseValue =10;

		updateLengthValue();
		updatePowerValue();
		updateShotValue();
		updatePulseValue();

		Button setting=(Button) findViewById(R.id.btn_settings);
		Button ready=(Button) findViewById(R.id.btn_ready_pause);
		Button back = (Button) findViewById(R.id.btn_back);

		setting.setOnClickListener(this);
		ready.setOnClickListener(this);
		back.setOnClickListener(this);

		initializeShotButtons();
		initializePulseButtons();
		initialSeekArc();
		initializeTypeButtons();

		//spray activation
		ImageView img = (ImageView) findViewById(R.id.img_shotype) ;
		img.setImageResource(R.drawable.sprayactive);
		UIHandler.postDelayed(UIreportsRunnable, 0);
	}

	private void initializeTypeButtons() {
		Button  sprayBtn = (Button) findViewById(R.id.btn_spray);
		Button  dotBtn = (Button) findViewById(R.id.btn_dot);
		Button  pulseBtn = (Button) findViewById(R.id.btn_pulse);

		sprayBtn.setOnClickListener(this);
		dotBtn.setOnClickListener(this);
		pulseBtn.setOnClickListener(this);
	}

	private void initializeShotButtons() {
		//declaring main menu buttons
		Button addShot=(Button) findViewById(R.id.btn_inc_shot);
		Button decShot=(Button) findViewById(R.id.btn_dec_shot);
		//reshot increase
		addShot.setOnLongClickListener(
				new View.OnLongClickListener() {
					public boolean onLongClick(View arg0) {
						shotAutoIncrement = true;
						repeatUpdateHandler.post(new RptShotUpdater());
						return false;
					}
				}
		);

		addShot.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
						&& shotAutoIncrement) {
					shotAutoIncrement = false;
				} else if (event.getAction() == MotionEvent.ACTION_DOWN)
					incrementShot();
				return false;
			}


		});

		//reshot decrease
		decShot.setOnLongClickListener(
				new View.OnLongClickListener() {
					public boolean onLongClick(View arg0) {
						shotAutoDecrement = true;
						repeatUpdateHandler.post(new RptShotUpdater());
						return false;
					}
				}
		);

		decShot.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
						&& shotAutoDecrement) {
					shotAutoDecrement = false;
				} else if (event.getAction() == MotionEvent.ACTION_DOWN)
					decrementShot();
				return false;
			}


		});

	}
	private void initializePulseButtons() {
		//declaring main menu buttons
		Button addPulse=(Button) findViewById(R.id.btn_inc_pulse);
		Button decPulse=(Button) findViewById(R.id.btn_dec_pulse);
		//pulse increase
		addPulse.setOnLongClickListener(
				new View.OnLongClickListener() {
					public boolean onLongClick(View arg0) {
						pulseAutoIncrement = true;
						repeatUpdateHandler.post(new RptPulseUpdater());
						return false;
					}
				}
		);

		addPulse.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
						&& pulseAutoIncrement) {
					pulseAutoIncrement = false;
				} else if (event.getAction() == MotionEvent.ACTION_DOWN)
					incrementPulse();
				return false;
			}


		});

		//pulse decrease
		decPulse.setOnLongClickListener(
				new View.OnLongClickListener() {
					public boolean onLongClick(View arg0) {
						pulseAutoDecrement = true;
						repeatUpdateHandler.post(new RptPulseUpdater());
						return false;
					}
				}
		);

		decPulse.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
						&& pulseAutoDecrement) {
					pulseAutoDecrement = false;
				} else if (event.getAction() == MotionEvent.ACTION_DOWN)
					decrementPulse();
				return false;
			}


		});

	}
	private void initialSeekArc() {
		ShaderSeekArc seekArcPower = (ShaderSeekArc) findViewById(R.id.seek_arc_power);
		ShaderSeekArc seekArcLength = (ShaderSeekArc) findViewById(R.id.seek_arc_length);

		seekArcPower.setEndColor(0xffc60924);
		seekArcLength.setEndColor(0xffc60924);
		seekArcPower.setStartColor(0xff7408b4);
		seekArcLength.setStartColor(0xff7408b4);

		// notice the endValue must large than startValue,
		// and progress should between them
		seekArcPower.setStartValue(0);
		seekArcPower.setEndValue(100);
		seekArcPower.setProgress(powerValue);
		seekArcLength.setStartValue(0);
		seekArcLength.setEndValue(100);
		seekArcLength.setProgress(lengthValue);

		// notice the endAngle must large than startAngle in math,
		// and (endAngle - startAngle) should less　than or equals 360
		seekArcPower.setStartAngle(-190);
		seekArcLength.setStartAngle(-190);
		seekArcPower.setEndAngle(80);
		seekArcLength.setEndAngle(80);

		seekArcPower.setOnSeekArcChangeListener(new ShaderSeekArc.OnSeekArcChangeListener() {

			@Override
			public void onProgressChanged(ShaderSeekArc seekArc, float progress) {
				Log.d("TIRAXTEST", "progress " + progress);
				powerValue = (int) progress;
				updatePowerValue();
			}

			@Override
			public void onStartTrackingTouch(ShaderSeekArc seekArc) {
				Log.d("TIRAXTEST", "onStartTrackingTouch");
			}

			@Override
			public void onStopTrackingTouch(ShaderSeekArc seekArc) {
				Log.d("TIRAXTEST", "onStopTrackingTouch");
				updatePowerValue();
			}
		});
		seekArcPower.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (PedalWasActive > 0)
					return true;
				else
					return false;
			}
		});
		seekArcLength.setOnSeekArcChangeListener(new ShaderSeekArc.OnSeekArcChangeListener() {

			@Override
			public void onProgressChanged(ShaderSeekArc seekArc, float progress) {

				lengthValue = (int) progress;
				updateLengthValue();
			}

			@Override
			public void onStartTrackingTouch(ShaderSeekArc seekArc) {

			}

			@Override
			public void onStopTrackingTouch(ShaderSeekArc seekArc) {
				updateLengthValue();
			}
		});
		seekArcLength.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (PedalWasActive > 0)
					return true;
				else
					return false;
			}
		});
	}

	private void updateLengthValue() {
		LengthText.setText("" + (lengthValue));
		if(started);
			DataProvider.setRegister(DataProvider.RDPTH, (char) lengthValue);
	}

	private void updatePowerValue() {
		powerText.setText(""+powerValue);
		if(started) {
			Mode mode = Manager.getType();
			DataProvider.setRegister(DataProvider.RPWR, (char) (powerValue*mode.powerMultiplyer+mode.powerAdder));
		}
	}
	private void updatePulseValue() {
		if(pulseIsActive) {
			pulseText.setText("" + (double) (pulseValue / 10.0));
			if (started) {
				DataProvider.setRegister(DataProvider.RFDLY, (char) (pulseValue));
			}
		}else{
			pulseText.setText("--");
			if (started) ;
				DataProvider.setRegister(DataProvider.RFDLY, (char) 0);
		}
	}
	private void updateShotValue() {
		if(shotIsActive) {
			shotText.setText("" + (double) (shotValue / 10.0));
			if (started)
				DataProvider.setRegister(DataProvider.RFRSHt, (char) shotValue);
		}else{
			shotText.setText("--");
			if (started) ;
				DataProvider.setRegister(DataProvider.RFRSHt, (char) 0);
		}
	}


	public void decrementShot(){
		if(shotIsActive && PedalWasActive<=0) {
			shotValue -= 5;
			if (shotValue < 0) {
				shotValue = 0;
			}
			updateShotValue();
		}

	}
	public void incrementShot(){
		if(shotIsActive && PedalWasActive<=0) {
			shotValue += 5;
			if (shotValue == 0)
				shotValue = 2;
			if (shotValue > 50)
				shotValue = 50;
			updateShotValue();
		}
	}
	public void decrementPulse(){
		if(pulseIsActive && PedalWasActive<=0) {
			pulseValue--;
			if (pulseValue < 0) {
				pulseValue = 0;
			}
			updatePulseValue();
		}

	}
	public void incrementPulse(){
		if(pulseIsActive && PedalWasActive<=0) {
			pulseValue++;
			if (pulseValue > 20)
				pulseValue = 20;
			updatePulseValue();
		}
	}

	@Override
	public void onClick(View arg0) {
		if(PedalWasActive<=0) {
			if (arg0.getId() == R.id.btn_back)
				this.finish();
			if (arg0.getId() == R.id.btn_spray) {
				Values.shotType = ShotTypes.SPRAY;
				ImageView img = (ImageView) findViewById(R.id.img_shotype);
				img.setImageResource(R.drawable.sprayactive);
				pulseIsActive = false;
				shotIsActive = false;
				updatePulseValue();
				updateShotValue();
			}
			if (arg0.getId() == R.id.btn_dot) {
				Values.shotType = ShotTypes.DOT;
				ImageView img = (ImageView) findViewById(R.id.img_shotype);
				img.setImageResource(R.drawable.dotactive);
				pulseIsActive = true;
				shotIsActive = true;
				updatePulseValue();
				updateShotValue();
			}
			if (arg0.getId() == R.id.btn_pulse) {
				Values.shotType = ShotTypes.PULSE;
				ImageView img = (ImageView) findViewById(R.id.img_shotype);
				img.setImageResource(R.drawable.pulseactive);
				pulseIsActive = true;
				shotIsActive = false;
				updatePulseValue();
				updateShotValue();
			}
			if (arg0.getId() == R.id.btn_settings) {
				Intent int_next = new Intent(MainSettings.this, EnterPassActivity.class);
				startActivity(int_next);
			}
			if (arg0.getId() == R.id.btn_ready_pause) {
				//DO start functions
				RelativeLayout back = (RelativeLayout) findViewById(R.id.background_RelativeLayout);
				if (!started) {
					Values.shot = shotIsActive ? this.shotValue : 0;
					Values.length = this.lengthValue;
					Values.power = this.powerValue;
					Values.pulse = pulseIsActive ? this.pulseValue : 0;

					Mode op = Manager.getType();
					com.Tirax.plasma.Compiler.setRegisters(op);
					started = true;
					back.setBackgroundDrawable(getResources().getDrawable(R.drawable.fractionalmainpause));
				} else {
					started = false;
					back.setBackgroundDrawable(getResources().getDrawable(R.drawable.fractionalmainready));
					DataProvider.setRegister(DataProvider.RPWR, (char) 0);
				}
			}

			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		}
				
	}


	class RptShotUpdater implements Runnable {
		private static final long REP_DELAY = 5;

		public void run() {
			if(shotAutoIncrement){

				incrementShot();
				repeatUpdateHandler.postDelayed( new RptShotUpdater(), REP_DELAY );
			} else if(shotAutoDecrement) {
				decrementShot();
				repeatUpdateHandler.postDelayed(new RptShotUpdater(), REP_DELAY);
			}
		}
	}

	class RptPulseUpdater implements Runnable {
		private static final long REP_DELAY = 5;

		public void run() {
			if(pulseAutoIncrement){

				incrementPulse();
				repeatUpdateHandler.postDelayed( new RptPulseUpdater(), REP_DELAY );
			} else if(pulseAutoDecrement) {
				decrementPulse();
				repeatUpdateHandler.postDelayed(new RptPulseUpdater(), REP_DELAY);
			}
		}
	}
	Runnable UIreportsRunnable = new Runnable() {

		@Override
		public void run() {


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
