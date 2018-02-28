package com.Tirax.plasma.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.Tirax.plasma.R;




/**
 * Created by Lenovo on 7/4/2016.
 */
public class ErrorDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public TextView textDia;

    public ErrorDialog(Activity a) {
        super(a);

        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        this.setTitle("Error");
        this.setCancelable(false);
        textDia = (TextView) findViewById(R.id.txt_dia);
    }


    @Override
    public void onClick(View v) {

    }
}