package com.aparoksha.main;

import com.aparoksha.main.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
 
public class Display_notification extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        //---get the notification ID for the notification; 
        // passed in by the MainActivity---
     //   int notifID = getIntent().getExtras().getInt("NotifID");
     //   String event = getIntent().getExtras().getString("event");
      //  String address = getIntent().getExtras().getString("address");
        
        
        Bundle bdl =   getIntent().getExtras();
        
        
     //   Bundle bdl;
		
	//	bdl = intent.getExtras();
		String event_name = bdl.getString("Event");
		String event_date = bdl.getString("Date");
		String event_time = bdl.getString("Time");
		String event_venue = bdl.getString("Venue");
        
        //---PendingIntent to launch activity if the user selects 
        // the notification---
        //Intent i = new Intent("net.learn2develop.AlarmDetails");
        //i.putExtra("NotifID", notifID);  
 
        //PendingIntent detailsIntent = 
          //  PendingIntent.getActivity(this, 0, i, 0);
        
        
        
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.aparoksha_logo_small);
        mBuilder.setContentTitle("Reminder");
        mBuilder.setContentText(event_name + "  is gonna start in half an hour,brace urselves ");
        mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        //LED
        mBuilder.setLights(Color.YELLOW, 3000, 3000);
           
        Intent resultIntent = new Intent(this, Events.class);
        resultIntent.putExtra("Event", event_name);
        //resultIntent.putExtra("event", event);
      //  resultIntent.putExtra("address", address);
        
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Events.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
               stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
        	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        	    
        Notification notification = null; 
        	// notificationID allows you to update the notification later on.
        	mNotificationManager.notify(0, mBuilder.build());
        	//mNotificationManager.cancel(notifID);
        /*NotificationManager nm = (NotificationManager)
            getSystemService(NOTIFICATION_SERVICE);
        Notification notif = new Notification(
            R.drawable.images, 
            "Time's up!",
            System.currentTimeMillis());
 
        CharSequence from = "AlarmManager - Time's up!";
        CharSequence message = "This is your alert, courtesy of the AlarmManager";        
        notif.setLatestEventInfo(this, from, message, detailsIntent);
 
        //---100ms delay, vibrate for 250ms, pause for 100 ms and
        // then vibrate for 500ms---
        notif.vibrate = new long[] { 100, 250, 100, 500};        
        nm.notify(notifID, notif);*/
        //---destroy the activity---
        finish();
    }
}