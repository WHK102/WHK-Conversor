package com.drawcoders.conversor;

import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by whk on 26-04-15.
 */
public class FragmentGenericConvert extends Fragment{
    public Conv conv;
    public String msg_converted;

    public void setup(Integer resource_msg_converted) {
        this.conv = new Conv();
        this.msg_converted = getString(resource_msg_converted);
    }

    public void convert(Boolean show_msg) {
        if(show_msg) {

            // Hide keyboard
            try{
                InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }catch(Exception ignored){}

            // Show msg
            Toast.makeText(getActivity(), msg_converted, Toast.LENGTH_SHORT).show();
        }
    }
}
