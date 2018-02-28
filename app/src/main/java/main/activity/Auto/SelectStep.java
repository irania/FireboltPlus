package main.activity.Auto;




import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.Tirax.plasma.MyActivity;
import com.Tirax.plasma.Storage.Pages;
import com.Tirax.plasma.R;


import main.activity.StartActivity;


public class SelectStep extends MyActivity implements OnClickListener {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step_auto);
				//declaring main menu buttons
				Button high=(Button) findViewById(R.id.btn_high);
				Button medium=(Button) findViewById(R.id.btn_medium);
				Button low=(Button) findViewById(R.id.btn_low);
				Button back= (Button) findViewById(R.id.btn_auto_back);
				
				//declaring onclicklistener functions
				high.setOnClickListener(this);
				medium.setOnClickListener(this);
				low.setOnClickListener(this);
				back.setOnClickListener(this);

		
	}

	@Override
	public void onClick(View arg0) {
		
				//declaring intents
				Intent int_start=new Intent(this,StartActivity.class);


				//starting corresponding intents

				if (arg0.getId()==R.id.btn_high){
					Pages.step = Pages.HIGH;
					startActivity(int_start);
				}
				
				if (arg0.getId()==R.id.btn_medium){
					Pages.step = Pages.MEDIUM;
					startActivity(int_start);
				}

				if (arg0.getId()==R.id.btn_low){
					Pages.step = Pages.LOW;
					startActivity(int_start);
				}
				if (arg0.getId()==R.id.btn_auto_back){
					this.finish();
				}

		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				
	}
	

}
