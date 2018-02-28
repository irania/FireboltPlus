package com.Tirax.plasma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import main.activity.MainActivity;

/**
 * Created by MHSaadatfar on 3/12/2016.
 */
public class StartMyActivityAtBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
