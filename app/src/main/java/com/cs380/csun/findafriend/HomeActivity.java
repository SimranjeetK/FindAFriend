package com.cs380.csun.findafriend;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeActivity extends AppCompatActivity {
    TextView tvName1;
    ImageView ImageViewButton;
    ListView lv;
    String[] names={"FakeProfile69","Seal"};
    String[] matched={"3","2"};
    int[] images={R.drawable.jack,R.drawable.seal};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lv=(ListView) findViewById(R.id.matches);

        MatchAdapter adapter1 = new MatchAdapter(this,names,matched,images);
        lv.setAdapter(adapter1);



        ImageViewButton = (ImageView) findViewById(R.id.circleImageView);
        tvName1 = (TextView) findViewById(R.id.tvName);
        readName();
        final ImageButton SettingsButton = (ImageButton) findViewById(R.id.bSettings);
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


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              if (position==0)
              {
                  startActivity(new Intent(HomeActivity.this, JackActivity.class));
              }
                if (position==1)
                {
                    startActivity(new Intent(HomeActivity.this, SealActivity.class));
                }
            }
        });




        ImageViewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, EditProfileActivity.class));
            }
        });

        SettingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
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
}
