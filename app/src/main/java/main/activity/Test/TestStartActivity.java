package main.activity.Test;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.Tirax.plasma.Compiler;
import com.Tirax.plasma.LogCatEnabler;
import com.Tirax.plasma.Manager;
import com.Tirax.plasma.Mode;
import com.Tirax.plasma.MyActivity;
import com.Tirax.plasma.Options.TestOption;
import com.Tirax.plasma.R;
import com.Tirax.plasma.SerialPortsHardware.DataProvider;
import com.Tirax.plasma.Storage.Values;

import main.activity.EnterPassActivity;
import main.activity.StopActivity;


public class TestStartActivity extends MyActivity implements OnClickListener {


		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_start);

		declareButtons();
		
	}


		private void declareButtons() {
		//declaring main menu buttons
			Button auto=(Button) findViewById(R.id.btn_start);
			Button back=(Button) findViewById(R.id.btn_back_start);

			//declaring onclicklistener function
			auto.setOnClickListener(this);
			back.setOnClickListener(this);
		}

	@Override
	public void onClick(View arg0) {


				//starting corresponding intents
				if (arg0.getId()==R.id.btn_start){
					Values.power = 100;
					sendStartCommunicationData();
					//declaring intents

					Intent int_stop=new Intent(TestStartActivity.this,TestStopActivity.class);
					startActivity(int_stop);
					this.finish();
				}
				
				if (arg0.getId()==R.id.btn_back_start){
					this.finish();
				}

		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				
	}


	public void sendStartCommunicationData(){
		try{

			if(LogCatEnabler.startStop)
				Log.e("TIRAX3",">>>>>>>>>>>>>>> START <<<<<<<<<<<<<<<<<");
			Mode op = new TestOption();
			Compiler.setRegisters(op);

		}
		catch(Exception ex){
			Log.e("EXCEPTION TIRAX",ex+"");
		}
	}

	

}
