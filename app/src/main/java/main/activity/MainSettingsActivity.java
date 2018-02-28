package main.activity;
import com.Tirax.plasma.R;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Tirax.plasma.Enums.TestType;
import com.Tirax.plasma.MyActivity;

import main.activity.Test.TestResult;
import main.activity.Test.TesterActivity;


/**
 * Created by a.irani on 12/15/2016.
 */
public class MainSettingsActivity extends MyActivity implements View.OnClickListener {

    Activity thisActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main_page);

        Button back = (Button)findViewById(R.id.btn_settings_back);
        Button exit = (Button)findViewById(R.id.btn_settings_exit);
        Button log = (Button)findViewById(R.id.btn_settings_logs);
        Button time = (Button)findViewById(R.id.btn_settings_time);
        Button test = (Button)findViewById(R.id.btn_settings_test);
        Button serial = (Button)findViewById(R.id.btn_settings_serial);


        back.setOnClickListener(this);
        exit.setOnClickListener(this);
        log.setOnClickListener(this);
        serial.setOnClickListener(this);
        test.setOnClickListener(this);
        time.setOnClickListener(this);


        thisActivity = this;


    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_settings_back){
            this.finish();
        }
        if(v.getId()==R.id.btn_settings_exit){
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);

        }
        if(v.getId()==R.id.btn_settings_logs){
            Intent int_settings = new Intent(MainSettingsActivity.this,ShowLogCat.class);
            startActivity(int_settings);

        }
        if(v.getId()==R.id.btn_settings_time){
            Intent int_settings = new Intent(MainSettingsActivity.this,ShowTimes.class);
            startActivity(int_settings);
        }


        if (v.getId()==R.id.btn_settings_test){
            Intent int_settings = new Intent(MainSettingsActivity.this,TesterActivity.class);
            startActivity(int_settings);
        }

        if (v.getId()==R.id.btn_settings_serial){
            Intent int_settings = new Intent(MainSettingsActivity.this,ShowSerialNumber.class);
            startActivity(int_settings);
        }
    }
}
