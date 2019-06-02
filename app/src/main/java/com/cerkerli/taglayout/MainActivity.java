package com.cerkerli.taglayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cerkerli.library.utils.Util;
import com.cerkerli.library.view.TagLayout;
import com.cerkerli.library.view.TagLayoutClickListener;

public class MainActivity extends AppCompatActivity {

    private TagLayout tagLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tagLayout = findViewById(R.id.tag_layout);
        tagLayout.setOnClickListener(new TagLayoutClickListener() {
            @Override
            public void onClick(int id, String text, boolean isSelected) {
                switch (id){
                    case R.id.material_design:

                        Util.toast(text+" " + isSelected);

                        break;

                    case R.id.rx_java:

                        Util.toast(text+" " + isSelected);

                        break;
                    case R.id.gradle:

                        Util.toast(text+" " + isSelected);


                        break;
                    case R.id.android_studio:

                        Util.toast(text+" " + isSelected);


                        break;
                    case R.id.kotlin:

                        Util.toast(text+" " + isSelected);


                        break;
                    case R.id.flutter:

                        Util.toast(text+" " + isSelected);


                        break;




                }
            }
        });

    }
}
