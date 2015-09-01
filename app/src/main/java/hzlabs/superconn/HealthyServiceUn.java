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
public class HealthyServiceUn extends Service{

    AlarmManager alarmManager5;
    PendingIntent pendingIntent5;
    Intent myIntent5;





    @Override
    public void onCreate() {
        super.onCreate();






            boolean isEnabled = Settings.System.getInt(this.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) == 1;
            if (isEnabled == true) {

                modifyAirplanemode(false);
                Toast.makeText(getApplicationContext(), "Airplane Mode OFF",
                        Toast.LENGTH_LONG).show();
            }

        myIntent5 = new Intent(this, HealthyService.class);

        pendingIntent5 = PendingIntent.getService(this, 0, myIntent5, 0);


        alarmManager5 = (AlarmManager) getSystemService(ALARM_SERVICE);


        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.add(Calendar.MINUTE, 1240);

        alarmManager5.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent5);




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
    public IBinder onBind(Intent intent) {
        return null;
    }
}
