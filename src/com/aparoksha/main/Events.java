package com.aparoksha.main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aparoksha.main.R;
import com.navigation.adapter.NavDrawerListAdapter;
import com.navigation.drawer.NavDrawerItem;

public class Events extends Activity implements OnClickListener,
		OnTouchListener {

	ImageButton							floatbtn;
	DBmanager							dbms;
	String								name;
	String								date;
	String								time;
	String								venue;
	TextView							textView_heading_1;
	TextView							textView_introduction;
	TextView							textView_heading_3;
	TextView							textView_organizer_1_name;
	TextView							textView_organizer_1_contact;
	TextView							textView_organizer_2_name;
	TextView							textView_organizer_2_contact;
	TextView							textView_organizer_3_name;
	TextView							textView_organizer_3_contact;
	TextView							textView_organizer_4_name;
	TextView							textView_organizer_4_contact;
	TextView							textView_organizer_5_name;
	TextView							textView_organizer_5_contact;
	TextView							textView_date;
	TextView							textView_timings;
	TextView							textView_venue;
	String								string_event;
	String								string_introduction;
	String								string_organizer_1_name;
	String								string_organizer_2_name;
	String								string_organizer_3_name;
	String								string_organizer_4_name;
	String								string_organizer_5_name;
	String								string_organizer_5_contact;
	String								string_organizer_1_contact;
	String								string_organizer_2_contact;
	String								string_organizer_3_contact;
	String								string_organizer_4_contact;
	String								string_date;
	String								string_timings;
	String								string_venue;
	Bundle								bundle;

	int									timeHour;
	int									timeMinutes;
	int									day;

	LinearLayout						linearLayout;
	ImageView							imageView;
	ScrollView							scrollView;
	int									oldY;
	int									alpha;
	boolean								animationDone;
	int									animationDuration;
	boolean								callPlaced;
	Venues[]							venues;

	private DrawerLayout				mDrawerLayout;
	private ListView					mDrawerList;
	private ActionBarDrawerToggle		mDrawerToggle;

	// nav drawer title
	private CharSequence				mDrawerTitle;

	// used to store app title
	private CharSequence				mTitle;

	// slide menu items
	private String[]					navMenuTitles;
	private String[]					navMenuSubtitles;
	private TypedArray					navMenuIcons;

	private ArrayList<NavDrawerItem>	navDrawerItems;
	private NavDrawerListAdapter		adapter;

	Random								random;
	boolean								resultSet;
	boolean								notified		= false;

	private ProgressDialog				pDialog;

	private String						ifuser;

	JSONParser							jsonParser		= new JSONParser();

	SharedPreferences					mSharedPreference;

	private static final String			REGISTER_URL	= "http://aparoksha.iiita.ac.in/register1/index.php?page=add";

	private static final String			TAG_SUCCESS		= "success";
	private static final String			TAG_MESSAGE		= "message";

	private class Venues {

		String	location;
		double	coordinateLatitude;
		double	coordinateLongitude;

		public Venues(String location, double coordinateLatitude,
				double coordinateLongitude) {
			this.location = location;
			this.coordinateLatitude = coordinateLatitude;
			this.coordinateLongitude = coordinateLongitude;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentView(R.layout.activity_events);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		mSharedPreference = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		ifuser = (mSharedPreference.getString("whoistheuser", "Anonymous_User"));

		createNavigationDrawer(savedInstanceState);

		dbms = new DBmanager(this);
		animationDone = true;
		name = "eventnewerr";
		date = "12 jan";
		time = "10 am";
		venue = "855,bh3";

		random = new Random();
		try {
			bundle = getIntent().getExtras();
			string_event = bundle.getString("Event");
			// notified = bundle.getBoolean("from_notify", false);

		} catch (Exception e) {
			string_event = getIntent().getStringExtra("Event");
			// notified = getIntent().getBooleanExtra("from_notify", false);
			Log.d("Exception", e.getMessage());
		} finally {

		}

		floatbtn = (ImageButton) findViewById(R.id.floatingbutton);

		if (notified == true) {
			Log.d("notify", "true");
			// floatbtn.setVisibility(View.GONE);
		}

		floatbtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				dialogboxmanager();
			}
		});

		mapViews();
		createVenues();
		initializeObjects();
		// setTypeFace();
		readText();
		setText();
		setImage();
		addListeners();

	}

	private void createVenues() {
		venues = new Venues[13];

		venues[0] = new Venues("CC-3", 25.432160, 81.770314);
		venues[1] = new Venues("LT", 25.431143, 81.771001);
		venues[2] = new Venues("Main Audi", 25.431056, 81.769069);
		venues[3] = new Venues("Admin Audi", 25.430591, 81.770754);
		venues[4] = new Venues("CC-1", 25.431530, 81.770024);
		venues[5] = new Venues("CC-2", 25.430542, 81.772084);
		venues[6] = new Venues("Library", 25.431802, 81.771419);
		venues[7] = new Venues("Cafe", 25.431435, 81.771868);
		venues[8] = new Venues("Basketball Court", 25.429268, 81.771101);
		venues[9] = new Venues("Tennis Court", 25.429016, 81.771989);
		venues[10] = new Venues("Pavilion", 25.428856, 81.772828);
		venues[11] = new Venues("Ground", 25.429314, 81.773140);
		venues[12] = new Venues("Dormitory", 25.428665, 81.774915);
	}

	private void setTypeFace() {
		Typeface typeface_1;
		// Typeface typeface_2;

		try {
			typeface_1 = Typeface.createFromAsset(getAssets(),
					"fonts/gabriola.ttf");
			// typeface_2 = Typeface.createFromAsset(getAssets(),
			// "fonts/vgafix.fon");

			textView_heading_1.setTypeface(typeface_1);
			// textView_heading_2.setTypeface(typeface_1);
			textView_heading_3.setTypeface(typeface_1);

			textView_date.setTypeface(typeface_1);
			textView_introduction.setTypeface(typeface_1);
			textView_organizer_1_contact.setTypeface(typeface_1);
			textView_organizer_1_name.setTypeface(typeface_1);
			textView_organizer_2_contact.setTypeface(typeface_1);
			textView_organizer_2_name.setTypeface(typeface_1);
			textView_organizer_3_contact.setTypeface(typeface_1);
			textView_organizer_3_name.setTypeface(typeface_1);
			textView_organizer_4_contact.setTypeface(typeface_1);
			textView_organizer_4_name.setTypeface(typeface_1);
			textView_organizer_5_contact.setTypeface(typeface_1);
			textView_organizer_5_name.setTypeface(typeface_1);
			textView_timings.setTypeface(typeface_1);
			textView_venue.setTypeface(typeface_1);

			// textView_heading_1.setAllCaps(true);
			// textView_heading_2.setAllCaps(true);

			// textView_heading_1.setElevation(5);
			// textView_heading_2.setElevation(5);

			// textView_heading_1.setTextSize(22);
			// textView_heading_2.setTextSize(20);
			// textView_introduction.setTypeface(typeface_2);
			// textView_organizers.setTypeface(typeface_2);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Error : " + e,
					Toast.LENGTH_SHORT).show();
		}
	}

	private void addListeners() {
		textView_organizer_1_contact.setOnClickListener(this);
		textView_organizer_2_contact.setOnClickListener(this);
		textView_organizer_3_contact.setOnClickListener(this);
		textView_organizer_4_contact.setOnClickListener(this);
		textView_organizer_5_contact.setOnClickListener(this);
		scrollView.setOnTouchListener(this);
	}

	private void mapViews() {
		scrollView = (ScrollView) findViewById(R.id.events_scrollView);
		textView_heading_1 = (TextView) findViewById(R.id.events_textView_heading_1);
		// textView_heading_2 = (TextView)
		// findViewById(R.id.events_textView_heading_2);
		textView_heading_3 = (TextView) findViewById(R.id.events_textView_heading_3);
		textView_introduction = (TextView) findViewById(R.id.events_textView_about);
		textView_organizer_1_name = (TextView) findViewById(R.id.events_textView_organizer_1_name);
		textView_organizer_2_name = (TextView) findViewById(R.id.events_textView_organizer_2_name);
		textView_organizer_1_contact = (TextView) findViewById(R.id.events_textView_organizer_1_contact);
		textView_organizer_2_contact = (TextView) findViewById(R.id.events_textView_organizer_2_contact);
		textView_organizer_3_name = (TextView) findViewById(R.id.events_textView_organizer_3_name);
		textView_organizer_3_contact = (TextView) findViewById(R.id.events_textView_organizer_3_contact);
		textView_organizer_4_name = (TextView) findViewById(R.id.events_textView_organizer_4_name);
		textView_organizer_4_contact = (TextView) findViewById(R.id.events_textView_organizer_4_contact);
		textView_organizer_5_name = (TextView) findViewById(R.id.events_textView_organizer_5_name);
		textView_organizer_5_contact = (TextView) findViewById(R.id.events_textView_organizer_5_contact);
		textView_date = (TextView) findViewById(R.id.events_textView_date);
		textView_timings = (TextView) findViewById(R.id.events_textView_time);
		textView_venue = (TextView) findViewById(R.id.events_textView_venue);
		linearLayout = (LinearLayout) findViewById(R.id.events_linearLayout);
		imageView = (ImageView) findViewById(R.id.events_imageView_top);
	}

	private void initializeObjects() {
		// string_event = bundle.getString("Event");
		string_introduction = new String("");
		alpha = 0;
		oldY = 0;
		callPlaced = false;
		resultSet = false;
		// linearLayout.setVisibility(View.GONE);
		linearLayout.setAlpha(0);
		animationDuration = getResources().getInteger(
				android.R.integer.config_longAnimTime);
		string_date = null;
		string_organizer_1_contact = null;
		string_organizer_2_contact = null;
		string_organizer_3_contact = null;
		string_organizer_4_contact = null;
		string_organizer_5_contact = null;
		string_organizer_1_name = null;
		string_organizer_2_name = null;
		string_organizer_3_name = null;
		string_organizer_4_name = null;
		string_organizer_5_name = null;
		string_timings = null;
		string_venue = null;
		timeHour = 9;
		timeMinutes = 0;
		day = -1;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.penaltyshootout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.events_actionMap) {
			callPlaced = true;
			try {
				double coordinateLatitude = 25.430201;
				double coordinateLongitude = 81.771927;
				int i;
				for (i = 0; i < 13; i++) {
					if (venues[i].location.equalsIgnoreCase(string_venue)) {
						coordinateLatitude = venues[i].coordinateLatitude;
						coordinateLongitude = venues[i].coordinateLongitude;
						break;
					}
				}
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
						Uri.parse("geo:0,0?q=" + coordinateLatitude + ","
								+ coordinateLongitude));
				intent.setClassName("com.google.android.apps.maps",
						"com.google.android.maps.MapsActivity");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			} catch (Exception e) {
				Log.d("Map Error", e.getMessage());
			}
		} else if (id == R.id.events_actionRegister) {

			LayoutInflater inflater = getLayoutInflater();
			View alertLayout = inflater.inflate(R.layout.register_dialogbox,
					null);

			AlertDialog.Builder alert = new AlertDialog.Builder(Events.this);
			alert.setTitle("Registration");
			alert.setView(alertLayout);
			alert.setCancelable(false);

			alert.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							new AttemptRegister().execute();

						}
					});

			alert.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							//Toast.makeText(getBaseContext(), "ok done.!",
								//	Toast.LENGTH_SHORT).show();
						}
					});

			if (ifuser.equals("Anonymous_User")) {
				Toast.makeText(getBaseContext(),
						"You have to login before event Registration",
						Toast.LENGTH_SHORT).show();

			} else if (isNetworkConnected()) {

				AlertDialog dialog = alert.create();
				dialog.show();
			} else {
				Toast.makeText(getApplicationContext(),
						"You are not connected to Internet,And try again..!!",
						Toast.LENGTH_SHORT).show();
			}
		}

		return super.onOptionsItemSelected(item);
	}

	public void dialogboxmanager() {
		// custom dialog
		// final Dialog dialog = new Dialog(Penaltyshootout.this);
		// dialog.setContentView(R.layout.events_dialogbox);
		// dialog.setTitle("Penalty Shootout");

		// dialog.show();

		try {
			dbms.open();
			LayoutInflater inflater = getLayoutInflater();
			View alertLayout = inflater
					.inflate(R.layout.events_dialogbox, null);

			CheckBox fav = (CheckBox) alertLayout
					.findViewById(R.id.favourite_checkbox);
			CheckBox rem = (CheckBox) alertLayout
					.findViewById(R.id.reminder_checkbox);

			if (dbms.checkentrynew(string_event) == 1) {
				fav.setChecked(true);
			}

			fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {

					if (buttonView.isChecked()) {
						// checked
						Toast toast = Toast.makeText(getApplicationContext(),
								"U have added this to favourites",
								Toast.LENGTH_SHORT);
						toast.show();
						Log.d("hey", "hello toast");

						dbms.createEntry(string_event, string_timings,
								string_date, string_venue);

						String test = dbms.getData();
						Log.d("afr entering", test);

					} else {
						// not checked
						Toast toast = Toast.makeText(getApplicationContext(),
								"Removed from favourites", Toast.LENGTH_SHORT);
						toast.show();

						dbms.deleteEntry(string_event);
						String test = dbms.getData();
						if (test == null)
							Log.d("after deletng", "found null");
						else
							Log.d("After deleting", test);

						/*
						 * resultSet = true; Intent intent = new Intent();
						 * intent.putExtra("Result", 1); setResult(RESULT_OK,
						 * intent);
						 */
					}

				}
			});

			rem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {

					if (buttonView.isChecked()) {
						// checked
						if (day > 0) {
							Toast toast = Toast
									.makeText(
											getApplicationContext(),
											"U will be reminded half an hour before the event",
											Toast.LENGTH_SHORT);
							toast.show();

							reminder();
						} else {
							Toast toast = Toast.makeText(
									getApplicationContext(),
									"Sorry !! I cant help u !! ",
									Toast.LENGTH_SHORT);
							toast.show();
						}

					} else {
						// not checked
						Toast toast = Toast.makeText(getApplicationContext(),
								"Sorry !! I cant help u !! ",
								Toast.LENGTH_SHORT);
						toast.show();
					}

				}
			});

			AlertDialog.Builder alert = new AlertDialog.Builder(Events.this);
			alert.setTitle(string_event);
			alert.setView(alertLayout);
			alert.setCancelable(false);
			alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					//Toast.makeText(getBaseContext(), "ok done.!",
						//	Toast.LENGTH_SHORT).show();
				}
			});

			/*
			 * Context context = getApplicationContext(); CharSequence text =
			 * "Hello toast!"; int duration = Toast.LENGTH_SHORT;
			 * 
			 * Toast toast = Toast.makeText(context, text, duration);
			 * toast.show();
			 */

			AlertDialog dialog = alert.create();
			dialog.show();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		} finally {
			// dbms.close();
		}

	}

	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	private void readText() {
		boolean inMorning;
		StringTokenizer stringTokenizer;
		BufferedReader bufferedReader = null;
		InputStream inputStream_event_details;
		InputStream inputStream_event_introduction = null;
		byte[] byteArray;

		try {
			inputStream_event_details = getResources().openRawResource(
					getResources().getIdentifier(
							"raw/" + getFileName(string_event) + "_details",
							"raw", getPackageName()));
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream_event_details));

		} catch (NotFoundException e) {
			Toast.makeText(getApplicationContext(), "Error + " + e,
					Toast.LENGTH_LONG).show();
			inputStream_event_details = null;
			bufferedReader = null;
			Log.d("Resource", "Text File details not found");
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Error + " + e,
					Toast.LENGTH_LONG).show();
			inputStream_event_details = null;
			bufferedReader = null;
		}

		try {
			inputStream_event_introduction = getResources()
					.openRawResource(
							getResources().getIdentifier(
									"raw/" + getFileName(string_event)
											+ "_introduction", "raw",
									getPackageName()));
		} catch (NotFoundException e) {
			Toast.makeText(getApplicationContext(), "Error + " + e,
					Toast.LENGTH_LONG).show();
			inputStream_event_introduction = null;
			Log.d("Resource", "Text File introduction not found");
		} catch (Exception e) {
			inputStream_event_introduction = null;
		}

		try {
			byteArray = new byte[inputStream_event_introduction.available()];
			inputStream_event_introduction.read(byteArray);
			string_introduction = new String(byteArray);
		} catch (Exception e) {
			string_introduction = null;
		}

		try {
			string_date = bufferedReader.readLine();
			string_timings = bufferedReader.readLine();
			string_venue = bufferedReader.readLine();

			{
				stringTokenizer = new StringTokenizer(string_date, " -/");
				day = Integer.parseInt(stringTokenizer.nextToken());

				stringTokenizer = new StringTokenizer(string_timings, " :-");
				try {
					timeHour = Integer.parseInt(stringTokenizer.nextToken());
					timeMinutes = Integer.parseInt(stringTokenizer.nextToken());
				} catch (NumberFormatException exception) {
					timeHour = 9;
					timeMinutes = 0;
				}

				inMorning = stringTokenizer.nextToken().toLowerCase()
						.equals("am");
				if (!inMorning && (timeHour < 12)) {
					timeHour += 12;
				}
				if (inMorning && (timeHour == 12)) {
					timeHour = 0;
				}

				timeMinutes = Math.min(0, (timeMinutes - 30));
				if (timeMinutes == 0) {
					--timeHour;

					if (timeHour < 0) {
						day--;
						timeHour = 23;
						timeMinutes = 30;
					}
				}
			}

			try {
				string_organizer_1_name = bufferedReader.readLine();
				string_organizer_1_contact = bufferedReader.readLine();
			} catch (Exception e) {
				string_organizer_1_contact = null;
				string_organizer_1_name = null;
			}

			try {
				string_organizer_2_name = bufferedReader.readLine();
				string_organizer_2_contact = bufferedReader.readLine();
			} catch (Exception e) {
				string_organizer_2_contact = null;
				string_organizer_2_name = null;
			}

			try {
				string_organizer_3_name = bufferedReader.readLine();
				string_organizer_3_contact = bufferedReader.readLine();
			} catch (Exception e) {
				string_organizer_3_contact = null;
				string_organizer_3_name = null;
			}

			try {
				string_organizer_4_name = bufferedReader.readLine();
				string_organizer_4_contact = bufferedReader.readLine();
			} catch (Exception e) {
				string_organizer_4_contact = null;
				string_organizer_4_name = null;
			}

			try {
				string_organizer_5_name = bufferedReader.readLine();
				string_organizer_5_contact = bufferedReader.readLine();
			} catch (Exception e) {
				string_organizer_5_contact = null;
				string_organizer_5_name = null;
			}
		} catch (Exception e) {
			// Toast.makeText(getApplicationContext(), "Error + " + e,
			// Toast.LENGTH_LONG).show();
			Log.d("NoOganizer", "3rd Organizer Missing");
		}
	}

	private void setImage() {
		int imageResourceId;
		imageResourceId = R.drawable.penalty_shootout;

		try {
			imageResourceId = getResources().getIdentifier(
					"drawable/" + getFileName(string_event), null,
					getPackageName());
		} catch (NotFoundException e) {
			imageResourceId = R.drawable.penalty_shootout;
			Toast.makeText(getApplicationContext(), "Image Resource Not Found",
					Toast.LENGTH_SHORT).show();
			Log.d("ImageError", e.getMessage());
		} catch (IllegalStateException e) {
			// TODO: handle exception
			imageResourceId = R.drawable.penalty_shootout;
			Toast.makeText(getApplicationContext(), "Image Resource Not Found",
					Toast.LENGTH_SHORT).show();
			Log.d("ImageError", e.getMessage());
		} catch (RuntimeException e) {
			// TODO: handle exception
			imageResourceId = R.drawable.penalty_shootout;
			Toast.makeText(getApplicationContext(), "Image Resource Not Found",
					Toast.LENGTH_SHORT).show();
			Log.d("ImageError", e.getMessage());
		} catch (Exception e) {
			imageResourceId = R.drawable.penalty_shootout;
			Toast.makeText(getApplicationContext(), "Image Resource Not Found",
					Toast.LENGTH_SHORT).show();
			Log.d("ImageError", e.getMessage());
		} finally {
			imageView.setImageDrawable(getResources().getDrawable(
					imageResourceId));
		}

	}

	private void setText() {
		// textView_heading_1.setText(string_event);
		// textView_heading_2.setText("Details");
		// textView_heading_3.setText("Organizers");
		getActionBar().setTitle(string_event);

		if (string_introduction != null) {
			textView_introduction.setText(string_introduction);
		}

		if ((string_date != null) && (string_venue != null)
				&& (string_venue != null)) {
			textView_date.setText(string_date);
			textView_timings.setText(string_timings);
			textView_venue.setText(string_venue);
		}

		if (string_organizer_1_contact != null) {
			textView_organizer_1_name.setText(string_organizer_1_name);
			textView_organizer_1_contact.setText(string_organizer_1_contact);
		}

		if (string_organizer_2_contact != null) {
			textView_organizer_2_name.setText(string_organizer_2_name);
			textView_organizer_2_contact.setText(string_organizer_2_contact);
		}

		if (string_organizer_3_contact != null) {
			textView_organizer_3_name.setText(string_organizer_3_name);
			textView_organizer_3_contact.setText(string_organizer_3_contact);
		}

		if (string_organizer_4_contact != null) {
			textView_organizer_4_name.setText(string_organizer_4_name);
			textView_organizer_4_contact.setText(string_organizer_4_contact);
		}

		if (string_organizer_5_contact != null) {
			textView_organizer_5_name.setText(string_organizer_5_name);
			textView_organizer_5_contact.setText(string_organizer_5_contact);
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.events_textView_organizer_1_contact
				&& !textView_organizer_1_contact.getText().toString()
						.equals("")) {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:+91"
					+ textView_organizer_1_contact.getText().toString()));
			callPlaced = true;
			callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(callIntent);
		} else if (v.getId() == R.id.events_textView_organizer_2_contact
				&& !textView_organizer_2_contact.getText().toString()
						.equals("")) {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:+91"
					+ textView_organizer_2_contact.getText().toString()));
			callPlaced = true;
			callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(callIntent);
		} else if (v.getId() == R.id.events_textView_organizer_3_contact
				&& !textView_organizer_3_contact.getText().toString()
						.equals("")) {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:+91"
					+ textView_organizer_3_contact.getText().toString()));
			callPlaced = true;
			callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(callIntent);
		} else if (v.getId() == R.id.events_textView_organizer_4_contact
				&& !textView_organizer_4_contact.getText().toString()
						.equals("")) {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:+91"
					+ textView_organizer_4_contact.getText().toString()));
			callPlaced = true;
			callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(callIntent);
		} else if (v.getId() == R.id.events_textView_organizer_5_contact
				&& !textView_organizer_5_contact.getText().toString()
						.equals("")) {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:+91"
					+ textView_organizer_5_contact.getText().toString()));
			callPlaced = true;
			callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(callIntent);
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		int newY;
		int diff;

		newY = v.getScrollY();
		diff = newY - oldY;
		oldY = newY;

		// event.g

		alpha += diff;

		imageView.setAlpha(255 - Math.min(alpha, 100));
		linearLayout.setAlpha(alpha / 150.0f);

		return false;
	}

	private void createNavigationDrawer(Bundle savedInstanceState) {
		mTitle = mDrawerTitle = getTitle();
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuSubtitles = getResources().getStringArray(
				R.array.nav_drawer_subscripts);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		addItemsToNavigationDrawer();

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		adapter.setDrawable(getResources().getDrawable(R.drawable.iiita_night2));
		adapter.setText(ifuser);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_navigation_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {

			public void onDrawerClosed(View view) {
				//getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				//getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	private void addItemsToNavigationDrawer() {
		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],
				navMenuSubtitles[0], navMenuIcons.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],
				navMenuSubtitles[1], navMenuIcons.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],
				navMenuSubtitles[2], navMenuIcons.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3],
				navMenuSubtitles[3], navMenuIcons.getResourceId(3, -1), true,
				"22"));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4],
				navMenuSubtitles[4], navMenuIcons.getResourceId(4, -1)));
		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5],
				navMenuSubtitles[5], navMenuIcons.getResourceId(5, -1), true,
				"50+"));

		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6],
				navMenuSubtitles[6], navMenuIcons.getResourceId(6, -1), true,
				"50+"));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7],
				navMenuSubtitles[7], navMenuIcons.getResourceId(7, -1), true,
				"50+"));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8],
				navMenuSubtitles[8], navMenuIcons.getResourceId(8, -1), true,
				"50+"));
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	/*
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.events_actionMap).setVisible(!drawerOpen);
		menu.findItem(R.id.events_actionRegister).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
			case 0:
				// fragment = new PagesFragment();
				break;
			case 1:
				Intent in = new Intent("com.aparoksha.main.navigationdrawer");
				in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(in);
				finish();
				break;
			case 2:
				Intent intent = new Intent("com.aparoksha.main.eventsbyday");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(intent);
				finish();
				break;
			case 3:
				// fragment = new PagesFragment();
				Intent i = new Intent("com.aparoksha.main.mainactivity");
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i);
				finish();
				break;
			case 4:
				// fragment = new PagesFragment();
				Intent i2 = new Intent("com.aparoksha.main.favorites");
				i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i2);
				finish();
				break;
			case 5:
				Intent i3 = new Intent("com.aparoksha.main.Login");
				i3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i3);
				finish();
				// fragment = new PagesFragment();
				break;
			case 6:
				Intent i4 = new Intent("com.aparoksha.main.Register");
				i4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i4);
				finish();
				// fragment = new PagesFragment();
				break;
			case 7:
				// fragment = new PagesFragment();
				Intent i9 = new Intent("com.aparoksha.main.organizers");
				i9.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i9);
				finish();
				break;
			case 8:
				Intent i8 = new Intent("com.aparoksha.main.app_developers");
				i8.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i8);
				finish();
				// fragment = new PagesFragment();
				break;
			default:
				break;
		}

		// if (fragment != null) {
		// FragmentManager fragmentManager = getFragmentManager();
		// fragmentManager.beginTransaction()
		// .replace(R.id.frame_container, fragment).commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
		// setTitle(navMenuTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
		// } else {
		// error in creating fragment
		// Log.e("MainActivity", "Error in creating fragment");
		// }
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private String getFileName(String eventName) {
		String imageName;
		StringTokenizer stringTokenizer;

		stringTokenizer = new StringTokenizer(eventName);
		imageName = stringTokenizer.nextToken().toLowerCase();

		while (stringTokenizer.hasMoreTokens()) {
			imageName += "_" + stringTokenizer.nextToken().toLowerCase();
		}

		return imageName;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (!callPlaced) {
			// if (!resultSet) {
			// Intent intent = new Intent();
			// intent.putExtra("Result", 1);
			// setResult(RESULT_OK, intent);
			// }
			finish();
		}
	}

	private void reminder() {

		if (day < 0) {
			return;
		}

		callPlaced = true;
		int randomInteger = random.nextInt() % 1000;

		Calendar Calendar_Object = Calendar.getInstance();
		Calendar_Object.set(Calendar.MONTH, 2);
		Calendar_Object.set(Calendar.YEAR, 2015);
		Calendar_Object.set(Calendar.DAY_OF_MONTH, day);
		Calendar_Object.set(Calendar.HOUR_OF_DAY, timeHour);
		Calendar_Object.set(Calendar.MINUTE, timeMinutes);
		Calendar_Object.set(Calendar.SECOND, 0);
		// MyView is my current Activity, and AlarmReceiver is the
		// BoradCastReceiver
		Intent myIntent = new Intent(Events.this, Display_notification.class);
		Bundle bdl = new Bundle();

		bdl.putString("Event", string_event);
		bdl.putString("Date", string_date);
		bdl.putString("Time", string_timings);
		bdl.putString("Venue", string_venue);

		myIntent.putExtras(bdl);

		// PendingIntent pendingIntent = PendingIntent.getBroadcast(Events.this,
		// 0, myIntent, 0);

		/* check from here */

		PendingIntent displayIntent = PendingIntent.getActivity(
				getBaseContext(), randomInteger, myIntent,
				PendingIntent.FLAG_ONE_SHOT);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		/*
		 * The following sets the Alarm in the specific time by getting the long
		 * value of the alarm date time which is in calendar object by calling
		 * the getTimeInMillis(). Since Alarm supports only long value , we're
		 * using this method.
		 */
		alarmManager.set(AlarmManager.RTC, Calendar_Object.getTimeInMillis(),
				displayIntent);

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		getActionBar().setTitle(string_event);
	}

	class AttemptRegister extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean	failure	= false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Events.this);
			pDialog.setMessage("Registering...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;
			String username = ifuser;
			String event = string_event;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("event", getEventCode()));

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL,
						"POST", params);

				// check your log for json response
				Log.d("Login attempt", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					Log.d("Register Successful!", json.toString());
					// Intent i = new Intent(Login.this,
					// Navigationdrawer.class);
					// finish();
					// startActivity(i);
					return json.getString(TAG_MESSAGE);
				} else if (success == 0) {
					Log.d("regtr_Fail_or_already_exists ",
							json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(Events.this, file_url, Toast.LENGTH_LONG).show();
			}

		}

	}

	String getEventCode() {
		int i;

		String[] eventsNames = { "Eureka", "Alkhwarizm", "Code Show",
				"Infinitum", "C Fresh", "Confondere", "QWERTY Wars",
				"Perplexus", "Biomeda", "IT Quiz", "Techno Booz", "NU Vision",
				"V Flare", "Platzen", "Cold Fire", "Penalty Shootout",
				"AI Pod", "Xplorer", "Bolt", "Black Box", "Techno Fault",
				"Webkriti", "Back Bone", "Riddlonics", "Wolf of 2311",
				"PS I Luv Electronics", "Thought Bazaar", "Hackathon" };
		String[] eventCodes = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "22", "23", "24", "29", "30", "31", "32", "33" };

		for (i = 0; i < eventsNames.length; i++) {
			if (eventsNames[i].equalsIgnoreCase(string_event)) {
				return eventCodes[i];
			}
		}

		return null;
	}

}
