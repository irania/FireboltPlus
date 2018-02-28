package main.activity.Test;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Tirax.plasma.Enums.TestType;
import com.Tirax.plasma.MyActivity;
import com.Tirax.plasma.SerialPortsHardware.DataProvider;
import com.Tirax.plasma.Test.TestPulse;
import com.Tirax.plasma.Test.TestRun0t100;
import com.Tirax.plasma.Test.TestSerialBrief;
import com.Tirax.plasma.R;
import com.Tirax.plasma.Test.TestShower;


public class TestResult extends MyActivity implements View.OnLongClickListener {

    protected static String log="";
    protected  static  String log_header="";
    private TestThread t = new TestThread();

    public static TestType test;

     public static void appendLog(String s){
         log=log.concat(s);
     }
    public static void setLog(String s){
        log=s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        Button back  = (Button) findViewById(R.id.btn_testres_back);
        back.setOnLongClickListener(this);

        log="";
        t.start();
        UIHandler.postDelayed(UpdateUI, 0);
    }


    private Handler UIHandler = new Handler();
    Runnable UpdateUI = new Runnable() {

        @Override
        public void run() {
            TextView log = (TextView) findViewById(R.id.txt_testres_log);
            log.setText(TestResult.log_header+TestResult.log);
            UIHandler.postDelayed(UpdateUI, 100);
        }
    };

    @Override
    public boolean onLongClick(View v) {
        if(v.getId() == R.id.btn_testres_back){
            t.interrupt();
            this.finish();
        }
        return false;
    }

    public class TestThread extends Thread {
        public void run() {
                switch (TestResult.test){
                    case SERIAL_BRIEF:{
                        TestSerialBrief tsb = new TestSerialBrief();
                        TestResult.log_header="Brief Serial Communication Test Result:\n";
                        tsb.Run();
                        break;
                    }
                    case POWER0T100:{
                        TestRun0t100 tsb = new TestRun0t100();
                        TestResult.log_header="Power 0 -> 100 Test Result:\n";
                        tsb.Run();
                        break;
                    }
                    case SHOWER: {
                        TestShower tsb = new TestShower();
                        TestResult.log_header="Shower Test Result:\n";
                        tsb.Run();
                        break;
                    }
                    case Pulse: {
                        TestPulse tsb = new TestPulse();
                        TestResult.log_header="Pulse Test Result:\n";
                        tsb.Run();
                        break;
                    }
                }
                DataProvider.zeroAllRegisters();
        }
    };
}


