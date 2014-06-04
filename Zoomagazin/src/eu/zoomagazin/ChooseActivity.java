package eu.zoomagazin;

import com.example.zoomagazin.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewParent;

public class ChooseActivity extends Activity {

	private static final String PET_TYPE = "pet";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose);
		
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.choose, menu);
		return true;
	}
	
	public void DogActivity(View view){
		Intent intent = new Intent(this, OptionsActivity.class);
		intent.putExtra(PET_TYPE , "1");
		startActivity(intent);
	}
	
	public void CatActivity(View view){
		Intent intent = new Intent(this, OptionsActivity.class);
		intent.putExtra(PET_TYPE , "2");
		startActivity(intent);
	}
	
	public void BirdActivity(View view){
		Intent intent = new Intent(this, OptionsActivity.class);
		intent.putExtra(PET_TYPE , "3");
		startActivity(intent);
	}
	
	public void LittleActivity(View view){
		Intent intent = new Intent(this, OptionsActivity.class);
		intent.putExtra(PET_TYPE , "4");
		startActivity(intent);
	}
	
	public void AquaActivity(View view){
		Intent intent = new Intent(this, OptionsActivity.class);
		intent.putExtra(PET_TYPE , "5");
		startActivity(intent);
	}
	
	public void TeraActivity(View view){
		Intent intent = new Intent(this, OptionsActivity.class);
		intent.putExtra(PET_TYPE , "6");
		startActivity(intent);
	}

}
