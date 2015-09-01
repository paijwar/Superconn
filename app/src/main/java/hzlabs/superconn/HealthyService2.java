package hzlabs.superconn;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Huzeyfe on 12.5.2015.
 */
public class HealthyService2 extends Service{


    AlarmManager alarmManager3;
    PendingIntent pendingIntent3;
    Intent myIntent3;



    @Override
    public void onCreate() {
        super.onCreate();


        boolean isEnabled = Settings.System.getInt(this.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) == 1;
        if (isEnabled == true)
        {
            modifyAirplanemode(false);
            Toast.makeText(getApplicationContext(), "Airplane Mode OFF",
                    Toast.LENGTH_LONG).show();
        }


        myIntent3 = new Intent(this, HealthyService.class);

        pendingIntent3 = PendingIntent.getService(this, 0, myIntent3, 0);


        alarmManager3 = (AlarmManager) getSystemService(ALARM_SERVICE);


        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.add(Calendar.MINUTE, 1240);

        alarmManager3.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent3);



        onDestroy();



    }


    @SuppressWarnings("deprecation")
    public void airPlanemodeON(View v) {
        boolean isEnabled = Settings.System.getInt(this.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) == 1;
        if (isEnabled == false) {
            modifyAirplanemode(true);
            Toast.makeText(getApplicationContext(), "Airplane Mode ON",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void airPlanemodeOFF(View v) {
        boolean isEnabled = Settings.System.getInt(this.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) == 1;
        if (isEnabled == true)// means this is the request to turn ON AIRPLANE mode
        {
            modifyAirplanemode(false);
            Toast.makeText(getApplicationContext(), "Airplane Mode OFF",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void modifyAirplanemode(boolean mode) {
        Settings.System.putInt(getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, mode ? 1 : 0);// Turning ON/OFF Airplane mode.

        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);// creating intent and Specifying action for AIRPLANE mode.
        intent.putExtra("state", !mode);// indicate the "state" of airplane mode is changed to ON/OFF
        sendBroadcast(intent);// Broadcasting and Intent

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
