package com.madebyatomicrobot.realmtest;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Unique extends RealmObject {
    @PrimaryKey String id = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }
}
