package com.myapplication.siva.cubbalance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;


public class SMSReceiver extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";
    private static String TAG="com.myapplication.siva.receivebroadcast";


    public void onReceive(Context context, Intent intent) {

        Bundle intentExtras = intent.getExtras();
        Log.i(TAG,"---BF8 Studios---");
        try {
            Toast.makeText(context, "---BF8 Studios---", Toast.LENGTH_LONG).show();
            Log.i(TAG,"Going inside");
            if (intentExtras != null) {
                Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
                String smsMessageStr = "";
                for (int i = 0; i < sms.length; ++i) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                    String smsBody = smsMessage.getMessageBody().toString();
                    String address = smsMessage.getOriginatingAddress();

                    smsMessageStr += "SMS From: " + address + "\n";
                    smsMessageStr += smsBody + "\n";
                    Toast.makeText(context, smsMessageStr, Toast.LENGTH_LONG).show();


                }

                //SmsActivity inst = SmsActivity.instance();
                //inst.updateList(smsMessageStr);
            }
        }catch (Exception e)
        {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }
    }
}
