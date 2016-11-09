package com.madebyatomicrobot.realmtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Handler handler;
    private Realm realm;

    private Runnable adder = new Runnable() {
        @Override
        public void run() {
            addViaService();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.start_adding).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleStartAdding();
            }
        });

        findViewById(R.id.stop_adding).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleStopAdding();
            }
        });

        handler = new Handler();
        realm = Realm.getDefaultInstance();
        setupChangeListener();
    }

    @Override
    protected void onDestroy() {
        handleStopAdding();
        realm.close();
        super.onDestroy();
    }

    private void setupChangeListener() {
        RealmResults<Unique> results = realm.where(Unique.class).findAll();
        handleResults(results);

        results.addChangeListener(new RealmChangeListener<RealmResults<Unique>>() {
            @Override
            public void onChange(RealmResults<Unique> element) {
                handleResults(element);
            }
        });
    }

    private void handleStartAdding() {
        handler.postDelayed(adder, 100);  // same result for larger delays
    }

    private void handleStopAdding() {
        handler.removeCallbacks(adder);
    }

    private void addViaService() {
        Intent intent = MainService.buildIntent(this);
        startService(intent);

        handleStartAdding();
    }

    private void handleResults(List<Unique> results) {
        TextView countView = (TextView) findViewById(R.id.count);
        countView.setText(String.format("Count: %d", results.size()));
    }
}