package main.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.Tirax.plasma.MyActivity;
import com.Tirax.plasma.R;


import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShowLogCat extends MyActivity implements View.OnClickListener {
    int backpressed=0;
    public boolean finished=false;
    private Handler timerHandler = new Handler();
    private String filterValue="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logcat);
        Button con=(Button)findViewById(R.id.btn_logcat_filter);
        Button send=(Button)findViewById(R.id.btn_filter_send);
        Button rec=(Button)findViewById(R.id.btn_filter_rec);
        Button tirax=(Button)findViewById(R.id.btn_filter_tirax);
        Button clear=(Button)findViewById(R.id.btn_filter_clear);
        Button back=(Button)findViewById(R.id.btn_filter_back);
        Button refresh=(Button)findViewById(R.id.btn_filter_refresh);

        con.setOnClickListener(this);
        send.setOnClickListener(this);
        rec.setOnClickListener(this);
        tirax.setOnClickListener(this);
        clear.setOnClickListener(this);
        back.setOnClickListener(this);
        refresh.setOnClickListener(this);

        showLog("");

        timerHandler.postDelayed(TimerRunnable, 0);
    }

    private void showLog(String filterVal) {
        try {

            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            StringBuilder log=new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {

                if(line.contains(filterVal)) {
                    log.insert(0,line+"\n");

                }

            }
            TextView tv = (TextView)findViewById(R.id.textView1);
            tv.setMovementMethod(new ScrollingMovementMethod());
            tv.setText(log.toString());
            tv.scrollTo(0,0);
        } catch (IOException e) {
        }
    }

    @Override
    public void onBackPressed() {
        //disable button back
        backpressed++;
        if(backpressed>1)
        {
            try {
                Runtime.getRuntime().exec("logcat -c");
                this.finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
            backpressed=0;
        }
    }

    @Override
    public void onClick(View v) {
        if(R.id.btn_logcat_filter == v.getId()) {
            showLog(((EditText)findViewById(R.id.txt_filter)).getText().toString());
        }
        if(R.id.btn_filter_send == v.getId()) {
            filterValue = "TIRAX1";
            showLog(filterValue);
        }
        if(R.id.btn_filter_rec == v.getId()) {
            filterValue = "TIRAX4";
            showLog(filterValue);
        }
        if(R.id.btn_filter_tirax == v.getId()) {
            filterValue = "TIRAX";
            showLog(filterValue);
        }
        if(R.id.btn_filter_clear == v.getId()) {
            ((TextView)findViewById(R.id.textView1)).setText("");
        }
        if(R.id.btn_filter_refresh == v.getId()) {
            filterValue = "";
            ((TextView)findViewById(R.id.textView1)).setText("");
            showLog(filterValue);
        }
        if(R.id.btn_filter_back == v.getId()) {
            finished=true;
            this.finish();
        }
    }


    Runnable TimerRunnable = new Runnable() {

        @Override
        public void run() {

            showLog(filterValue);
            if(!finished)
                timerHandler.postDelayed(TimerRunnable, 60000);
        }
    };
}
