package com.se.cores;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class FeedbackItemAvailableDialog extends AppCompatDialogFragment {

    public interface FeedbackItemDialogListener {
        public void onItemDialogPositiveClick(DialogFragment dialog);
        public void onItemDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    FeedbackItemDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (FeedbackItemDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("Must implement FeedbackItemDialogListener");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage(R.string.itemAvailable)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onItemDialogPositiveClick(FeedbackItemAvailableDialog.this);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        listener.onItemDialogNegativeClick(FeedbackItemAvailableDialog.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
