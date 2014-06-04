package eu.zoomagazin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CheckForUpdates extends BroadcastReceiver{
	
	private static String url_all_products = "http://zoomagazin.eu/android/Updates.php";
	JSONParser jParser = new JSONParser();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NEWS = "news";
    private static final String TAG_DATE = "date";
    JSONArray news = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		//Get current date
		final Calendar calendar = Calendar.getInstance();
	    int mYear = calendar.get(Calendar.YEAR);
	    int mMonth = calendar.get(Calendar.MONTH)+1;
	    int mDay = calendar.get(Calendar.DAY_OF_MONTH);
	    String date = ""+mYear+"-"+mMonth+"-"+mDay;
	    
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(TAG_DATE, date));
		
        JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                news = json.getJSONArray(TAG_NEWS);

                for (int i = 0; i < news.length(); i++) {
                	Intent intent2 = new Intent(context, NotificationActivity.class);
               		context.startActivity(intent2);
                }
            } else {
            	
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
		
	}
    
    
}
