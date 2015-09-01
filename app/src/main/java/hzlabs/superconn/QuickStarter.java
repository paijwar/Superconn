package hzlabs.superconn;//package hzlabs.superconn;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ServiceInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;



/**
 * Created by Huzeyfe on 3.5.2015.
 */
public class QuickStarter extends Service {




    private WindowManager mWindowManager;

    private ImageView image;

    private String filename = "qs_genel2bc41a.txt";
    private String filename2 = "qs_durum2bc41a.txt";
    private String filepath = "FileStorage";
    private String fileStatus = "son1bc41a.txt";

    Button stop;


    File QsFile;
    File QsSFile;
    File StatusFile;

    boolean widget_state = false;

    public static PendingIntent pendingIntent;

    public static PendingIntent pendingIntent2;

    public static AlarmManager alarmManager;

    public static Intent myIntent;

    public static Intent myIntent2;

    public boolean durum1 = true;
    public boolean durum12 = false;


/*
    //DOUBLE CLICK VALUES  >>

    static final int MAX_DURATION = 200;

    long startTime;
*/




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {





        boolean ara;

        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        QsFile = new File(directory , filename);
        StatusFile = new File(directory, fileStatus);


        boolean s;
        image = new ImageView(this);


        image.setAdjustViewBounds(true);
        File directory2 = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        QsSFile = new File(directory2 , filename2);



       /* ara = bilgi_durum_oku();
        if(ara==true){


           stopSelf();


        }
*/
        s=bilgi_durum_oku_s();

        if(s==false) {



            image.setImageResource(R.drawable.widget);
        }else if(s==true){
            image.setImageResource(R.drawable.widget2);
        }

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        final WindowManager.LayoutParams paramsF = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        paramsF.gravity = Gravity.TOP | Gravity.LEFT;
        paramsF.x = 0;
        paramsF.y = 500;
        Display display = mWindowManager.getDefaultDisplay();
        int width = display.getWidth();

        image.setMaxHeight(width/7);
        image.setMaxWidth(width/7);








        try {

            image.setOnTouchListener(new View.OnTouchListener() {
                // WindowManager.LayoutParams paramsT = paramsF;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;
                private boolean isOnClick;
                private final float SCROLL_THRESHOLD = 10;
                final Intent RepInn = new Intent(QuickStarter.this,RepeaterService.class);


                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = paramsF.x;
                            initialY = paramsF.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            isOnClick = true;

                        /*    if(System.currentTimeMillis() - startTime <= MAX_DURATION)
                            {
                                //DOUBLE TAP

                            }*/


                            break;


                        case MotionEvent.ACTION_CANCEL:




                        case MotionEvent.ACTION_UP:




                            paramsF.x =0;
                            paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
                            mWindowManager.updateViewLayout(v, paramsF);


                            if (isOnClick) {

                                //TODO onClick code

                                if(widget_state==false){

                                    alarmdan_cık();
                                    dataDurumBelirle(true);

                                    bilgi_durum_yaz_s("s");

                                    image.setImageResource(R.drawable.widget2);//yeşil yap

                                    Toast.makeText(QuickStarter.this,"Data connection:ON",Toast.LENGTH_SHORT).show();
                                    widget_state = true;


                                }else if(widget_state==true) {

                                    startService(RepInn);
                                    image.setImageResource(R.drawable.widget);
                                    bilgi_durum_yaz_s("");
                                    Toast.makeText(QuickStarter.this,"Supercon is resuming",Toast.LENGTH_SHORT).show();
                                    widget_state = false;
                                }

                                mWindowManager.updateViewLayout(v, paramsF);//renk değişimi için

                            }


                            //  startTime = System.currentTimeMillis();


                            break;

                        case MotionEvent.ACTION_MOVE:
                            paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
                            paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);



                            mWindowManager.updateViewLayout(v, paramsF);

                            if (isOnClick && (Math.abs(initialX - paramsF.x) > SCROLL_THRESHOLD || Math.abs(initialY - paramsF.y) > SCROLL_THRESHOLD)) {
                                //Log.i(LOG_TAG, "movement detected");
                                isOnClick = false;
                            }
                            break;

                    }
                    return false;
                }


            });


            mWindowManager.addView(image, paramsF);


        } catch (Exception e) {
            e.printStackTrace();
        }








        Log.i("Service", "onStartCommand");
        return android.app.Service.START_STICKY;

    }

    @Override

    public void onCreate() {

// TODO Auto-generated method stub



    }






    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {

        Toast.makeText(this,"Superconn icon will appear soon",Toast.LENGTH_SHORT).show();

        return super.bindService(service, conn, flags);
    }

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

    public void alarmdan_cık(){


        myIntent = new Intent(QuickStarter.this, RepeaterService.class);

        pendingIntent = PendingIntent.getService(QuickStarter.this, 0, myIntent, 0);


        myIntent2 = new Intent(QuickStarter.this, Repeater2.class);

        pendingIntent2 = PendingIntent.getService(QuickStarter.this, 0, myIntent2, 0);


        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);

        alarmManager.cancel(pendingIntent2);


    }




    public void bilgi_durum_yaz_qs(String durum) {

        try {
            FileOutputStream fos = new FileOutputStream(QsFile);
            fos.write(durum.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean bilgi_durum_oku_st() {


        String myData = "";


        try {
            FileInputStream fis = new FileInputStream(StatusFile);
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

        String sdf = "a";
        String ghj = "";

        asd = myData.toString();

        int a = asd.length();


        if (asd.contains("a")) {

            durum12 = true;
        } else if (asd == ghj) {

            durum12 = false;
        } else if (asd == null) {

            durum12 = false;
        } else {

            durum12 = false;
        }


        return durum12;

    }

    public boolean bilgi_durum_oku(){




        String myData="";


        try {
            FileInputStream fis = new FileInputStream(QsFile);
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

        String ghj="";

        asd=myData.toString();

        int a = asd.length();



        if(asd.contains("q")){

            durum1=true;}

        else if (asd==ghj){

            durum1=false;}

        else if (asd==null){

            durum1=false;}
        else{

            durum1=false;
        }




        return durum1;
    }
    public void bilgi_durum_yaz_s(String durum) {

        try {
            FileOutputStream fos = new FileOutputStream(QsSFile);
            fos.write(durum.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public boolean bilgi_durum_oku_s(){




        String myData="";


        try {
            FileInputStream fis = new FileInputStream(QsSFile);
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

        String ghj="";

        asd=myData.toString();


        if(asd.contains("s")){

            durum1=true;}

        else if (asd==ghj){

            durum1=false;}

        else if (asd==null){

            durum1=false;}
        else{

            durum1=false;
        }


        return durum1;
    }

    @Override
    public void onDestroy() {


            bitis();
            super.onDestroy();

    }


    public void bitis(){

        mWindowManager.removeViewImmediate(image);
        bilgi_durum_yaz_s("");

    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {



        Log.e("FLAGX : ", ServiceInfo.FLAG_STOP_WITH_TASK + "");
        Intent restartServiceIntent = new Intent(getApplicationContext(),
                this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(
                getApplicationContext(), 1, restartServiceIntent,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext()
                .getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);





        super.onTaskRemoved(rootIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}
