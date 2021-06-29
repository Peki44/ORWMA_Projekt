package com.example.virtualcloset;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    public static SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteHelper = new SQLiteHelper(this, "ClosetDB.sqLite", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS CLOSET (Id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,image BLOG,type VARCHAR)");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, CentralActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}