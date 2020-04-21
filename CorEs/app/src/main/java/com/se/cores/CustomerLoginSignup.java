package com.se.cores;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;

public class CustomerLoginSignup extends AppCompatActivity {
    private Button button2;
    private Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_signup);

        button4 = findViewById(R.id.back);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainPage();
            }
        });

        button2 = findViewById(R.id.customerotp);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                openCustomerOtpPage();
            }
        });
    }

    public void openMainPage() {
        Intent intent = new Intent(this,CustomerRetailer.class);
        startActivity(intent);
    }

    public void openCustomerOtpPage(){
        Intent intent = new Intent(this,CustomerOtp.class);
        startActivity(intent);
    }
}
