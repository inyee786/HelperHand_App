package com.example.getgpslocation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.*;

public class MainActivity extends ActionBarActivity {

	Button btnShowLocation;
	DatabaseHelper myDb;
	GPSTracker gps;
	String value;
	private ArrayList <ContactList> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			value = extras.getString("phno");
			//The key argument here must match that used in the other activity
		}

        btnShowLocation = (Button) findViewById(R.id.show_location);
        
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			@Override
			public void onClick(View v) {
				gps = new GPSTracker(MainActivity.this);
				
				if(gps.canGetLocation()) {
					double latitude = gps.getLatitude();
					double longitude = gps.getLongitude();


                     list = myDb.getList(value);

					smsIntent.setData(Uri.parse("smsto:"));
					smsIntent.setType("vnd.android-dir/mms-sms");
					smsIntent.putExtra("address"  , list);
					//smsIntent.putExtra("address"  , new String ("01234"));
					smsIntent.putExtra("sms_body"  ,  "Your Location is -\nLat: " + latitude + "\nLong: " + longitude);

					try {
						startActivity(smsIntent);
						finish();
						Log.i("Finished sending SMS...", "");
					} catch (android.content.ActivityNotFoundException ex) {
						Toast.makeText(MainActivity.this,
								"SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
					}




					/*Toast.makeText(
							getApplicationContext(),
							"Your Location is -\nLat: " + latitude + "\nLong: "
									+ longitude, Toast.LENGTH_LONG).show();*/
				} else {
					gps.showSettingsAlert();
				}
			}
		});
    }
}
