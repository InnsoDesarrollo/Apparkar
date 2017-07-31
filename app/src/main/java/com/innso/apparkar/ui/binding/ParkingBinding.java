package com.innso.apparkar.ui.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.innso.apparkar.ui.viewModels.ParkingViewModel;

import static com.innso.apparkar.ui.viewModels.ParkingViewModel.BY_MINUTE;

public class ParkingBinding {

    @BindingAdapter({"parkingValue"})
    public static void setParkingValue(TextView textView, String value) {
        if (!TextUtils.isEmpty(value) && !value.equals("-1")) {
            textView.setText(" : " + value);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"parkingCount"})
    public static void setCount(TextView textView, int count) {
        if (count != -1) {
            textView.setText(" : " + count);
        } else {
            textView.setVisibility(View.INVISIBLE);
        }
    }

    @BindingAdapter({"chargeFor"})
    public static void setCharge(TextView textView, String charge) {
        if (charge == null) {
            textView.setText("Parqueadero o Bahia gratuita");
        } else {
            if (charge.equals(BY_MINUTE)) {
                textView.setText("Cobro por: minutos");
            }
        }
    }

    @BindingAdapter({"paking_spinner_adapter"})
    public static void addSpinnerAdapter(AppCompatSpinner spinner, ParkingViewModel parkingViewModel) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parkingViewModel.setChargeType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
