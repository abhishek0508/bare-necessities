package com.se.cores;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.ArrayList;


public class ShopUpdate extends AppCompatActivity {

    private Switch Activity_ShopStatus;
    private Button aItemList;
    private AutoCompleteTextView Input_AvailableItems;
    private AutoCompleteTextView Input_UnavailableItems;
    private Button Button_AddItem;
    private Button Button_RemoveItem;
    private EditText Input_OpenTimeUpdate;
    private EditText Input_CloseTimeUpdate;
    private String[] ListItems;
    private String[] aUnavailableItems;
    boolean[] checkedItems;
    ArrayList<Integer> AvailableItems = new ArrayList<Integer>(); // supposed to track checked items but seems to be useless
    final ArrayList<Integer> markedUnavailable = new ArrayList<Integer>();// where we track unchecked items


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_update);

// First intent: the switch that takes shop status.
        Activity_ShopStatus = findViewById(R.id.switch_ShopStatus);
        Boolean ShopStatus = Activity_ShopStatus.isChecked(); // gets shopstatus: open(1) or closed(0)


// Checkbox Dialog Function clumsily put in main instead of a separate method/class because coding illeterate here :'( I tried. Was big mess.
        aItemList = findViewById(R.id.ItemListButton); // is action button for LIST ITEM button
        ListItems = getResources().getStringArray(R.array.Essentials); // is string storing list of Essentials

        aItemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ListBuilder = new AlertDialog.Builder(ShopUpdate.this);
                ListBuilder.setMultiChoiceItems(ListItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        aUnavailableItems = getResources().getStringArray(R.array.UnavailableItems);
                        if (!isChecked) {
                            if (!markedUnavailable.contains(position)) {
                                markedUnavailable.add(position);
                            } else {
                                markedUnavailable.remove(position);
                            }
                        }
                    }
                });
                ListBuilder.setCancelable(false);
                ListBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i = 0; i < markedUnavailable.size(); i++) {
                            item = ListItems[markedUnavailable.get(i)]; // getting the item name from ListItems
                            aUnavailableItems[i] = item; // aUnavailableItems should be reinitialized once sent to db. Also, don't think Unavailable needs to be stored in res.
                        }
                        // reinitializing markedUnavailable
                        for (int i = 0; i < markedUnavailable.size(); i++) {
                            markedUnavailable.set(i, 0);
                        }

                    }
                });
            }
        });

 // gets list of available items manually entered by shopkeeper. Have to add to DB or remark available.

        Button_AddItem = findViewById(R.id.AddItemButton);
        Button_AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Input_AvailableItems = findViewById(R.id.AvailableItems);
                String EnteredAvailableItem = Input_AvailableItems.getText().toString();
                // Entered Item should be passed to Database Adapter and checked (marked true). If new then should be put in Essential list and added to available items
            }
        });


// gets items not currently available, have to uncheck this in DB. Can't allow unavailable items to be items not already there in shop
        Button_RemoveItem = (findViewById(R.id.DeleteItemButton));
        Button_RemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Input_UnavailableItems = findViewById(R.id.OutOfStock);
                String OutofStockItems = Input_UnavailableItems.getText().toString();
            }
        });

// Shop Time details updation
        Input_OpenTimeUpdate = findViewById(R.id.UpdatedOpenTime);
//        Time Data_OpenTimeUpdate = ((Time) Input_OpenTimeUpdate.getText());

        Input_CloseTimeUpdate = findViewById(R.id.UpdatedTIme_Close);
//        Time Data_CloseTimeUpdate = ((Time) Input_CloseTimeUpdate.getText());


       /* // Database Adapter for Screen 7
        DatabaseAdapter DatabaseAdapter_ShopUpdationPage = new DatabaseAdapter();
        DatabaseAdapter_ShopUpdationPage(int input, int AdaptedInput) {

        }*/


/*
        //supposedly back button
        getSupportActionBar().setTitle("Update Shop Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        public void onBackPressed(View v) {
          finish();
        }
*/
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, )
//                    .commitNow();
//        }
    }
}
