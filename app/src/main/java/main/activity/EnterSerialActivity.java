package main.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Tirax.plasma.Enums.SecurityType;
import com.Tirax.plasma.License;
import com.Tirax.plasma.MyActivity;
import com.Tirax.plasma.SecurityFile;
import com.Tirax.plasma.R;


public class EnterSerialActivity extends MyActivity implements  View.OnClickListener {

    TextView serial_textview;
    String serial_txt="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pass);

        assignKeys();

        serial_textview = (TextView) findViewById(R.id.txt_pass);
        setserialText();

        TextView tv = (TextView) findViewById(R.id.txt_pass_main);
        tv.setText("Please Enter Serial Code. ");


    }

    private void setserialText() {
        serial_textview.setText(serial_txt);
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
            serial_txt +="0";
        }
        if (v.getId()==R.id.button1){
            serial_txt +="1";
        }
        if (v.getId()==R.id.button2){
            serial_txt +="2";
        }
        if (v.getId()==R.id.button3){
            serial_txt +="3";
        }
        if (v.getId()==R.id.button4){
            serial_txt +="4";
        }
        if (v.getId()==R.id.button5){
            serial_txt +="5";
        }
        if (v.getId()==R.id.button6){
            serial_txt +="6";
        }
        if (v.getId()==R.id.button7){
            serial_txt +="7";
        }
        if (v.getId()==R.id.button8){
            serial_txt +="8";
        }
        if (v.getId()==R.id.button9){
            serial_txt +="9";
        }
        if (v.getId()==R.id.btn_clear){
            if(serial_txt.length()>0)
                serial_txt = serial_txt.substring(0,serial_txt.length()-1);
        }
        setserialText();
        if (v.getId()==R.id.btn_back_pass){
            this.finish();
        }
        if (v.getId()==R.id.btn_next_pass){

            //TODO test it
            if(serial_txt.length()==6) {
                SecurityFile.save(SecurityType.SERIAL, serial_txt);
                License.initializeTime();
                Intent int_setting = new Intent(EnterSerialActivity.this, MainActivity.class);
                startActivity(int_setting);
                this.finish();
            }

        }

    }
}
