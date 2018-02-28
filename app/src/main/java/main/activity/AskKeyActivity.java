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


import main.activity.Auto.AutoActivity;
//TODO test it
public class AskKeyActivity extends MyActivity implements  View.OnClickListener {

    TextView pass_textview;
    String pass_txt="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pass);

        assignKeys();

        pass_textview = (TextView) findViewById(R.id.txt_pass);
        setPassText();

        TextView tv = (TextView) findViewById(R.id.txt_pass_main);
        try {
            tv.setText("Please Contact Support. Code: "+ SecurityFile.load(SecurityType.REQUEST_CODE));
        } catch (Exception e) {
            tv.setText("Please Contact Support. Code is not Accessible.");
            e.printStackTrace();
        }


    }

    private void setPassText() {
        pass_textview.setText(pass_txt);
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
            try {
                if(License.isTrueKey(pass_txt)) {


                        License.UpdateValidTime(pass_txt);
                        License.UpdateValidDate(pass_txt);

                        Intent int_setting = new Intent(AskKeyActivity.this,MainActivity.class);
                        startActivity(int_setting);
                        this.finish();
                }
                else{
                    if(License.isTrial()){
                        Intent int_setting = new Intent(AskKeyActivity.this,AutoActivity.class);
                        startActivity(int_setting);
                    }
                }
            } catch (Exception e) {
                //TODO what should i do?
                e.printStackTrace();
            }
        }

    }
}
