package com.innso.apparkar.ui.views;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.innso.apparkar.R;
import com.innso.apparkar.databinding.ViewMainHeaderBinding;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;

public class ViewMainHeader extends FrameLayout implements TextWatcher {

    private ViewMainHeaderBinding binding;

    private BehaviorSubject<String> searchQuery = BehaviorSubject.create();

    private Handler handler = new Handler();

    private Runnable runnable = () -> searchQuery.onNext(binding.editTextParkingZone.getText().toString());

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
        binding.editTextParkingZone.addTextChangedListener(this);
        binding.editTextParkingZone.clearFocus();
    }

    public Observable<String> onQueryChange() {
        return searchQuery.observeOn(AndroidSchedulers.mainThread());
    }

    public void searchMode() {
        binding.progressBar.setVisibility(VISIBLE);
        binding.imageViewSearch.setVisibility(INVISIBLE);
    }

    public void normalMode() {
        binding.progressBar.setVisibility(INVISIBLE);
        binding.imageViewSearch.setVisibility(VISIBLE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        handler.removeCallbacks(runnable);
    }

    @Override
    public void afterTextChanged(Editable s) {
        handler.postDelayed(runnable, 1500);
    }
}
