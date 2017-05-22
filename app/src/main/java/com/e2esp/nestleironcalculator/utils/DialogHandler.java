package com.e2esp.nestleironcalculator.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.SpannedString;
import android.view.View;
import android.widget.TextView;

import com.e2esp.nestleironcalculator.R;

/**
 * Created by Zain on 5/22/2017.
 */

public class DialogHandler {

    public static void showReachedIntakeDialog(Context context, View.OnClickListener onCalculateClickListener, View.OnClickListener onTryAgainClickListener) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_reached_intake);

        //((TextView) dialog.findViewById(R.id.textViewResult)).setText(context.getText(R.string.result_satisfied));

        dialog.findViewById(R.id.buttonCalculate).setOnClickListener(onCalculateClickListener);
        dialog.findViewById(R.id.buttonTryAgain).setOnClickListener(onTryAgainClickListener);

        dialog.show();
    }

    public static void showDefectDialog(Context context, SpannedString message, View.OnClickListener onProceedClickListener) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_defect);

        ((TextView) dialog.findViewById(R.id.textViewDefect)).setText(message);

        dialog.findViewById(R.id.buttonProceed).setOnClickListener(onProceedClickListener);

        dialog.show();
    }

}
