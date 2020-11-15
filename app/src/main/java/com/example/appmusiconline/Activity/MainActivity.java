package com.example.appmusiconline.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.appmusiconline.Fragment.Fragment_getstart;
import com.example.appmusiconline.Fragment.Fragment_getstart_1;
import com.example.appmusiconline.Fragment.Fragment_getstart_2;
import com.example.appmusiconline.R;
import com.example.appmusiconline.SildePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager pager ;
    private PagerAdapter pagerAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pager = findViewById(R.id.viewPagerGetStart);
        pagerAdapter = new SildePagerAdapter(getSupportFragmentManager() ) ;
        pager.setAdapter(pagerAdapter);
      //  startActivity(new Intent(MainActivity.this , TrangchuActivity.class));
    }
}
