package main.activity.Test;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.Tirax.plasma.Enums.SecurityType;
import com.Tirax.plasma.LogCatEnabler;
import com.Tirax.plasma.Manager;
import com.Tirax.plasma.Mode;
import com.Tirax.plasma.MyActivity;
import com.Tirax.plasma.R;
import com.Tirax.plasma.SecurityFile;
import com.Tirax.plasma.SerialPortsHardware.DataProvider;
import com.Tirax.plasma.Storage.Values;
import com.friendlyarm.AndroidSDK.HardwareControler;

import main.activity.EnterPassActivity;
import main.activity.ShowTimes;
import main.activity.StartActivity;

public class TestStopActivity extends MyActivity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_stop);

	}






}
