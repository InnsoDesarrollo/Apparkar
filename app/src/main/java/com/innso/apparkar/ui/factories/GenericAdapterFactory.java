package com.innso.apparkar.ui.factories;

import android.view.ViewGroup;

import com.innso.apparkar.ui.interfaces.GenericItemView;

public abstract class GenericAdapterFactory {

    public static final int TYPE_HEADER = 1001;
    public static final int TYPE_ITEM = 1002;
    public static final int TYPE_FOOTER = 1003;
    public static final int TYPE_CATEGORY = 1004;

    public abstract GenericItemView onCreateViewHolder(ViewGroup parent, int viewType);

}