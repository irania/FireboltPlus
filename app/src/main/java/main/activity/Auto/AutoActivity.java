package main.activity.Auto;




import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.Tirax.DeviceAdminReceiver;
import com.Tirax.plasma.MyActivity;
import com.Tirax.plasma.MyDeviceAdminReciver;
import com.Tirax.plasma.Storage.Pages;
import com.Tirax.plasma.R;
import com.Tirax.plasma.Storage.Values;

import main.activity.EnterPassActivity;
import main.activity.MainSettings;
import main.activity.StartActivity;
import main.activity.Test.TestStartActivity;


public class AutoActivity extends MyActivity implements OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plasma_auto);

        //declaring main menu buttons
        Button acne=(Button) findViewById(R.id.btn_acne);
        Button blepharo=(Button) findViewById(R.id.btn_blepharoplasty);
        Button scar=(Button) findViewById(R.id.btn_scar);
        Button mole = (Button) findViewById(R.id.btn_mole);
        ImageButton test = (ImageButton) findViewById(R.id.btn_auto_test);
        ImageButton settingsBtn =(ImageButton) findViewById(R.id.btn_auto_settings);

        //declaring onclicklistener functions
        acne.setOnClickListener(this);
        blepharo.setOnClickListener(this);
        scar.setOnClickListener(this);
        mole.setOnClickListener(this);
        settingsBtn.setOnClickListener(this);
        test.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {

        //declaring intents
        Intent int_start=new Intent(AutoActivity.this,MainSettings.class);
        Pages.step = Pages.LOW;
        Values.power = 10;

        //starting corresponding intents
        if (arg0.getId()==R.id.btn_acne){
            Pages.auto_type = Pages.ACNE;
            startActivity(int_start);
        }

        if (arg0.getId()==R.id.btn_blepharoplasty){
            Pages.auto_type = Pages.BLEPHARO;
            startActivity(int_start);
        }

        if (arg0.getId()==R.id.btn_scar){
            Pages.auto_type = Pages.SCAR;
            startActivity(int_start);
        }

        if(arg0.getId() == R.id.btn_mole){
            Pages.auto_type = Pages.MOLE;
            startActivity(int_start);
        }

        if(arg0.getId() == R.id.btn_auto_test){
            Intent int_test = new Intent(AutoActivity.this, TestStartActivity.class);
            startActivity(int_test);
        }

        if (arg0.getId() == R.id.btn_auto_settings) {
            Intent int_settings = new Intent(AutoActivity.this, EnterPassActivity.class);
            startActivity(int_settings);
        }
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


}
