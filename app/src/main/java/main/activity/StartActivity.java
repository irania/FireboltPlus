package main.activity;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.Tirax.plasma.*;
import com.Tirax.plasma.Compiler;
import com.Tirax.plasma.SerialPortsHardware.DataProvider;
import com.Tirax.plasma.Storage.Pages;
import com.Tirax.plasma.Storage.Values;
import com.Tirax.plasma.R;

import main.activity.Test.TesterActivity;


public class StartActivity extends MyActivity implements OnClickListener {

	public int seekBarProgress;
	public static int time=0;
	public static int frequency;
	Mode op;
		private int powerValue;
		TextView power;
		TextView powerReal;

		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		declareButtons();

		op = Manager.getType();
		TextView mode_text = (TextView) findViewById(R.id.txt_start_autoMode);
		mode_text.setText(op.autoMode);

		setPowerValue();

		power = (TextView) findViewById(R.id.txt_power_start);
		powerReal = (TextView) findViewById(R.id.txt_start_powerEqual);
		power.setText(powerValue + "%");
		powerReal.setText((int)((powerValue*op.powerMultiplyer+op.powerAdder)* DataProvider.powerBase/100) + " pulse/sec");

		initialSeekBar();
		goToCorrectPage();
		
	}

	private void goToCorrectPage() {
		if (DataProvider.getPedalisActive()) {

			Intent int_auto = new Intent(StartActivity.this, TesterActivity.class);
			startActivity(int_auto);

		}
	}

	private void setPowerValue() {

			powerValue = Values.power;
			seekBarProgress=powerValue;
		}


		private void initialSeekBar() {
			SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
			seekBar.setProgress(powerValue);

			seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					if(progress<10) {
						seekBar.setProgress(10);

						seekBarProgress = 10;
					}
					else
						seekBarProgress = progress;

				}


				public void onStartTrackingTouch(SeekBar seekBar) {

				}

				public void onStopTrackingTouch(SeekBar seekBar) {
					power.setText(seekBarProgress + "%");
					powerReal.setText((int)((seekBarProgress*op.powerMultiplyer+op.powerAdder)* DataProvider.powerBase/100) + " pulse/sec");


				}

			});
		}

		private void declareButtons() {
		//declaring main menu buttons
			Button auto=(Button) findViewById(R.id.btn_start);
			Button back=(Button) findViewById(R.id.btn_back_start);
			Button incrPower = (Button) findViewById(R.id.btn_start_power_up);
			Button decrPower = (Button) findViewById(R.id.btn_start_power_down);
			ImageButton settingsBtn =(ImageButton) findViewById(R.id.btn_start_settings);

			//declaring onclicklistener functions
			settingsBtn.setOnClickListener(this);

			incrPower.setOnClickListener(this);
			decrPower.setOnClickListener(this);

			auto.setOnClickListener(this);
			back.setOnClickListener(this);
		}

	@Override
	public void onClick(View arg0) {

		

				//starting corresponding intents
				if (arg0.getId()==R.id.btn_start){
					Values.power = seekBarProgress;
					sendStartCommunicationData();
					//declaring intents

					Intent int_stop=new Intent(StartActivity.this,StopActivity.class);
					startActivity(int_stop);
					this.finish();
				}
				
				if (arg0.getId()==R.id.btn_back_start){
					this.finish();
				}

		if (arg0.getId() == R.id.btn_start_settings) {
			Intent int_settings = new Intent(StartActivity.this,EnterPassActivity.class);
			startActivity(int_settings);
		}

		if(arg0.getId() == R.id.btn_start_power_up){
			addSeekbarPower(5);

		}

		if(arg0.getId() == R.id.btn_start_power_down){
			addSeekbarPower(-5);

		}
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				
	}

	private void addSeekbarPower(int value) {

		seekBarProgress += value;
		seekBarProgress = (seekBarProgress/5)*5;
		if(seekBarProgress> 100 || seekBarProgress<10)
			seekBarProgress -=value;
		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
		seekBar.setProgress(seekBarProgress);
		power.setText(seekBarProgress + "%");
		powerReal.setText((int)((seekBarProgress*op.powerMultiplyer+op.powerAdder)* DataProvider.powerBase/100) + " pulse/sec");

	}

	public void sendStartCommunicationData(){
		try{

			if(LogCatEnabler.startStop)
				Log.e("TIRAX3",">>>>>>>>>>>>>>> START <<<<<<<<<<<<<<<<<");
			Mode op = Manager.getType();

			Compiler.setRegisters(op);

		}
		catch(Exception ex){
			Log.e("EXCEPTION TIRAX",ex+"");
		}
	}

	

}
