package com.Tirax.plasma;

import android.app.Activity;

public class ResetTask{
	

	public static Activity mainActivity;


	public static void resetSystem() {
		//TODO active it

		/*try{
			Intent mStartActivity = new Intent(mainActivity, MainActivity.class);

			SerialPort.turnOff();

			SharedPrefrences.setReseted(true);
			SharedPrefrences.setRegisters(DataProvider.getAllRegisters());
			android.os.SystemClock.sleep(700);

			int mPendingIntentId = 123456;
			PendingIntent mPendingIntent = PendingIntent.getActivity(mainActivity, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
			AlarmManager mgr = (AlarmManager)mainActivity.getSystemService(Context.ALARM_SERVICE);
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);

			Log.e("TIRAX", "Log set" + SharedPrefrences.getReseted());
			System.exit(0);
		}
		catch(Exception ex){
			Log.e("TIRAX ERROR","i want reset");
		}
*/

	}
}