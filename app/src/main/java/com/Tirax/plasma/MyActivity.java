package com.Tirax.plasma;

import android.app.Activity;
import android.os.Bundle;




import main.activity.MainActivity;

/**
 * Created by Lenovo on 7/4/2016.
 */
public class MyActivity extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.nowActivity = this;


    }


}
