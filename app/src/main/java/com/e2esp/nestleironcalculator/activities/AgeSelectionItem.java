package com.e2esp.nestleironcalculator.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;

import com.e2esp.nestleironcalculator.R;

/**
 * Created by farooq on 17-May-17.
 */

public class AgeSelectionItem extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_item);

        RadioButton rbtnSelectAge = (RadioButton) findViewById(R.id.rbtnSelectAge);

        rbtnSelectAge.setButtonDrawable(R.drawable.radio_sheet1);

        /*
        TextView EventId = (TextView) findViewById(R.id.eventId);
        Intent intent = new Intent(this, NewEventActivity.class);

        intent.putExtra("eventId", EventId.getText());


        startActivity(intent);
*/

    }

}


