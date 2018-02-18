package com.drawcoders.conversor;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by whk on 27-04-15.
 */
public interface CustomListViewMenuInterface {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
}
