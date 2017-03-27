package com.innso.apparkar.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.innso.apparkar.R;
import com.innso.apparkar.databinding.ViewMainHeaderBinding;

public class ViewMainHeader extends FrameLayout {

    private ViewMainHeaderBinding binding;

    public ViewMainHeader(Context context) {
        super(context);
        init();
    }

    public ViewMainHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewMainHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = DataBindingUtil.inflate(inflater, R.layout.view_main_header, this, true);
    }
}
