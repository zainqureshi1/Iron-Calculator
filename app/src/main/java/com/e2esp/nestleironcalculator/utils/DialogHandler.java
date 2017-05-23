package com.e2esp.nestleironcalculator.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.SpannedString;
import android.view.View;
import android.widget.TextView;

import com.e2esp.nestleironcalculator.R;
import com.e2esp.nestleironcalculator.callbacks.OnDialogClickedListener;

/**
 * Created by Zain on 5/22/2017.
 */

public class DialogHandler {

    public static Dialog showReachedIntakeDialog(Context context, final OnDialogClickedListener onDialogClickedListener) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_reached_intake);

        //((TextView) dialog.findViewById(R.id.textViewResult)).setText(context.getText(R.string.result_satisfied));

        dialog.findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogClickedListener.onDialogButtonClick(dialog, 0);
            }
        });
        dialog.findViewById(R.id.buttonTryAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogClickedListener.onDialogButtonClick(dialog, 1);
            }
        });

        dialog.show();
        return dialog;
    }

    public static void showDefectDialog(Context context, SpannableString message, final OnDialogClickedListener onDialogClickedListener) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_defect);

        ((TextView) dialog.findViewById(R.id.textViewDefect)).setText(message, TextView.BufferType.SPANNABLE);

        dialog.findViewById(R.id.buttonProceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogClickedListener.onDialogButtonClick(dialog, 0);
            }
        });

        dialog.show();
    }

}
