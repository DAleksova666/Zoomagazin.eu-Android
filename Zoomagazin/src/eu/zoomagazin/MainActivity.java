package eu.zoomagazin;

import java.util.GregorianCalendar;

import com.example.zoomagazin.R;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewParent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        scheduleCheck();
        
        //Change the color of the title bar.
        View titleView = getWindow().findViewById(android.R.id.title);
        if (titleView != null) {
          ViewParent parent = titleView.getParent();
          if (parent != null && (parent instanceof View)) {
            View parentView = (View)parent;
            parentView.setBackgroundColor(Color.rgb(104, 105, 18));
          }
        }
    }
    
    public void scheduleCheck(){
    		// Current time
            Long time = new GregorianCalendar().getTimeInMillis();
           
            Intent intent = new Intent(this, CheckForUpdates.class);
       
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            //Repeatedly calls the CheckForUpdates class at a period of one day.
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,time,AlarmManager.INTERVAL_DAY,
            		PendingIntent.getBroadcast(this,1,  intent, PendingIntent.FLAG_UPDATE_CURRENT));
         
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    

	public void ChooseActivity(View view){
		//Check for Internet connection.
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
        	
        	//There is connection.Continue.
          	Intent intent = new Intent(this, ChooseActivity.class);
       		startActivity(intent);
        }else{
        	
        	//There is no Internet connection.Display alert.
        	new AlertDialog.Builder(this)
            .setTitle("Няма интернет връзка")
            .setMessage("Приложението изисква интернет връзка за да продължите.")
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) { 
                    // continue with delete
                }
             })
             .show();
        }
		
	}
    
}
