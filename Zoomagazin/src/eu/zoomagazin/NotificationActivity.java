package eu.zoomagazin;

import com.example.zoomagazin.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;

public class NotificationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		createNotification();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}
	
	public void createNotification() {
		//Opens the News activity if the notification is selected.
	    Intent intent = new Intent(this, NewsActivity.class);
	    PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

	    Notification noti = new NotificationCompat.Builder(this)
	        .setContentTitle("Новина в Zoomagazin.bg ! ")
	        .setContentText("Има новини в зоомагазина!").setSmallIcon(R.drawable.icon)
	        .setContentIntent(pIntent)
	        .addAction(R.drawable.icon, "Прочетете новините тук.", pIntent).build();
	    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    noti.flags |= Notification.FLAG_AUTO_CANCEL;
	    notificationManager.notify(0, noti);

	  }

}
