package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.List;


public class ShopDetails extends AppCompatActivity implements FeedbackShopStatusDialog.FeedbackShopStatusListener,
                                                              FeedbackItemAvailableDialog.FeedbackItemDialogListener,
                                                              LoginForFeedbackDialog.LoginForFeedbackListener {

    FeedBack feedBack;
    FeedbackBuilder feedbackBuilder;
    String shopID;
    private Shop shop;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        DatabaseAdapter db = new DatabaseAdapter();

        Intent intent = getIntent();
        shop = (Shop)intent.getSerializableExtra("SHOP");

        Button feedbackButton = findViewById(R.id.feedbackButton);

        SharedPreferences sp = getSharedPreferences("CoresPref", MODE_PRIVATE);

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sp.getBoolean("loggedIn", false) == true) {
                    feedbackBuilder = new FeedbackBuilder().setShopID("");

                    DialogFragment itemFeedback = new FeedbackItemAvailableDialog();
                    itemFeedback.show(getSupportFragmentManager(), "itemFeedback");

                    DialogFragment statusFeedback = new FeedbackShopStatusDialog();
                    statusFeedback.show(getSupportFragmentManager(), "statusFeedback");

                    feedBack = feedbackBuilder.build();

                    db.updateFeedback(feedBack);
                }
                else {
                    DialogFragment notLoggedIn = new LoginForFeedbackDialog();
                    notLoggedIn.show(getSupportFragmentManager(), "notLoggedInForFeedback");
                }
            }
        });
    }

    @Override
    public void onItemDialogPositiveClick(DialogFragment dialog) {
        feedbackBuilder.setItemAvailability(true);
    }

    @Override
    public void onItemDialogNegativeClick(DialogFragment dialog) {
        feedbackBuilder.setItemAvailability(false);
    }

    @Override
    public void onStatusDialogPositiveClick(DialogFragment dialog) {
        feedbackBuilder.setTrueStatus(true);
    }

    @Override
    public void onStatusDialogNegativeClick(DialogFragment dialog) {
        feedbackBuilder.setTrueStatus(false);
    }

    @Override
    public void onLoginFeedbackDialogOKClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}

