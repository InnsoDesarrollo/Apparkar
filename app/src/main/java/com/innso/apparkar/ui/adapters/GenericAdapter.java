package com.innso.apparkar.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.innso.apparkar.ui.factories.GenericAdapterFactory;
import com.innso.apparkar.ui.interfaces.CategoryItem;
import com.innso.apparkar.ui.interfaces.GenericItem;
import com.innso.apparkar.ui.interfaces.GenericItemView;

import static com.innso.apparkar.ui.factories.GenericAdapterFactory.TYPE_CATEGORY;
import static com.innso.apparkar.ui.factories.GenericAdapterFactory.TYPE_FOOTER;
import static com.innso.apparkar.ui.factories.GenericAdapterFactory.TYPE_HEADER;

public class GenericAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String defaultCategory = "Otro";

    protected List<GenericItem> items;

    protected GenericAdapterFactory factory;

    private String other;

    public GenericAdapter(GenericAdapterFactory factory) {
        this.factory = factory;
        this.items = new ArrayList<>();
        this.other = defaultCategory;
    }

    public GenericAdapter(GenericAdapterFactory factory, String other) {
        this.factory = factory;
        this.items = new ArrayList<>();
        this.other = other;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = (View) this.factory.onCreateViewHolder(parent, viewType);

        return new RecyclerView.ViewHolder(view) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GenericItemView genericItemView = (GenericItemView) holder.itemView;
        genericItemView.bind(getItem(position).getData());
    }

    protected GenericItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setItems(List<? extends GenericItem> items) {

        this.items = new ArrayList<>();

        if (items != null) {

            for (int i = 0, size = items.size(); i < size; i++) {

                GenericItem genericItem = items.get(i);

                addNewItem(genericItem);
            }

        } else {
            this.items = new ArrayList<>();
        }

        notifyDataSetChanged();
    }

    private void addNewItem(GenericItem genericItem) {
        if (genericItem instanceof CategoryItem) {
            CategoryItem categoryItem = (CategoryItem) genericItem;
            addCategories(categoryItem);
        } else {
            this.items.add(genericItem);
        }
    }


    private void addCategories(CategoryItem item) {

        final String category = TextUtils.isEmpty(item.getData()) ? other : item.getData();

        int categoryIndex = indexCategoryOf(category);

        if (categoryIndex != -1) {

            this.items.add(categoryIndex + 1, item);

        } else {

            this.items.add(new CategoryItem(category));

            this.items.add(item);
        }
    }

    private int indexCategoryOf(String category) {
        for (int i = 0, size = items.size(); i < size; i++) {
            GenericItem itemBasket = getItem(i);
            if (itemBasket.getType() == GenericAdapterFactory.TYPE_CATEGORY) {
                CategoryItem categoryItem = (CategoryItem) itemBasket;
                if (categoryItem.getData().equals(category)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void updateCategories() {
        removeCategories();
        setItems(new ArrayList<>(items));
    }

    private void removeCategories() {
        Iterator<GenericItem> itemIterator = items.iterator();
        while (itemIterator.hasNext()) {
            GenericItem item = itemIterator.next();
            if (item.getType() == TYPE_CATEGORY) {
                itemIterator.remove();
            }
        }
    }

    public void update(GenericItem item) {
        int index = items.indexOf(item);
        if (index != -1) {
            items.set(index, item);
            notifyItemChanged(index);
        }
    }

    public void remove(GenericItem item) {
        int index = items.indexOf(item);
        if (index != -1) {
            items.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void addItem(GenericItem item) {
        addNewItem(item);
        notifyDataSetChanged();
    }

    public void addItems(List<? extends GenericItem> items) {
        if (items != null) {
            for (int i = 0, size = items.size(); i < size; i++) {
                addNewItem(items.get(i));
            }
            notifyDataSetChanged();
        }
    }

    public void addItems(int index, List<? extends GenericItem> itemsToAdd) {
        if (itemsToAdd == null) {
            itemsToAdd = new ArrayList<>();
        }
        items.addAll(index, itemsToAdd);
        notifyItemInserted(index);
    }

    public void addItem(int index, GenericItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(index, item);
        notifyItemInserted(index);
    }

    public void clearAll() {
        if (items != null) {
            items.clear();
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (items != null) {
            Iterator<GenericItem> i = items.iterator();
            while (i.hasNext()) {
                GenericItem itemView = i.next();
                if (itemView.getType() != TYPE_HEADER && itemView.getType() != TYPE_FOOTER) {
                    i.remove();
                }
            }
            notifyDataSetChanged();
        }
    }

    public int getItemPosition(GenericItem item) {
        int itemPosition = -1;
        if (items != null && !items.isEmpty()) {
            itemPosition = items.indexOf(item);
        }
        return itemPosition;
    }

    public GenericItem getItemAtPosition(int position) {
        GenericItem genericItem = null;
        if (items != null && items.size() > position) {
            genericItem = getItem(position);
        }
        return genericItem;
    }

}