package com.drawcoders.conversor;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by whk on 27-04-15.
 */
public class FragmentFileToSHA1Sum extends FragmentGenericConvert {
    private EditText input;
    private EditText output;
    private TextView input_title;
    private CheckBox trim_input;
    private CheckBox output_option;
    private LinearLayout arguments_container;
    private TextView arguments_title;
    private EditText arguments_value;
    private Button chose;
    private ImageButton share;

    public final static Integer RESULT_CODE_FILE_CHOSE = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_generic_file, container, false);

        this.input = (EditText)rootView.findViewById(R.id.input);
        this.output = (EditText)rootView.findViewById(R.id.output);
        this.input_title = (TextView)rootView.findViewById(R.id.input_title);
        this.trim_input = (CheckBox)rootView.findViewById(R.id.trim_input);
        this.output_option = (CheckBox)rootView.findViewById(R.id.output_option);
        this.arguments_container = (LinearLayout)rootView.findViewById(R.id.arguments_container);
        this.arguments_title = (TextView)rootView.findViewById(R.id.arguments_title);
        this.arguments_value = (EditText)rootView.findViewById(R.id.arguments_value);
        this.chose = (Button)rootView.findViewById(R.id.chose);
        this.share = (ImageButton)rootView.findViewById(R.id.share);

        this.output_option.setVisibility(View.GONE);
        this.arguments_container.setVisibility(View.GONE);

        this.input.setTypeface(Typeface.MONOSPACE);

        this.output.setHint(getString(R.string.hint_output_file_to_sha1sum));
        this.output.setTypeface(Typeface.MONOSPACE);

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

        this.chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FileChoserActivity.class);
                startActivityForResult(intent, RESULT_CODE_FILE_CHOSE);
            }
        });

        this.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = output.getText().toString().trim();

                if(!value.isEmpty()){
                    String filename = input.getText().toString();
                    if(filename.contains("/"))
                        filename = filename.split("/")[filename.split("/").length - 1];

                    String subject = String.format(getString(R.string.share_subject_file_to_sha1sum), getString(R.string.app_name), filename);
                    String body = String.format(getString(R.string.share_value_file_to_sha1sum), filename, output.getText().toString().trim());

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
        this.setup(R.string.converted_file_to_sha1sum);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_CODE_FILE_CHOSE){

            String path = data.getStringExtra("path");

            this.input.setText("");

            if((path != null) && (!path.isEmpty()))
                this.input.setText(path);
        }
    }

    @Override
    public void convert(Boolean show_msg) {
        String in = this.input.getText().toString();

        this.output.setText(this.conv.checkSHA1Sum(in));

        super.convert(show_msg);
    }
}
