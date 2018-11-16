package com.cs380.csun.findafriend;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InitEditProfileActivity extends AppCompatActivity {

    ArrayList<String> selectedItems=new ArrayList<>();
    EditText nameText;
    ImageView addPhotoButton;
    ListView ch1;
    int checked = 0;
    DatabaseHelper myInterests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_edit_profile);

        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final Intent home = new Intent(InitEditProfileActivity.this, HomeActivity.class);

        if(settings.contains("profileCreated")){
            startActivity(home);
            finish();
        }
        ch1=(ListView) findViewById(R.id.checkable_list);
        final EditText myEditText = (EditText) findViewById(R.id.edAddInterest);
        nameText = (EditText) findViewById(R.id.etName);
        final Button addButton = (Button) findViewById(R.id.bAdd);
        final Button doneButton = (Button) findViewById(R.id.bDone);
        addPhotoButton  = (ImageView) findViewById(R.id.bAddPhoto);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.placeholder);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap1);
        roundedBitmapDrawable.setCircular(true);
        addPhotoButton.setImageDrawable(roundedBitmapDrawable);

        ch1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        myInterests = new DatabaseHelper(this);

        final List<String> items = new ArrayList<String>();
        items.add("La La Land");
        items.add("Lakers");
        items.add("Waifu");
        items.add("POGO");
        items.add("Skydiving");
        items.add("Cycling");

        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.rowlayout,R.id.txt_lan,items);
        ch1.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                items.add(0,myEditText.getText().toString());
                adapter.notifyDataSetChanged();
                myEditText.setText("");
            }
        });

        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 0);
            }
        });

        //Switches activity if 5 or more interests are selected AND the name field is not empty
        //Currently does not finsh the activity as data is currently not stored
        doneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (ch1.getCheckedItemCount() > 4 && ch1.getCheckedItemCount() < 100 && !nameText.getText().toString().equals("")) {

                    settings.edit().putBoolean("profileCreated", true).commit();
                    writeName(view);
                    for (int i =0; i< ch1.getCount();i++){
                        if (ch1.isItemChecked(i))
                        {
                            myInterests.insertData(ch1.getItemAtPosition(i).toString());
                        }
                    }

                    startActivity(home);
                    finish();
                }
                //else
                //Error message?
            }
        });


        ch1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItems.contains(selectedItem)) {
                    selectedItems.remove(selectedItem); //uncheck item
                    checked = 0;
                }
                else {
                    selectedItems.add(selectedItem);
                    checked = 1;
                }
            }
            });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data") ;
        RoundedBitmapDrawable roundedBitmapDrawable2 = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable2.setCircular(true);
        addPhotoButton.setImageDrawable(roundedBitmapDrawable2);

        String fileName = "profilePhoto";
        FileOutputStream fileOut = null;
        try{
            fileOut = openFileOutput(fileName, MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally{
            try{
                if(fileOut != null){
                    fileOut.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeName(View view) {

        String name = nameText.getText().toString();
        String file_name = "username";

        try {
            FileOutputStream fileOutputStream = openFileOutput(file_name ,MODE_PRIVATE);
            fileOutputStream.write(name.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}