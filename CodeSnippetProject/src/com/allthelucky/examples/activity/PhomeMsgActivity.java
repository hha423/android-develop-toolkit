package com.allthelucky.examples.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

public class PhomeMsgActivity extends Activity {

    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button = new Button(this);
        setContentView(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();//sms();
            }
        });
        
    }
    
    protected void call() {
        String mobile = "18672950256";
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));// Intent.ACTION_DIAL
        startActivity(intent);
    }
    
    protected void sms() {
        SmsManager smsManager = SmsManager.getDefault();
        String text="hello,world";
        String mobile = "18672950256";
        ArrayList<String> texts = smsManager.divideMessage(text);
        for(String t:texts) {
            smsManager.sendTextMessage(mobile, null, t, null, null);
        }    
    }
   
}
