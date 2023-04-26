package com.example.myloginapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    MaterialButton contactUs;
    LinearLayout sendEmailLayout , buttonLayout;
    Button sendEmail , detectUrlButton ,btnHistory, scanapp;
    EditText editTextTo,editTextSubject,editTextMessage;
    ListView installedAppsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactUs = findViewById(R.id.contact_us);
        editTextTo = findViewById(R.id.editText1);
        editTextSubject = findViewById(R.id.editText2);
        editTextMessage = findViewById(R.id.editText3);
        sendEmailLayout= findViewById(R.id.send_email_layout);
        buttonLayout = findViewById(R.id.button_layout);
        btnHistory = findViewById(R.id.btn_history);
        detectUrlButton = findViewById(R.id.detect_url);
        sendEmail = findViewById(R.id.send_email);
        scanapp = findViewById(R.id.scan_app);
        contactUs.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        sendEmail.setOnClickListener(this);
        detectUrlButton.setOnClickListener(this);
//        installedAppsListView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
           case  R.id.contact_us :
               sendEmailLayout.setVisibility(view.VISIBLE);
               buttonLayout.setVisibility(view.GONE);
               break;
            case R.id.btn_history:
                Intent in1 = new Intent(MainActivity.this,ListActivity.class);
                startActivity(in1);
                break;
            case R.id.send_email:
                sendEmail();
                break;
            case R.id.detect_url:
                 Intent in = new Intent(MainActivity.this, MainActivityWebview.class);
                 startActivity(in);
                 break;
            case R.id.scan_app:
                Intent intent = new Intent(MainActivity.this,ScanAppList.class);
                startActivity(intent);
                break;
        }

    }

    private void sendEmail() {
        String to=editTextTo.getText().toString();
        String subject=editTextSubject.getText().toString();
        String message=editTextMessage.getText().toString();

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }
}