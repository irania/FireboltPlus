package main.activity;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Tirax.plasma.MyActivity;
import com.Tirax.plasma.R;
import com.Tirax.plasma.SerialPortsHardware.DataProvider;


public class EnterPassActivity extends MyActivity implements  View.OnClickListener {

    TextView pass_textview;
    String pass_txt="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pass);

        assignKeys();

        pass_textview = (TextView) findViewById(R.id.txt_pass);
        setPassText();

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            TextView vt = (TextView) findViewById(R.id.txt_pass_version);
            vt.setText("LCD Version: "+version+" ,Micro Version: "+ (int)DataProvider.getRegister(DataProvider.MVE));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void setPassText() {
        String txt="";
        for(int i=0;i<pass_txt.length();i++)
        {
            txt+="*";
        }
        pass_textview.setText(txt);
    }


    private void assignKeys() {
        //declaring main menu buttons
        Button one=(Button) findViewById(R.id.button1);
        Button two=(Button) findViewById(R.id.button2);
        Button three=(Button) findViewById(R.id.button3);
        Button four=(Button) findViewById(R.id.button4);
        Button fiv=(Button) findViewById(R.id.button5);
        Button six=(Button) findViewById(R.id.button6);
        Button svn=(Button) findViewById(R.id.button7);
        Button eit=(Button) findViewById(R.id.button8);
        Button nine=(Button) findViewById(R.id.button9);
        Button zero=(Button) findViewById(R.id.button0);

        Button clear=(Button) findViewById(R.id.btn_clear);
        Button next=(Button) findViewById(R.id.btn_next_pass);
        Button back=(Button) findViewById(R.id.btn_back_pass);


        //declaring onclicklistener functions
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        fiv.setOnClickListener(this);
        six.setOnClickListener(this);
        svn.setOnClickListener(this);
        eit.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);

        clear.setOnClickListener(this);
        next.setOnClickListener(this);
        back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.button0){
            pass_txt +="0";
        }
        if (v.getId()==R.id.button1){
            pass_txt +="1";
        }
        if (v.getId()==R.id.button2){
            pass_txt +="2";
        }
        if (v.getId()==R.id.button3){
            pass_txt +="3";
        }
        if (v.getId()==R.id.button4){
            pass_txt +="4";
        }
        if (v.getId()==R.id.button5){
            pass_txt +="5";
        }
        if (v.getId()==R.id.button6){
            pass_txt +="6";
        }
        if (v.getId()==R.id.button7){
            pass_txt +="7";
        }
        if (v.getId()==R.id.button8){
            pass_txt +="8";
        }
        if (v.getId()==R.id.button9){
            pass_txt +="9";
        }
        if (v.getId()==R.id.btn_clear){
            if(pass_txt.length()>0)
                pass_txt = pass_txt.substring(0,pass_txt.length()-1);
        }
        setPassText();
        if (v.getId()==R.id.btn_back_pass){
            this.finish();
        }
        if (v.getId()==R.id.btn_next_pass){
            if(pass_txt.equals("1394")) {
                Intent int_setting = new Intent(EnterPassActivity.this,MainSettingsActivity.class);
                startActivity(int_setting);
                this.finish();
            }
        }

    }
}
