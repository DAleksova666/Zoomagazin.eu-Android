package eu.zoomagazin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
 
public class ProductsActivity extends ListActivity {
 
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
 
    ArrayList<HashMap<String, String>> productsList;
 
    // Url that gets all products 
    private static String url_all_products = "http://zoomagazin.eu/android/Products.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "id";
    private static final String TAG_NAME = "nameBg";
    private static final String TAG_DESCRIPTION = "descriptionBg";
    private static final String TAG_PRICE = "price";
    private static final String TAG_PICTURE = "picture";
    private static final String PET_TYPE = "pet";
    private static final String PRODUCT_TYPE = "type";
    
	private String pet;
	private String type;
	private String idProduct;
	private String nameBg;
	private String picture;
	private String descriptionBg;
	private String price;

    JSONArray products = null;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        
        //Change the color of the title bar.
        View titleView = getWindow().findViewById(android.R.id.title);
        if (titleView != null) {
          ViewParent parent = titleView.getParent();
          if (parent != null && (parent instanceof View)) {
            View parentView = (View)parent;
            parentView.setBackgroundColor(Color.rgb(104, 105, 18));
          }
        }
        
        //Get chosen pet and type of products
        Intent intent = getIntent();
		pet = intent.getStringExtra(PET_TYPE);
		type = intent.getStringExtra(PRODUCT_TYPE);
        
        productsList = new ArrayList<HashMap<String, String>>();
        
        new LoadAllProducts().execute();
 
        ListView lv = getListView();
        
        //Launch new activity if product is selected.
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	idProduct = ((TextView) view.findViewById(R.id.idProduct)).getText().toString();
            	descriptionBg = ((TextView) view.findViewById(R.id.desc)).getText().toString();
            	price = ((TextView) view.findViewById(R.id.price)).getText().toString();
            	picture = ((TextView) view.findViewById(R.id.picture)).getText().toString();
            	nameBg = ((TextView) view.findViewById(R.id.name)).getText().toString();

                Intent intent = new Intent(getApplicationContext(), SingleProductActivity.class);
                
                intent.putExtra(TAG_PID, idProduct);
                intent.putExtra(TAG_NAME, nameBg);
                intent.putExtra(TAG_PICTURE, picture);
                intent.putExtra(TAG_DESCRIPTION, descriptionBg);
                intent.putExtra(TAG_PRICE, price);

                startActivity(intent);
            }
        });
 
    }
  
    class LoadAllProducts extends AsyncTask<String, String, String> {
 
    	//Show message while getting data.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProductsActivity.this);
            pDialog.setMessage("Моля изчакайте...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        //Get products from url.
        protected String doInBackground(String... args) {
        	
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(PRODUCT_TYPE,type));
            params.add(new BasicNameValuePair(PET_TYPE, pet));
            
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
 
            try {
                int success = json.getInt(TAG_SUCCESS);
                //Check response to see if there are products found for chosen pet and type of products.
                if (success == 1) {
                    products = json.getJSONArray(TAG_PRODUCTS);
 
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        idProduct = c.getString(TAG_PID);
                        descriptionBg = c.getString(TAG_DESCRIPTION);
                        price = c.getString(TAG_PRICE);
                        picture = c.getString(TAG_PICTURE);
                        nameBg = c.getString(TAG_NAME);

                        HashMap<String, String> map = new HashMap<String, String>();
                        
                        map.put(TAG_PID, idProduct);
                        map.put(TAG_DESCRIPTION, descriptionBg);
                        map.put(TAG_PRICE, price);
                        map.put(TAG_PICTURE, picture);
                        map.put(TAG_NAME, nameBg);
                        
                        productsList.add(map);
                    }
                } else {
                    //There are no products found.
                	nameBg = "Няма намерени продукти";
                    HashMap<String, String> map = new HashMap<String, String>();
                    
                    map.put(TAG_NAME, nameBg);

                    productsList.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
        
        //Remove the message and display products.
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            
            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            ProductsActivity.this, productsList,
                            R.layout.list_product, new String[] {TAG_PID,TAG_DESCRIPTION,TAG_PICTURE,TAG_PRICE,TAG_NAME},
                            new int[] { R.id.idProduct,R.id.desc,R.id.picture,R.id.price, R.id.name });
                    setListAdapter(adapter);
                }
            });
 
        }
 
    }
}
