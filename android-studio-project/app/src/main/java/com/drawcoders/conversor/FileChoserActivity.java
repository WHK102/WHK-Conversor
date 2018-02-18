package com.drawcoders.conversor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class FileChoserActivity extends ActionBarActivity {

    private String final_path = "/";
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_choser);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getTitle());

        this.list = (ListView)findViewById(R.id.list);
        this.list.setClickable(true);
        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int itemId, long arg3){
                try{
                    Object item = list.getItemAtPosition(itemId);
                    String navigate;

                    File f = new File(final_path + "/" + item.toString());

                    if(f.exists() && f.isDirectory()){
                        // Is dir

                        if(item.toString().contentEquals("..") && final_path.contentEquals("/")){
                            navigate = "/";
                        }else if(item.toString().contentEquals("..")){
                            String[] splits = final_path.split("/");
                            splits[splits.length - 1] = "";
                            navigate = "/"; /* root */
                            for(String spl : splits){
                                if(spl.length() > 0) /* Prevent null items */
                                    navigate += spl + "/";
                            }
                            showDir(navigate);
                        }else{
                            showDir(final_path + item.toString());
                        }

                    }else{
                        // Is file
                        final_path = final_path + item.toString();

                        Intent intent = new Intent();
                        intent.putExtra("path", final_path);
                        setResult(FragmentFileToMD5Sum.RESULT_CODE_FILE_CHOSE, intent);

                        finish();
                    }
                }catch(Exception e){
                    Toast.makeText(FileChoserActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        this.showDir("/");
    }

    private void showDir(String dirPath){
        try{
            ArrayList<String> itemArray;
            itemArray = new ArrayList<>();
            ArrayAdapter<String> itemAdapter;
            itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemArray);
            list.setAdapter(itemAdapter);

	        /* Add back directory */
            itemArray.add("..");

            File f = new File(dirPath);
            File[] files = f.listFiles();

			// Can read root directory?
            if((dirPath.contentEquals("/")) && (!f.canRead())){
				// Change to SD directory
                dirPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            }

			// Select path
            final_path = dirPath;

			// Order by name
            Comparator<? super File> filecomparator = new Comparator<File>(){
                public int compare(File file1, File file2) {
                    return String.valueOf(file1.getName()).compareTo(file2.getName());
                }
            };
            Arrays.sort(files, filecomparator);

			// Empty directory?
            if(files.length > 0){

				// Order by type

				// Add directories
                for(File file : files){
                    if(!file.isHidden() && file.canRead()){
                        if(file.isDirectory())
                            itemArray.add(file.getName() + "/");
                    }
                }

				// Add files
                for(File file : files){
                    if(!file.isHidden() && file.canRead()){
                        if(!file.isDirectory())
                            itemArray.add(file.getName());
                    }
                }
            }

            itemAdapter.notifyDataSetChanged();
        }catch(Exception e){
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);

                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);

                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
