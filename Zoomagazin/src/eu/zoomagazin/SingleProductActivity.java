package eu.zoomagazin;

import com.example.zoomagazin.R;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleProductActivity extends Activity {

	TextView txtName;
	TextView txtDesc;
	TextView txtPrice;
	ImageView productPicture;
 
    String idProduct;
    String nameBg;
    String descriptionBg;
    String price;
    String picture;
    String pictureUrl;

    private static final String TAG_PID = "id";
    private static final String TAG_NAME = "nameBg";
    private static final String TAG_PRICE = "price";
    private static final String TAG_PICTURE = "picture";
    private static final String TAG_DESCRIPTION = "descriptionBg";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        
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
 
        idProduct = i.getStringExtra(TAG_PID);
        nameBg = i.getStringExtra(TAG_NAME);
        descriptionBg = i.getStringExtra(TAG_DESCRIPTION);
        price = i.getStringExtra(TAG_PRICE);
        picture = i.getStringExtra(TAG_PICTURE);
        
        txtName = (TextView) findViewById(R.id.inputName);
        txtPrice = (TextView) findViewById(R.id.inputPrice);
        txtDesc = (TextView) findViewById(R.id.inputDesc2);
        productPicture = (ImageView) findViewById(R.id.productPicture);
        pictureUrl = "http://zoomagazin.eu/upload/Products/"+ picture;
        
        UrlImageViewHelper.setUrlDrawable(productPicture, pictureUrl);
        txtName.setText(nameBg);
        txtPrice.setText("Цена "+price+"лв.");
        txtDesc.setText("Описание "+descriptionBg);
        txtDesc.setMovementMethod(new ScrollingMovementMethod());

    }
  
    //Opens the site in a browser.
    public void SiteView(View view){
    	Uri uri = Uri.parse("http://zoomagazin.eu/index.php?zoo_p=18&id=" + idProduct + "&lang=Bg");
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}
}
