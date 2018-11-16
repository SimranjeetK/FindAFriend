package com.cs380.csun.findafriend;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EditProfileActivity extends AppCompatActivity {
    TextView tvName1;
    ImageView ImageViewButton;
    ArrayList<String> selectedItems=new ArrayList<>();
    ArrayList<String> selectedItems2=new ArrayList<>();
    DatabaseHelper myInterests;
    EditText nameText;
    ListView ch2,ch3;
    private DatabaseReference mDatabase;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ImageViewButton = (ImageView) findViewById(R.id.circleImageView);
        final List<String> items2 = new ArrayList<>();
      //final List<String> items3 = new ArrayList<String>();
      //firebase database creation and calls. currently only works sometimes.
      /*  mDatabase = FirebaseDatabase.getInstance().getReference().child("InterestList").child("Interests");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                items3.add(value);

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        final Button addButton = (Button) findViewById(R.id.bAdd);
        final Button saveButton = (Button) findViewById(R.id.bSave);
        final EditText myEditText = (EditText) findViewById(R.id.edAddInterest);
        final Intent home = new Intent(EditProfileActivity.this, HomeActivity.class);
        final MediaPlayer notifSound = MediaPlayer.create(this, R.raw.boop);

        //notifSound.start(); to use the sound

        tvName1 = (TextView) findViewById(R.id.tvName);
        ch2=(ListView) findViewById(R.id.checkable_list);
        ch3=(ListView) findViewById(R.id.checkable_list2);
        ch2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ch3.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        myInterests = new DatabaseHelper(this);
        readName();
        populateListView();
        final ImageButton SettingsButton = (ImageButton) findViewById(R.id.bSettings);

        items2.add("Naruto");
        items2.add("Comic Con");
        items2.add("Tiesto");
        items2.add("Dresses");
        items2.add("Korean BBQ");
        items2.add("Adidas");
        //would generate random items from firebase database
        //int idx = new Random().nextInt(items3.size());

        //  for (int g=0; g<6 ; g++)
        //  {
        //      items2.add(items3.get(idx));
        //  }

        final ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this, R.layout.rowlayout,R.id.txt_lan,items2);
        ch3.setAdapter(adapter2);

        FileInputStream fileIn = null;
        try{
            fileIn = openFileInput("profilePhoto");
            Bitmap photo = BitmapFactory.decodeStream(fileIn);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),photo);
            roundedBitmapDrawable.setCircular(true);
            ImageViewButton.setImageDrawable(roundedBitmapDrawable);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ch2.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItems.contains(selectedItem)) {
                    selectedItems.remove(selectedItem); //uncheck item

                }
                else {
                    selectedItems.add(selectedItem);

                }
            }
        });

        ch3.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem2 = ((TextView) view).getText().toString();
                if (selectedItems2.contains(selectedItem2)) {
                    selectedItems2.remove(selectedItem2); //uncheck item

                }
                else {
                    selectedItems2.add(selectedItem2);

                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                items2.add(0,myEditText.getText().toString());
                adapter2.notifyDataSetChanged();
                myEditText.setText("");
            }
        });

        SettingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this, SettingsActivity.class));
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                for (int i =0; i< ch2.getCount();i++){
                    if (!(ch2.isItemChecked(i)))
                    {
                        myInterests.deleteData(ch2.getItemAtPosition(i).toString());
                    }
                }
                for (int i =0; i< ch3.getCount();i++){
                    if (ch3.isItemChecked(i))
                    {
                        myInterests.insertData(ch3.getItemAtPosition(i).toString());
                    }
                }

                startActivity(home);
                finish();
            }
        });
    }

    public void readName()
    {
        FileInputStream fileInputStream = null;
        try {
            String name;
            fileInputStream = openFileInput("username");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while((name = bufferedReader.readLine())!= null)
                stringBuffer.append(name);
            tvName1.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void populateListView(){
        Cursor cursor = myInterests.getAllData();
        List<String> items1 = new ArrayList<String>();

        while (cursor.moveToNext()){
            items1.add(cursor.getString(1));
        }
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.rowlayout2,R.id.txt_lan,items1);
        ch2.setAdapter(adapter);
        for (int y = 0; y< ch2.getCount();y++)
        {
            ch2.setItemChecked(y,true);
        }
    }

}


