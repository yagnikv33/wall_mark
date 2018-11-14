package com.wallmark;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        createFolder();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.search:
                Toast.makeText(this, "Search Select", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_refresh:
                Toast.makeText(this, "Refresh Select", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createFolder() {
        String myfolder = Environment.getExternalStorageDirectory() + "/Wall Mark";
        File f = new File(myfolder);
        if (!f.exists())
            if (!f.mkdir()) {
                Toast.makeText(this, myfolder + " can't be created.", Toast.LENGTH_SHORT).show();
            }
    }
}