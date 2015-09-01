package hzlabs.superconn;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;

/**
 * Created by Huzeyfe on 3.5.2015.
 */
public class RepeaterService extends Service {
    private String repeat_time1 = "repeatLongbc41a.txt";
    private String repeat_time2 = "repeatShortbc41a.txt";
    private String filepath = "FileStorage";
    File repeat_file;
    File repeat_file_2;
    public static PendingIntent pendingIntent;

    public static AlarmManager alarmManager;

    public static Intent myIntent;

    private boolean durum;


    @Override
    public void onCreate() {

        // TODO Auto-generated method stub
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        repeat_file = new File(directory, repeat_time1);
        repeat_file_2 = new File(directory, repeat_time2);


    }

    @Override

    public void onStart(Intent intent, int startId) {

        // TODO Auto-generated method stub

        super.onStart(intent, startId);


            durum = durumNe();

            if (durum) {

                kurVeAta_Kapa(bilgi_durum_oku_rep1());

            } else if (!durum) {

                kurVeAta_Ac(bilgi_durum_oku_rep1());

            }


        }


    @Override

    public void onDestroy() {

        // TODO Auto-generated method stub

        super.onDestroy();
        this.stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //bu metod service de işe yaramıyor çünkü
    //cancel alarmını calıştırabilmek için iletişim kurulabilecek
    //bir service bulunmuyor %95 ihtimalle

    /*public static void cancel_alarm(){
        alarmManager.cancel(pendingIntent);
    }*/

    public boolean dataDurumBelirle(boolean ON) {
        try {
            //connectivitymaneger nesnesine sistem konnectivity service yi bağlıyoruz

            final ConnectivityManager conman = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

            //burada connectivity manager system service sınıfının ismini bir class a atıyoruz
            final Class conmanClass = Class.forName(conman.getClass().getName());
            //sınıfa atadığımız bu nesnedende dosya ismini alıyoruz
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");

            //ulaşılabilir yap
            iConnectivityManagerField.setAccessible(true);

            //bu dosyadan bir object nesnesi oluşturarak conman ı bu nesneye döndürüyoruz
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);

            //tekrar bu dosyadan bir nesne olş. ama class nesnesi. ve dosya ismini ist.
            final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());

            //bir metod olş. ve iste
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);

            //ata
            setMobileDataEnabledMethod.setAccessible(true);
            //
            //
            setMobileDataEnabledMethod.invoke(iConnectivityManager, ON);
        } catch (Exception e) {
        }
        return true;
    }

    public void kurVeAta_Kapa(int repTime) {

        //kapalı konumda calısır ve uzun sürer

        repTime=bilgi_durum_oku_rep1();

        myIntent = new Intent(this, Repeater2.class);

        pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);


        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.add(Calendar.SECOND, repTime);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        dataDurumBelirle(false);

        this.onDestroy();

    }

    public void kurVeAta_Ac(int repTime2) {
        //acık konum, kısa sürer.

        repTime2=bilgi_durum_oku_rep2();


        myIntent = new Intent(this, Repeater2.class);

        pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);


        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.add(Calendar.SECOND, repTime2);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


        dataDurumBelirle(true);

        this.onDestroy();

    }


    public boolean durumNe() {

        boolean mobileDataEnabled = false; // Assume disabled

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        try {

            Class cmClass = Class.forName(cm.getClass().getName());

            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");

            method.setAccessible(true); // Make the method callable

            // get the setting for "mobile data"

            mobileDataEnabled = (Boolean)method.invoke(cm);

        } catch (Exception e) {

            // Some problem accessible private API and do whatever error handling you want here

        }

        return mobileDataEnabled;

    }


    public int bilgi_durum_oku_rep1() {


        String myData = "";


        try {
            FileInputStream fis = new FileInputStream(repeat_file);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData = myData + strLine;

            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String asd;

        String ghj = "";

        asd = myData.toString();

        int a = asd.length();


        if (asd == ghj) {

            return 300;

        } else if (asd == null) {

            return 300;
        } else if(asd.contains("1")){

            return 150;
        }
        else if(asd.contains("2")){

            return 600;
        }
        else if(asd.contains("3")){

            return 900;
        }
        else if(asd.contains("5")){

            return 1800;
        }

        return 300;
    }

    public int bilgi_durum_oku_rep2() {


        String myData = "";


        try {
            FileInputStream fis = new FileInputStream(repeat_file_2);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData = myData + strLine;

            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String asd;

        String ghj = "";

        asd = myData.toString();

        int a = asd.length();


        if (asd == ghj) {

            return 14;

        } else if (asd == null) {

            return 14;
        } else if(asd.contains("q")){

            return 21;
        }

        return 14;
    }







}