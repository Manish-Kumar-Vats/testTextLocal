package com.example.testtwo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnSendOtp,btnLogin;
    EditText txtPhone,txtOtp;
    TextView temp;
    int randomNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtOtp=findViewById(R.id.otpText);
        txtPhone=findViewById(R.id.phoneText);
        temp=findViewById(R.id.temp);
        btnSendOtp=findViewById(R.id.sendOtpBtn);
        btnLogin=findViewById(R.id.LoginBtn);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Construct data
                    String apiKey = "apikey=" + "poxKbiEfKGM-BAataDvHveM12kaCLK9rxKw7ra4O2M";
                    Random random=new Random();
                    randomNumber=random.nextInt(9999);
                    String message = "&message=" + "Hey "+" your OTP is "+randomNumber;
                    String sender = "&sender=" + "Manish Vats";
                    String numbers = "&numbers=" + "+91-9891708986";

                    // Send data
                    HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
                    String data = apiKey + numbers + message + sender;
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                    conn.getOutputStream().write(data.getBytes("UTF-8"));
                    final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    final StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                    rd.close();
                    Toast.makeText(getApplicationContext(),"OTP sent!!",Toast.LENGTH_SHORT).show();
                    temp.setText("s"+randomNumber);
                   // return stringBuffer.toString();
                } catch (Exception e) {
                  //  System.out.println("Error SMS " + e);
                 //   return "Error " + e;
                    Toast.makeText(getApplicationContext(),"Error occured!!"+e,Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"SMS not sent!",Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (randomNumber==Integer.valueOf(txtOtp.getText().toString())){
                    Toast.makeText(getApplicationContext(),"You are logged in succesfully",Toast.LENGTH_LONG).show();
            }else{

                    Toast.makeText(getApplicationContext(),"wrong OTP",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
