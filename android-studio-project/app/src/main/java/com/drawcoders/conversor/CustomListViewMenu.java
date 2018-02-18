package com.drawcoders.conversor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by whk on 27-04-15.
 */
public class CustomListViewMenu extends ArrayAdapter<CustomListViewMenuInterface> {
    private LayoutInflater mInflater;

    public enum RowType {
        LIST_ITEM, HEADER_ITEM
    }

    public CustomListViewMenu(Context context, List<CustomListViewMenuInterface> items) {
        super(context, 0, items);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return RowType.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(mInflater, convertView);
    }
}
