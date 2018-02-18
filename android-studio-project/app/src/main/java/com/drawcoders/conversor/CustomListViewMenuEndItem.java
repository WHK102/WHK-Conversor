package com.drawcoders.conversor;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by whk on 27-04-15.
 */
public class CustomListViewMenuEndItem implements CustomListViewMenuInterface {
    private View view;
    private String title;
    //private Drawable icon;

    public CustomListViewMenuEndItem(String title) {
        this.title = title;
        //this.icon = icon;
    }

    @Override
    public int getViewType() {
        return CustomListViewMenu.RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        if (convertView == null)
            this.view = inflater.inflate(R.layout.custom_listview_menu_end_item, null);
        else
            this.view = convertView;

        TextView title = (TextView) this.view.findViewById(R.id.title);
        title.setText(this.title);

        //ImageView icon = (ImageView) this.view.findViewById(R.id.icon);
        //icon.setImageDrawable(this.icon);

        return view;
    }
}
