package hzlabs.superconn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Huzeyfe on 24.5.2015.
 */
public class ReceiverOff  extends BroadcastReceiver {


    File StatusFile;
    private String fileStatus = "son1bc41a.txt";
    private String filename2 = "qs_durum2bc41a.txt";
    private String filepath = "FileStorage";
    File QsSFile;

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            ContextWrapper contextWrapper = new ContextWrapper(context);
            File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);

            StatusFile = new File(directory, fileStatus);
            QsSFile = new File(directory , filename2);
            bilgi_durum_yaz("");
            bilgi_durum_yaz_s("");

    }

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

    public void bilgi_durum_yaz_s(String durum) {

        try {
            FileOutputStream fos = new FileOutputStream(QsSFile);
            fos.write(durum.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
