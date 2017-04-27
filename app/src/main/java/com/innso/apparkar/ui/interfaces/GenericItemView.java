package com.innso.apparkar.ui.interfaces;

public interface GenericItemView<T> {

    void bind(T item);

    T getData();

    void setSelected(boolean isSelected);

}
