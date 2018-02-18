package com.drawcoders.conversor;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by whk on 26-04-15.
 */
public class FragmentAsciiToSha256 extends FragmentGenericConvert {
    private EditText input;
    private EditText output;
    private TextView input_title;
    private CheckBox trim_input;
    private CheckBox output_option;
    private LinearLayout arguments_container;
    private TextView arguments_title;
    private EditText arguments_value;
    private ImageButton share;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_generic_input, container, false);

        this.input = (EditText)rootView.findViewById(R.id.input);
        this.output = (EditText)rootView.findViewById(R.id.output);
        this.input_title = (TextView)rootView.findViewById(R.id.input_title);
        this.trim_input = (CheckBox)rootView.findViewById(R.id.trim_input);
        this.output_option = (CheckBox)rootView.findViewById(R.id.output_option);
        this.arguments_container = (LinearLayout)rootView.findViewById(R.id.arguments_container);
        this.arguments_title = (TextView)rootView.findViewById(R.id.arguments_title);
        this.arguments_value = (EditText)rootView.findViewById(R.id.arguments_value);
        this.share = (ImageButton)rootView.findViewById(R.id.share);

        this.output_option.setVisibility(View.GONE);
        this.arguments_container.setVisibility(View.GONE);

        this.input.setHint(getString(R.string.hint_input_ascii_to_sha256));
        this.input.setTypeface(Typeface.MONOSPACE);

        this.output.setHint(getString(R.string.hint_output_ascii_to_sha256));
        this.output.setTypeface(Typeface.MONOSPACE);

        input_title.setText(getString(R.string.input_title_ascii_to_sha256));

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

        this.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value_output = output.getText().toString().trim();
                String value_input = input.getText().toString();

                if(trim_input.isChecked())
                    value_input = value_input.trim();

                if(!value_output.isEmpty()){

                    String subject = String.format(getString(R.string.share_subject_ascii_to_sha256), getString(R.string.app_name));
                    String body = String.format(getString(R.string.share_value_ascii_to_sha256), value_input, value_output);

                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
                    startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_using)));

                }else{
                    Toast.makeText(getActivity(), getString(R.string.share_need_output_value), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configuraciones
        this.setup(R.string.converted_ascii_to_sha256);

        return rootView;
    }

    @Override
    public void convert(Boolean show_msg) {
        String in = this.input.getText().toString();

        if(this.trim_input.isChecked())
            in = in.trim();

        this.output.setText(this.conv.SHA256(in));

        super.convert(show_msg);
    }
}
