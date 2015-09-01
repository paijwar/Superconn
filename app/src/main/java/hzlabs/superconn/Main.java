package hzlabs.superconn;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

public class Main extends Activity implements OnItemClickListener {

    public Button onayButton;
    public Button redButton;
    public boolean onayState;
    static final String EXTRA_MAP = "map";

    public Button startButton;
    public Button acilisButton;


    public Button infButton;

    String calisiyor = "a";
    String durmus = "";

    private String fileStatus = "son1bc41a.txt";
    private String repeat_time1 = "repeatLongbc41a.txt";
    private String filenameqs = "qs_genel2bc41a.txt";
    private String filenamerh = "repeatShortbc41a.txt";
    private String filenamesw = "sw_genel2bc41a.txt";
    private String filenameum = "um_genel2bc41a.txt";


    private String filepath = "FileStorage";

    File StatusFile;
    File QsFile;
    File repeat_file;
    //File repeat_file_2;
    File RhFile;
    File SwFile;
    File UmFile;



    Button n11;



    boolean durum1;
    boolean durumqs;
    boolean durumrh;
    boolean durumsw;
    boolean durumum;

////////////SURE
    RelativeLayout sure_menu;
    RelativeLayout onayRel;
    RelativeLayout repMenu;
    RelativeLayout aramenu2;
    RelativeLayout infRel;


    public static PendingIntent pendingIntent;

    public static AlarmManager alarmManager;

    public static Intent myIntent;

    public Intent wid_Intent;


    public static Intent myIntent2;

    public static PendingIntent pendingIntent2;


    //public static AlarmManager alarmManager2;


    public static Intent myIntent3;
    public static Intent myIntent4;
    public static Intent myIntent5;
    public static PendingIntent pendingIntent3;
    public static PendingIntent pendingIntent4;
    public static PendingIntent pendingIntent5;
    public static AlarmManager alarmManager3;
    public static AlarmManager alarmManager5;
    /**
     *
     *  *******************GÖRSEL DEKLARELER
     *
     */


    private Switch switchQS;
    private Switch switchRH;
    private Switch switchSW;
    //private Switch switchUM;
    int pos;
    int pos1;

    static final LauncherIcon[] ICONS = {
            new LauncherIcon(R.drawable.newup, "Update Frequency", "newup.png"),
            new LauncherIcon(R.drawable.rep_time, "Set Repeat Time", "rep_time.png"),
            new LauncherIcon(R.drawable.quick_starter, "Quick Starter", "quick_starter.png"),
    };

/////////animation
    private ViewFlipper viewFlipper;
    private float lastX;
    View menu;
    View onay;
    View repMenuV;
    View menu2;
    View inf;

    RadioGroup rg;




    RadioButton radioBtn1;
    RadioButton radioBtn2;
    RadioButton radioBtn3 ;
    RadioButton radioBtn4 ;
    RadioButton radioBtn5 ;





    public TextView up_text;
    public TextView qs_text;
    public TextView rh_text;

    public Button new_start;
    public Button new_rep;
    public Button new_set;

    public Button reset_button;
    //public Button about_button;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DisplayMetrics de = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(de);

        final int height = de.heightPixels;
        final int width = de.widthPixels;


        acilisButton = (Button)findViewById(R.id.acilis);
        acilisButton.getLayoutParams().height=height;
        acilisButton.getLayoutParams().width=width;




        /*

                     BEKLEME SÜRESİ EKLE

         */

        /////////////ON EKRANA CIKACAK TUM BILDIRIMLER

        sure_menu = (RelativeLayout)findViewById(R.id.uyarı_rel);

        onayRel = (RelativeLayout)findViewById(R.id.uyari);

        repMenu = (RelativeLayout)findViewById(R.id.rep);


        aramenu2 = (RelativeLayout)findViewById(R.id.ara2);

        infRel = (RelativeLayout)findViewById(R.id.infRel);


        up_text = (TextView)findViewById(R.id.up_text);
        qs_text = (TextView)findViewById(R.id.qs_text);
        rh_text = (TextView)findViewById(R.id.rh_text);



        LayoutInflater myinflate = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
        menu2 =myinflate.inflate(R.layout.rep_time2, null, false);
        menu = myinflate.inflate(R.layout.rep_time, null, false);
        onay = myinflate.inflate(R.layout.sure, null, false);
        repMenuV = myinflate.inflate(R.layout.radio, null, false);
        inf = myinflate.inflate(R.layout.justinf, null, false);


        infRel.addView(inf);
        infRel.setVisibility(View.GONE);


        repMenu.getLayoutParams().height=height*3/5;
        repMenu.getLayoutParams().width=width*7/8;
        repMenu.setX(width/16);
        repMenu.setY(height/4);
        repMenu.addView(repMenuV);

        repMenu.setVisibility(View.GONE);


        onayRel.getLayoutParams().height=height*1/3;
        onayRel.getLayoutParams().width=width*5/6;
        onayRel.setX(width/12);
        onayRel.setY(height/3);
        onayRel.addView(onay);

        onayRel.setVisibility(View.GONE);


        sure_menu.getLayoutParams().height=height;
        sure_menu.getLayoutParams().width=width;
        sure_menu.setX(0);
        sure_menu.setY(0);
        sure_menu.addView(menu);

        sure_menu.setVisibility(View.GONE);


        aramenu2.getLayoutParams().height=height;
        aramenu2.getLayoutParams().width=width;
        aramenu2.setX(0);
        aramenu2.setY(0);
        aramenu2.addView(menu2);

            aramenu2.setVisibility(View.GONE);


        ///////////////////MENULER BURADA//////////////////////////////
        ////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////





        new_start = (Button)findViewById(R.id.new_start);
        new_rep = (Button)findViewById(R.id.new_rep);
        new_set = (Button)findViewById(R.id.new_set);





        ///////////////////ANIMATION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);




        GridView gridview = (GridView) findViewById(R.id.dashboard_grid);
        gridview.setAdapter(new ImageAdapter(this));
        gridview.setOnItemClickListener(this);


        gridview.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_MOVE;
            }
        });





        onayButton = (Button)findViewById(R.id.ok_button);
        redButton = (Button)findViewById(R.id.cancel_button);





        ///////TUM DOSYALAR ICIN

        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);


        //FILE TANIMLAMALARI
        repeat_file = new File(directory, repeat_time1);
        StatusFile = new File(directory, fileStatus);
        QsFile = new File(directory , filenameqs);
        RhFile = new File(directory, filenamerh);
        SwFile = new File(directory, filenamesw);
        UmFile = new File(directory, filenameum);
        //////////QUICK STARTER MENUSU GORSELLERI


        switchQS = (Switch) findViewById(R.id.switchqs);
        switchRH = (Switch) findViewById(R.id.switchrh);
        switchSW = (Switch) findViewById(R.id.switchsw);
        //switchUM = (Switch) findViewById(R.id.switchum);


        //////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        /////////////////reptıme TAKIMI
        /////////////////////////////////////////////////////////

        rg = (RadioGroup) findViewById(R.id.radioGroup);


       radioBtn1 = new RadioButton(this);
      radioBtn2 = new RadioButton(this);
         radioBtn3 = new RadioButton(this);
        radioBtn4 = new RadioButton(this);
        radioBtn5 = new RadioButton(this);






        radioBtn1.setText("2,5 min");
        radioBtn2.setText("5 min (recommended)");
        radioBtn3.setText("10 min");
        radioBtn4.setText("15 min");
        radioBtn5.setText("30 min");



        rg.addView(radioBtn1);
        rg.addView(radioBtn2);
        rg.addView(radioBtn3);
        rg.addView(radioBtn4);
        rg.addView(radioBtn5);




        //ALARM INTENTİ !!!
        //!!!!!!!!!!!!!!!!!
        final Intent RepIn = new Intent(this, RepeaterService.class);

        //QUICKSTARTER INTET
        wid_Intent = new Intent(Main.this, QuickStarter.class);
        //dosyaya yer bildirimi yapılıyor  >>




///////////////////////////////////////////////////////
        if(bilgi_durum_oku_qs_state()){


            switchQS.setChecked(false);

        }
        else {

            switchQS.setChecked(true);


        //    n4.setText("ready", TextView.BufferType.NORMAL);
        }
/////////////////////////////////////////////
        if(bilgi_durum_oku_rh_state()){



            switchRH.setChecked(true);


        }
        else {

            switchRH.setChecked(false);



        }

//////////////////////////////////////////////////

        if(bilgi_durum_oku_sw_state()){



            switchSW.setChecked(true);
        }
        else {

            switchSW.setChecked(false);
        }
/////////////////////////////////////
      /*  if(bilgi_durum_oku_um_state()){



            switchUM.setChecked(false);
        }
        else {

            switchUM.setChecked(true);
        }*/
///////////////////////////////////////////////






        durum1 = bilgi_durum_oku();
        if (durum1 == false) {//stop menüsü


            new_start.setText("START");
            new_start.setBackgroundResource(R.drawable.new_button);



        } else if (durum1 == true) {



            new_start.setText("STOP");
            new_start.setBackgroundResource(R.drawable.new_button_s);



        }

////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////
        /////////////////////////BU BÖLÜM GİRİŞ İÇİN TÜM NESNELERİ DÜZENLER///////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////************************
        check();                      ///radiolar için kontrol ediliyor
//*********************************////////////////////////////


qs_text.setText(R.string.qs_ana);
rh_text.setText(R.string.rh);



////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////

        final ServiceConnection mConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            public void onServiceDisconnected(ComponentName name) {

            }
        };


            new_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {


                    durum1 = bilgi_durum_oku();

                    if (durum1 == true) {//stop menüsü

                        //TRY BLOĞUNA SOK VE HERHANGİ BİR HATADA RESET FONKSİYONLARINI ÇALIŞTIR

                        bilgi_durum_yaz(durmus);
                        alarmdan_cık();

                        new_start.setText("START");
                        Toast.makeText(Main.this,"Superconn stopped",Toast.LENGTH_SHORT).show();
                        new_start.setBackgroundResource(R.drawable.new_button);

                        // mImageView2.clearAnimation();

                        // bindService(wid_Intent,mConnection,BIND_AUTO_CREATE);
                        //wid_Intent.putExtra("1",true);
                        stopService(wid_Intent);

                        if (bilgi_durum_oku_sw_state()) {


                            boolean isEnabled = Settings.System.getInt(getContentResolver(),
                                    Settings.System.AIRPLANE_MODE_ON, 0) == 1;
                            if (isEnabled == true) {
                                modifyAirplanemode(false);
                                Toast.makeText(getApplicationContext(), "Airplane Mode OFF",
                                        Toast.LENGTH_LONG).show();
                            }

                        }


                    } else {
                        if (durum1 == false) {

           /*
            *
            * ANA KOMUTLAR
            *
            */

                            bilgi_durum_yaz(calisiyor);
                            startService(RepIn);

                            if (bilgi_durum_oku_qs_state()) {


                            } else {


                                startService(wid_Intent);


                            }


            /*
            *
            *
            * BURADA START BUTTON ETKİSİZ HALE
            * GETİRİLEREK
            * STOP BUTTON HALİNE GELİR>>
            *
            *
             */

                            new_start.setText("STOP");
                            Toast.makeText(Main.this,"Superconn is running",Toast.LENGTH_SHORT).show();
                            new_start.setBackgroundResource(R.drawable.new_button_s);


                            //onDestroy();
                            //System.exit(0);

                            ///////////////////////////////////////
                            ///////////////////////////////////////
                            //HEALTHY SLEEP KODLARI


                            if (bilgi_durum_oku_sw_state()) {


                                Calendar c = Calendar.getInstance();
                                int hour = c.get(Calendar.HOUR_OF_DAY);
                                int min = c.get(Calendar.MINUTE);
                                int hourAta;
                                int minAta;
                                int ata;


                                hourAta = 1 - hour + 24;
                                minAta = 60 - min;

                                if (minAta == 60) {
                                    minAta = 0;
                                } else {

                                    hourAta = hourAta - 1;

                                }

                                ata = hourAta * 60 + minAta + 20;


                                if (ata > 1240) {
                                    //uçak modunu aç
                                    //uçak modundan çıkaran service




                        /*    boolean isEnabled = Settings.System.getInt(getContentResolver(),
                                    Settings.System.AIRPLANE_MODE_ON, 0) == 1;
                            if (isEnabled == false) {
                                modifyAirplanemode(true);
                                Toast.makeText(getApplicationContext(), "Airplane Mode ON",
                                        Toast.LENGTH_LONG).show();
                            }





                            int newata;

                            newata = 1440-ata;


                            myIntent5 = new Intent(Main.this, HealthyServiceUn.class);

                            pendingIntent5 = PendingIntent.getService(Main.this, 0, myIntent5, 0);


                            alarmManager5 = (AlarmManager) getSystemService(ALARM_SERVICE);


                            Calendar calendar = Calendar.getInstance();

                            calendar.setTimeInMillis(System.currentTimeMillis());

                            calendar.add(Calendar.MINUTE, newata);

                            alarmManager5.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent5);

                        }*/


                                    Toast.makeText(Main.this, "You ara on between 01:20am and 04:40am, Healthy Sleep will work tomorrow", Toast.LENGTH_SHORT).show();

                                }

                                myIntent3 = new Intent(Main.this, HealthyService.class);

                                pendingIntent3 = PendingIntent.getService(Main.this, 0, myIntent3, 0);


                                alarmManager3 = (AlarmManager) getSystemService(ALARM_SERVICE);


                                Calendar calendar = Calendar.getInstance();

                                calendar.setTimeInMillis(System.currentTimeMillis());

                                calendar.add(Calendar.MINUTE, ata);

                                alarmManager3.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent3);


                            }


                        }
                    }




                }else{


                    Toast.makeText(Main.this,"Superconn doesn't work Android L and M",Toast.LENGTH_LONG).show();


                }
                }



            });












     /*   n11.setText("Your internet connection will turn off and" +
                " notifications will update every 5 minute." +
                " When you need internet connection," +
                " just tab the quick starter button which always top of screen." +
                "Healthy Sleep (sizi uykunuzun derin olduğu saatlerde zararlı " +
                "dalgelardan korumak için uçak modunu çalıştırır) ", TextView.BufferType.NORMAL);*/



        new_rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                check();
                sure_menu.setVisibility(View.VISIBLE);
                repMenu.setVisibility(View.VISIBLE);

            }
        });

        new_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewFlipper.setDisplayedChild(1);


            }
        });




        switchQS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                aramenu2.setVisibility(View.VISIBLE);
                onayRel.setVisibility(View.VISIBLE);

                if(isChecked){





                    onayButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bilgi_durum_yaz_qs_state("");
                            aramenu2.setVisibility(View.GONE);
                            onayRel.setVisibility(View.GONE);
                        }
                    });

                    redButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switchQS.setChecked(false);
                            aramenu2.setVisibility(View.GONE);
                            onayRel.setVisibility(View.GONE);
                        }
                    });




                }else{

                    onayButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bilgi_durum_yaz_qs_state("q");
                            aramenu2.setVisibility(View.GONE);
                            onayRel.setVisibility(View.GONE);
                        }
                    });

                    redButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switchQS.setChecked(true);
                            aramenu2.setVisibility(View.GONE);
                            onayRel.setVisibility(View.GONE);
                        }
                    });





                }

            }
        });
        switchRH.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                aramenu2.setVisibility(View.VISIBLE);
                onayRel.setVisibility(View.VISIBLE);

                if(isChecked){





                    onayButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bilgi_durum_yaz_rh_state("q");
                            aramenu2.setVisibility(View.GONE);
                            onayRel.setVisibility(View.GONE);
                        }
                    });

                    redButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switchRH.setChecked(false);
                            aramenu2.setVisibility(View.GONE);
                            onayRel.setVisibility(View.GONE);
                        }
                    });




                }else{

                    onayButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bilgi_durum_yaz_rh_state("");
                            aramenu2.setVisibility(View.GONE);
                            onayRel.setVisibility(View.GONE);
                        }
                    });

                    redButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switchRH.setChecked(true);
                            aramenu2.setVisibility(View.GONE);
                            onayRel.setVisibility(View.GONE);
                        }
                    });





                }

            }


        });
        switchSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {


                    aramenu2.setVisibility(View.VISIBLE);
                    onayRel.setVisibility(View.VISIBLE);

                    if(isChecked){





                        onayButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bilgi_durum_yaz_sw_state("q");
                                aramenu2.setVisibility(View.GONE);
                                onayRel.setVisibility(View.GONE);
                            }
                        });

                        redButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switchSW.setChecked(false);
                                aramenu2.setVisibility(View.GONE);
                                onayRel.setVisibility(View.GONE);
                            }
                        });




                    }else{

                        onayButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bilgi_durum_yaz_sw_state("");
                                aramenu2.setVisibility(View.GONE);
                                onayRel.setVisibility(View.GONE);
                            }
                        });

                        redButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switchSW.setChecked(true);
                                aramenu2.setVisibility(View.GONE);
                                onayRel.setVisibility(View.GONE);
                            }
                        });





                    }

                }else{


                    infRel.setVisibility(View.VISIBLE);
                    switchSW.setChecked(false);


                }

            }
        });

     /*   switchUM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                      bilgi_durum_yaz_um_state("");


                } else {

                      bilgi_durum_yaz_um_state("q");


                }

            }
        });*/


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub


                pos = rg.indexOfChild(findViewById(checkedId));



                //Method 2 For Getting Index of RadioButton
                //pos1 = rg.indexOfChild(findViewById(rg.getCheckedRadioButtonId()));

                //Toast.makeText(getBaseContext(), "Method 2 ID = " + String.valueOf(pos1),
                 //       Toast.LENGTH_SHORT).show();


                aramenu2.setVisibility(View.VISIBLE);
                onayRel.setVisibility(View.VISIBLE);




                switch (pos) {
                    case 0:


                        onayButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                bilgi_durum_yaz_rep1("1");
                                Toast.makeText(getBaseContext(), "Your notifications will update every 150 second.",
                                        Toast.LENGTH_SHORT).show();

                                check();
                                aramenu2.setVisibility(View.GONE);
                                onayRel.setVisibility(View.GONE);


                            }
                        });

                        redButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                aramenu2.setVisibility(View.GONE);
                                onayRel.setVisibility(View.GONE);

                            }
                        });

                        break;
                    case 1:


                        onayButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                bilgi_durum_yaz_rep1("");
                                Toast.makeText(getBaseContext(), "Your notifications will update every 5 minute.",
                                        Toast.LENGTH_SHORT).show();

                                check();
                                aramenu2.setVisibility(View.GONE);
                                onayRel.setVisibility(View.GONE);

                            }
                        });

                        redButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                aramenu2.setVisibility(View.GONE);
                                onayRel.setVisibility(View.GONE);

                            }
                        });


                        break;
                    case 2:



                        onayButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                bilgi_durum_yaz_rep1("2");

                                Toast.makeText(getBaseContext(), "Your notifications will update every 10 minute.",
                                        Toast.LENGTH_SHORT).show();

                                check();
                                aramenu2.setVisibility(View.GONE);
                                onayRel.setVisibility(View.GONE);


                            }
                        });

                        redButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                aramenu2.setVisibility(View.GONE);
                                onayRel.setVisibility(View.GONE);

                            }
                        });

                        break;

                    case 3:



                        onayButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                bilgi_durum_yaz_rep1("3");

                                Toast.makeText(getBaseContext(), "Your notifications will update every 15 minute.",
                                        Toast.LENGTH_SHORT).show();

                                check();
                                aramenu2.setVisibility(View.GONE);
                                onayRel.setVisibility(View.GONE);


                            }
                        });

                        redButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                aramenu2.setVisibility(View.GONE);
                                onayRel.setVisibility(View.GONE);

                            }
                        });

                        break;

                    case 4:




                        onayButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                bilgi_durum_yaz_rep1("4");

                                Toast.makeText(getBaseContext(), "Your notifications will update every 30 minute.",
                                        Toast.LENGTH_SHORT).show();

                                check();
                                aramenu2.setVisibility(View.GONE);
                                onayRel.setVisibility(View.GONE);

                            }
                        });

                        redButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                aramenu2.setVisibility(View.GONE);
                                onayRel.setVisibility(View.GONE);

                            }
                        });

                        break;


                    default:
                        Toast.makeText(getBaseContext(), "case blokları dışında",
                                Toast.LENGTH_SHORT).show();
                        check();
                        //The default selection is RadioButton 1
                }
            }
        });


        reset_button = (Button)findViewById(R.id.reset_button);



        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alarmdan_cık();
                stopService(wid_Intent);
                bilgi_durum_yaz("");
                bilgi_durum_yaz_rep1("");
                bilgi_durum_yaz_qs_state("");
                bilgi_durum_yaz_rh_state("");
                bilgi_durum_yaz_sw_state("");
                Toast.makeText(Main.this,"                 Superconn restarted\nPlease contact us if you have a problem with\n                 Superconn.",Toast.LENGTH_LONG).show();
                onBackPressed();



            }
        });




        acilisButton.setVisibility(View.GONE);



    }



    public void check(){



        int repDuvar2=bilgi_durum_oku_rep1();

        if(repDuvar2==150){

            rg.check(radioBtn1.getId());
            up_text.setText(R.string.rep0);
        }else if(repDuvar2==300){

            rg.check(radioBtn2.getId());
            up_text.setText(R.string.rep1);
        }else if(repDuvar2==600){

            rg.check(radioBtn3.getId());
            up_text.setText(R.string.rep2);
        }else if(repDuvar2==900){

            rg.check(radioBtn4.getId());
            up_text.setText(R.string.rep3);
        }else if(repDuvar2==1800){

            rg.check(radioBtn5.getId());
            up_text.setText(R.string.rep4);
        }


    }


    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            // when user first touches the screen to swap
            case MotionEvent.ACTION_DOWN: {
                lastX = touchevent.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                float currentX = touchevent.getX();

                // if left to right swipe on screen
                if (lastX < currentX) {
                    // If no more View/Child to flip
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Left and current Screen will go OUT from Right
                    viewFlipper.setInAnimation(this, R.anim.in_from_left);
                    viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                    // Show the next Screen
                   // viewFlipper.showNext();
                    viewFlipper.showPrevious();
                }

                // if right to left swipe on screen
                if (lastX > currentX) {
                    //if (viewFlipper.getDisplayedChild() == 1)
                    if(viewFlipper.getDisplayedChild()==viewFlipper.getChildCount()-1)
                        break;
                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Right and current Screen will go OUT from Left
                    viewFlipper.setInAnimation(this, R.anim.in_from_right);
                    viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                    // Show The Previous Screen
                    viewFlipper.showNext();
                }
                break;
            }
        }
        return false;


    }



    @Override/////ustmenu
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {


        switch (position) {

            case 0:



                check();
                sure_menu.setVisibility(View.VISIBLE);
                repMenu.setVisibility(View.VISIBLE);


                break;

            case 1:

viewFlipper.setDisplayedChild(0);


                break;

            case 2:


viewFlipper.setDisplayedChild(1);


                break;


        }
    }


    static class LauncherIcon {
        final String text;
        final int imgId;
        final String map;

        public LauncherIcon(int imgId, String text, String map) {
            super();
            this.imgId = imgId;
            this.text = text;
            this.map = map;
        }

    }


    static class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            return ICONS.length;
        }

        @Override
        public LauncherIcon getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        static class ViewHolder {
            public ImageView icon;
            public TextView text;
        }

        // Create a new ImageView for each item referenced by the Adapter
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder holder;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) mContext.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);

                v = vi.inflate(R.layout.dashboard_icon, null);
                holder = new ViewHolder();
               // holder.text = (TextView) v.findViewById(R.id.dashboard_icon_text);
                holder.icon = (ImageView) v.findViewById(R.id.dashboard_icon_img);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }

            holder.icon.setImageResource(ICONS[position].imgId);
            //holder.text.setText(ICONS[position].text);

            return v;
        }
    }

    public void alarmdan_cık() {


        myIntent = new Intent(this, RepeaterService.class);

        pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);

        myIntent2 = new Intent(this, Repeater2.class);

        pendingIntent2 = PendingIntent.getService(this, 0, myIntent2, 0);

        myIntent3 = new Intent(this, HealthyService.class);

        pendingIntent3 = PendingIntent.getService(this, 0, myIntent3, 0);

        myIntent4 = new Intent(this, HealthyService2.class);

        pendingIntent4 = PendingIntent.getService(this, 0, myIntent4, 0);

        myIntent5 = new Intent(this, HealthyServiceUn.class);

        pendingIntent5 = PendingIntent.getService(this, 0, myIntent5, 0);


        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        alarmManager.cancel(pendingIntent);

        alarmManager.cancel(pendingIntent2);

        alarmManager.cancel(pendingIntent3);

        alarmManager.cancel(pendingIntent4);

        alarmManager.cancel(pendingIntent5);

    }



    public boolean bilgi_durum_oku() {


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

            durum1 = true;
        } else if (asd == ghj) {

            durum1 = false;
        } else if (asd == null) {

            durum1 = false;
        } else {

            durum1 = false;
        }


        return durum1;

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

  /*  public int bilgi_durum_oku_rep2() {


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
        } else if(asd.contains("1")){

            return 21;
        }

        return 14;
    }*/

    public void bilgi_durum_yaz(String durum) {
        String aa = durum;
        try {
            FileOutputStream fos = new FileOutputStream(StatusFile);
            fos.write(durum.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void bilgi_durum_yaz_rep1(String rep1_ic) {

        try {
            FileOutputStream fos = new FileOutputStream(repeat_file);
            fos.write(rep1_ic.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public void bilgi_durum_yaz_rep2(String rep2_ic) {

        try {
            FileOutputStream fos = new FileOutputStream(repeat_file_2);
            fos.write(rep2_ic.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/



    public void bilgi_durum_yaz_qs_state(String durum) {

        try {
            FileOutputStream fos = new FileOutputStream(QsFile);
            fos.write(durum.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bilgi_durum_yaz_rh_state(String durum) {

        try {
            FileOutputStream fos = new FileOutputStream(RhFile);
            fos.write(durum.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bilgi_durum_yaz_sw_state(String durum) {

        try {
            FileOutputStream fos = new FileOutputStream(SwFile);
            fos.write(durum.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bilgi_durum_yaz_um_state(String durum) {

        try {
            FileOutputStream fos = new FileOutputStream(UmFile);
            fos.write(durum.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public boolean bilgi_durum_oku_qs_state(){




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

        String sdf="q";
        String ghj="";

        asd=myData.toString();

        int a = asd.length();



        if(asd.contains("q")){

            durumqs=true;}

        else if (asd==ghj){

            durumqs=false;}

        else if (asd==null){

            durumqs=false;}
        else{

            durumqs=false;
        }




        return durumqs;
    }

    public boolean bilgi_durum_oku_rh_state(){




        String myData="";


        try {
            FileInputStream fis = new FileInputStream(RhFile);
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


        if(asd.contains("q")){

            durumrh=true;}

        else if (asd==ghj){

            durumrh=false;}

        else if (asd==null){

            durumrh=false;}
        else{

            durumrh=false;
        }




        return durumrh;
    }
    public boolean bilgi_durum_oku_sw_state(){




        String myData="";


        try {
            FileInputStream fis = new FileInputStream(SwFile);
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

        String sdf="q";
        String ghj="";

        asd=myData.toString();

        int a = asd.length();



        if(asd.contains("q")){

            durumsw=true;}

        else if (asd==ghj){

            durumsw=false;}

        else if (asd==null){

            durumsw=false;}
        else{

            durumsw=false;
        }




        return durumsw;
    }
    public boolean bilgi_durum_oku_um_state(){




        String myData="";


        try {
            FileInputStream fis = new FileInputStream(UmFile);
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

        String sdf="q";
        String ghj="";

        asd=myData.toString();

        int a = asd.length();



        if(asd.contains("q")){

            durumum=true;}

        else if (asd==ghj){

            durumum=false;}

        else if (asd==null){

            durumum=false;}
        else{

            durumum=false;
        }




        return durumum;
    }






    @Override
    public void onBackPressed() {
        if( View.VISIBLE==infRel.getVisibility()|| View.VISIBLE==repMenu.getVisibility()) {



            sure_menu.setVisibility(View.GONE);
            repMenu.setVisibility(View.GONE);
            infRel.setVisibility(View.GONE);


        }else if (View.VISIBLE==onayRel.getVisibility()  || View.VISIBLE==sure_menu.getVisibility()  || View.VISIBLE==aramenu2.getVisibility()) {
            {

                //nothing

            }

        }
        else {
            this.onDestroy();
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
    protected void onDestroy() {
        super.onDestroy();


        this.finish();


    }
}