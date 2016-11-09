package com.madebyatomicrobot.realmtest;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import io.realm.Realm;
import io.realm.Realm.Transaction;

public class MainService extends IntentService {
    public static Intent buildIntent(Context context) {
        return new Intent(context, MainService.class);
    }

    public MainService() {
        super(MainService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Transaction() {
            @Override
            public void execute(Realm realm) {
                Unique unique = new Unique();
                realm.copyToRealm(unique);
            }
        });
        realm.close();
    }
}
