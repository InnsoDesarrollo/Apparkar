package com.innso.apparkar.ui.binding;


import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;

public class ParkingBinding {

    private static final String BY_MINUTE = "by_minute";

    @BindingAdapter({"parkingCount"})
    public static void setCount(TextView textView, int value) {
        if (value != -1) {
            textView.setText(" : " + value);
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

}
