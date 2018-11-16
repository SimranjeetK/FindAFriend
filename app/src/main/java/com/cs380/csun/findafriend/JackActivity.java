package com.cs380.csun.findafriend;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

//temporary profile to illustrate matched bluetooth profile
//with generic data

public class JackActivity extends AppCompatActivity {

    ImageView circleButton;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jack);
        final ImageButton SettingsButton = (ImageButton) findViewById(R.id.bSettings);
        circleButton  = (ImageView) findViewById(R.id.circleImageView);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.jack);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap1);
        roundedBitmapDrawable.setCircular(true);
        circleButton.setImageDrawable(roundedBitmapDrawable);
        populatelistview();

        SettingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JackActivity.this, SettingsActivity.class));
            }
        });

    }

    private void populatelistview() {
        String[] myItems = {"Lakers", "Waifu", "POGO"};
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.da_items,myItems);
        lv = (ListView) findViewById(R.id.matches);
        lv.setAdapter(adapter);
    }



}
