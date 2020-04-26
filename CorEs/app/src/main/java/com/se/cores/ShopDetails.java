package com.se.cores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShopDetails extends AppCompatActivity implements FeedbackShopStatusDialog.FeedbackShopStatusListener,
                                                              FeedbackItemAvailableDialog.FeedbackItemDialogListener {

    FeedBack feedBack;
    FeedbackBuilder feedbackBuilder;
    String shopID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        DatabaseAdapter db = new DatabaseAdapter();

        Button feedbackButton = findViewById(R.id.feedbackButton);

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                feedbackBuilder = new FeedbackBuilder();

                DialogFragment itemFeedback = new FeedbackItemAvailableDialog();
                itemFeedback.show(getSupportFragmentManager(), "itemFeedback");

                DialogFragment statusFeedback = new FeedbackShopStatusDialog();
                statusFeedback.show(getSupportFragmentManager(), "statusFeedback");

                feedBack = feedbackBuilder.build();

                db.updateFeedback(feedBack, shopID);
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
}
