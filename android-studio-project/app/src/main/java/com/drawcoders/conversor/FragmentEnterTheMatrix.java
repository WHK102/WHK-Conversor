package com.drawcoders.conversor;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by whk on 26-04-15.
 */
public class FragmentEnterTheMatrix extends FragmentGenericConvert {
    private EditText input;
    private WebView output;
    private TextView input_title;
    private CheckBox trim_input;
    private CheckBox output_option;
    private Handler handler = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enter_the_matrix, container, false);

        this.input = (EditText)rootView.findViewById(R.id.input);
        this.output = (WebView)rootView.findViewById(R.id.output);
        this.input_title = (TextView)rootView.findViewById(R.id.input_title);
        this.trim_input = (CheckBox)rootView.findViewById(R.id.trim_input);
        this.output_option = (CheckBox)rootView.findViewById(R.id.output_option);

        this.output.getSettings().setJavaScriptEnabled(true);
        this.output.setWebChromeClient(new WebChromeClient());
        this.output.loadUrl("about:blank");

        this.output_option.setVisibility(View.GONE);

        this.input.setHint(getString(R.string.hint_input_ascii_to_enter_the_matrix));
        this.input.setTypeface(Typeface.MONOSPACE);

        input_title.setText(getString(R.string.input_title_ascii_to_enter_the_matrix));

        // Live calculator
        /* this.input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                convert(false);
            }
        }); */

        // Configuraciones
        this.setup(R.string.converted_ascii_to_enter_the_matrix);

        return rootView;
    }

    @Override
    public void convert(Boolean show_msg) {
        String in = this.input.getText().toString();

        if(this.trim_input.isChecked())
            in = in.trim();

        this.output.loadData(conv.matrixCode(in), "text/html", "utf-8");

        // Stop
        if(handler != null)
            handler.removeCallbacksAndMessages(null);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                output.loadUrl("about:blank");
            }
        }, 8000);

        super.convert(show_msg);
    }
}
