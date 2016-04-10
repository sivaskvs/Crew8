package com.myapplication.siva.cubbalance;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import java.util.ArrayList;

public class SmsActivity extends Activity  {

    private static SmsActivity inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    private static String TAG="com.myapplication.siva.cubbalance";

    public static SmsActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        refreshSmsInbox();
    }

    public void refreshSmsInbox() {
        int i=0;
        TextView Balance = (TextView) findViewById(R.id.Balance);
        TextView abcd = (TextView) findViewById(R.id.abcd);
        Log.i(TAG, "Inside on refreshSMSInbox");
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query
                (Uri.parse("content://sms/inbox"),
                        new String[] { "_id","address","body","person","date"},
                        null,
                        null,
                        null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        SimpleDateFormat formatter  = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss",Locale.getDefault());
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss",Locale.getDefault());
        CubDb dbHandler = new CubDb(this, null,null, 1);
        Log.i(TAG, "Inside on refreshSMSInbox 2");

        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        Balance.setText("");
        abcd.setText("");
        do {
            if(smsInboxCursor.getString(indexAddress).endsWith("-CUBANK"))
            {
                String date = smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date")); //returns date( when was the message received )
                date = formatter.format(new Date(Long.parseLong(date)));
                Date sDate = new Date();
                try{
                    sDate = formatter.parse(date);
                }catch (Exception e){}
                String bal = smsInboxCursor.getString(indexBody);
                String bal2;
                int chk;
                if (bal.matches("(.*)Avl Bal(.*)")) {

                    chk = bal.indexOf("Avl Bal");
                    bal2 = bal.substring(chk + 8, bal.length());
                    String str,str2;
                    if(dbHandler.addDate(sDate,bal2)) {
                        str = "CURRENT BALANCE: " + bal2;
                        str2= date + "\n";
                        Balance.setText(str);
                        abcd.setText(str2);

                        i++;
                    }
                    else{

                        String str1 = dbHandler.giveBal();
                        String str3 = dbHandler.giveDate();
                        str = "CURRENT BALANCE: " + str1 ;
                        Balance.setText(str);
                        abcd.setText(str3);
                        i++;
                    }
                }
            }
        }while (smsInboxCursor.moveToNext() && i!=1);
    }
}

