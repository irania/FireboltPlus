package main.activity.Test;
import com.Tirax.plasma.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Tirax.plasma.Enums.TestType;
import com.Tirax.plasma.MyActivity;

import main.activity.Test.TestResult;


/**
 * Created by a.irani on 12/15/2016.
 */
public class TesterActivity extends MyActivity implements View.OnLongClickListener {

    Activity thisActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tester);

        Button back = (Button)findViewById(R.id.btn_settings_back);
        Button serial_brief_test = (Button)findViewById(R.id.btn_settings_serialtext);
        Button power0t100_test = (Button)findViewById(R.id.btn_settings_power0t100);
        Button shower_test = (Button)findViewById(R.id.btn_settings_showertest);
        Button pulse_test = (Button)findViewById(R.id.btn_settings_pulsetest);

        back.setOnLongClickListener(this);
        serial_brief_test.setOnLongClickListener(this);
        power0t100_test.setOnLongClickListener(this);
        shower_test.setOnLongClickListener(this);
        pulse_test.setOnLongClickListener(this);



        thisActivity = this;


    }


    @Override
    public boolean onLongClick(View v) {
        if(v.getId()==R.id.btn_settings_back){
            this.finish();
        }


        if (v.getId()==R.id.btn_settings_power0t100){
            Intent int_settings = new Intent(TesterActivity.this,TestResult.class);
            TestResult.test = TestType.POWER0T100;
            startActivity(int_settings);
        }

        if (v.getId()==R.id.btn_settings_serialtext){
            Intent int_settings = new Intent(TesterActivity.this,TestResult.class);
            TestResult.test = TestType.SERIAL_BRIEF;
            startActivity(int_settings);
        }

        if (v.getId()==R.id.btn_settings_showertest){
            Intent int_settings = new Intent(TesterActivity.this,TestResult.class);
            TestResult.test = TestType.SHOWER;
            startActivity(int_settings);
        }
        if (v.getId()==R.id.btn_settings_pulsetest){
            Intent int_settings = new Intent(TesterActivity.this,TestResult.class);
            TestResult.test = TestType.Pulse;
            startActivity(int_settings);
        }


        return false;
    }
}
