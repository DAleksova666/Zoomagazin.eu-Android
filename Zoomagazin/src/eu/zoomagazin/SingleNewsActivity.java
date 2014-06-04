package eu.zoomagazin;


import com.example.zoomagazin.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;
 
public class SingleNewsActivity extends Activity {
 
	TextView txtName;
	TextView txtDesc;
	TextView txtDate;
 
    String idNews;
    String nameBg;
    String descriptionBg;
    String date;
 
    private static final String TAG_PID = "id";
    private static final String TAG_NAME = "nameBg";
    private static final String TAG_DATE = "date";
    private static final String TAG_DESCRIPTION = "descriptionBg";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_news);
        
        //Change the color of the title bar.
        View titleView = getWindow().findViewById(android.R.id.title);
        if (titleView != null) {
          ViewParent parent = titleView.getParent();
          if (parent != null && (parent instanceof View)) {
            View parentView = (View)parent;
            parentView.setBackgroundColor(Color.rgb(104, 105, 18));
          }
        }
        
        Intent i = getIntent();
        idNews = i.getStringExtra(TAG_PID);
        nameBg = i.getStringExtra(TAG_NAME);
        descriptionBg = i.getStringExtra(TAG_DESCRIPTION);
        date = i.getStringExtra(TAG_DATE);
        
        txtName = (TextView) findViewById(R.id.inputName);
        txtDate = (TextView) findViewById(R.id.inputDate);
        txtDesc = (TextView) findViewById(R.id.inputDesc);

        txtName.setText(nameBg);
        txtDate.setText("Публикувано на "+date);
        txtDesc.setText(descriptionBg);
        txtDesc.setMovementMethod(new ScrollingMovementMethod());
        
    }
  
    //Opens the site in a browser.
    public void SiteView(View view){
    	Uri uri = Uri.parse("http://zoomagazin.bg/index.php?p=48&id=" + idNews );
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}
}
