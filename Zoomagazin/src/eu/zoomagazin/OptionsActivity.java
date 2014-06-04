package eu.zoomagazin;

import com.example.zoomagazin.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewParent;

public class OptionsActivity extends Activity {

	private static final String PET_TYPE = "pet";
	private static final String PRODUCT_TYPE = "type";
	private String newproducts = "1";
	private String sales = "2";
	private String promotions = "3";
	private String pet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		pet = intent.getStringExtra(PET_TYPE);
		
		//Change the color of the title bar.
		setContentView(R.layout.activity_options);
		View titleView = getWindow().findViewById(android.R.id.title);
        if (titleView != null) {
          ViewParent parent = titleView.getParent();
          if (parent != null && (parent instanceof View)) {
            View parentView = (View)parent;
            parentView.setBackgroundColor(Color.rgb(104, 105, 18));
          }
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options, menu);
		return true;
	}
	
	public void News(View view){
		Intent intent = new Intent(this, NewsActivity.class);
		intent.putExtra(PET_TYPE , pet);
		startActivity(intent);
	}
	
	public void NewProducts(View view){
		Intent intent = new Intent(this, ProductsActivity.class);
		intent.putExtra(PET_TYPE , pet);
		intent.putExtra(PRODUCT_TYPE , newproducts);
		startActivity(intent);
	}
	
	public void Sales(View view){
		Intent intent = new Intent(this, ProductsActivity.class);
		intent.putExtra(PET_TYPE , pet);
		intent.putExtra(PRODUCT_TYPE , sales);
		startActivity(intent);
	}
	
	public void Promotions(View view){
		Intent intent = new Intent(this, ProductsActivity.class);
		intent.putExtra(PET_TYPE , pet);
		intent.putExtra(PRODUCT_TYPE , promotions);
		startActivity(intent);
	}

}
