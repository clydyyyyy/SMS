package com.example.myapp_sms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //initialize variable
    EditText phoneNum,phoneMessage;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //assign var

        phoneNum = findViewById(R.id.phone_Num);
        phoneMessage=findViewById(R.id.phone_Message);
        btnSend=findViewById(R.id.btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check condition

                if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED){
                    //WHEN PERMISSION GRANTED
                    //METHOD

                    sendMessage();
                }else{
                    //when permission is not granted
                    //Request permission
                    ActivityCompat.requestPermissions(MainActivity.this
                            ,new String[]{Manifest.permission.SEND_SMS},100);
                }
            }
        });
    }

    private void sendMessage() {
        //get values from edit text
        String sPhone = phoneNum.getText().toString().trim();
        String sMessage=  phoneMessage.getText().toString().trim();

        //check condition
        if (!sPhone.equals("") && !sMessage.equals("")){
            //when both edit text value not equal to blank

            //initialize sms manager
            SmsManager smsManager = SmsManager.getDefault();
            //send text message
            smsManager.sendTextMessage(sPhone, null, sMessage, null, null);

            //display toast
            Toast.makeText(getApplicationContext()
                    , "SMS sent Successfully!",Toast.LENGTH_LONG).show();
        }else{
            //edit text is blank
            //Display Text
            Toast.makeText(getApplicationContext()
                    ,"Enter value first.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0]
        == PackageManager.PERMISSION_GRANTED){
            //when permission is granted
            //call method
            sendMessage();
        }else{
            //when permission is denied
            Toast.makeText(getApplicationContext()
                    ,"Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}