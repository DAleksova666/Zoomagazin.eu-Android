package eu.zoomagazin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.zoomagazin.R;

 
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
 
public class NewsActivity extends ListActivity {
 
    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser();
 
    ArrayList<HashMap<String, String>> newsList;
 
    //Url to get all news from zoomagazin.bg
    private static String url_all_products = "http://zoomagazin.eu/android/News.php";
 
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NEWS = "news";
    private static final String TAG_PID = "id";
    private static final String TAG_NAME = "nameBg";
    private static final String TAG_DESCRIPTION = "descriptionBg";
    private static final String TAG_DATE = "date";
    
    String idNews;
    String nameBg;
    String descriptionBg;
    String date;
    
    JSONArray news = null;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        
        //Change the color of the title bar.
        View titleView = getWindow().findViewById(android.R.id.title);
        if (titleView != null) {
          ViewParent parent = titleView.getParent();
          if (parent != null && (parent instanceof View)) {
            View parentView = (View)parent;
            parentView.setBackgroundColor(Color.rgb(104, 105, 18));
          }
        }

        newsList = new ArrayList<HashMap<String, String>>();
 
        new LoadAllProducts().execute();
 
        ListView lv = getListView();
 
        //Launch new activity if news is selected.
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	idNews = ((TextView) view.findViewById(R.id.idNews)).getText().toString();
            	descriptionBg = ((TextView) view.findViewById(R.id.desc)).getText().toString();
            	date= ((TextView) view.findViewById(R.id.date)).getText().toString();
            	nameBg = ((TextView) view.findViewById(R.id.name)).getText().toString();
 
                Intent intent = new Intent(getApplicationContext(),SingleNewsActivity.class);

                intent.putExtra(TAG_PID, idNews);
                intent.putExtra(TAG_NAME, nameBg);
                intent.putExtra(TAG_DESCRIPTION, descriptionBg);
                intent.putExtra(TAG_DATE, date);

                startActivity(intent);
            }
        });
 
    }
  
    class LoadAllProducts extends AsyncTask<String, String, String> {
 
    	//Show message while getting data.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewsActivity.this);
            pDialog.setMessage("Моля изчакайте...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        //Get products from url.
        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
 
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    news = json.getJSONArray(TAG_NEWS);
                    
                    for (int i = 0; i < news.length(); i++) {
                        JSONObject c = news.getJSONObject(i);
 
                        idNews = c.getString(TAG_PID);
                        descriptionBg = c.getString(TAG_DESCRIPTION);
                        date = c.getString(TAG_DATE);
                        nameBg = c.getString(TAG_NAME);
                         
                        HashMap<String, String> map = new HashMap<String, String>();
 
                        map.put(TAG_PID, idNews);
                        map.put(TAG_DESCRIPTION, descriptionBg);
                        map.put(TAG_DATE, date);
                        map.put(TAG_NAME, nameBg);

                        newsList.add(map);
                    }
                } else {
                	 //There is no news found.
                	nameBg = "Няма намерени новини";
                    HashMap<String, String> map = new HashMap<String, String>();
                    
                    map.put(TAG_NAME, nameBg);

                    newsList.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
 

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                	ListAdapter adapter = new SimpleAdapter(
                            NewsActivity.this, newsList,
                            R.layout.list_item, new String[] { TAG_PID,TAG_DESCRIPTION,TAG_DATE,TAG_NAME},
                            new int[] { R.id.idNews,R.id.desc,R.id.date, R.id.name });
                    setListAdapter(adapter);
                }
            });
 
        }
 
    }
}