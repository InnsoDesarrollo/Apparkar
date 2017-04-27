package com.innso.apparkar.ui.interfaces;

import com.innso.apparkar.ui.factories.GenericAdapterFactory;

public class CategoryItem implements GenericItem<String> {

    public String category;

    public CategoryItem(String category) {
        this.category = category;
    }

    @Override
    public String getData() {
        return category;
    }

    @Override
    public int getType() {
        return GenericAdapterFactory.TYPE_CATEGORY;
    }

}
