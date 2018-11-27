package com.example.sakkawy.movieapp.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MovieEntry.class},exportSchema = false,version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    private static final String TAG = "MovieDatabase";
    private final static Object LOCK = new Object();
    private static MovieDatabase sInstance;
    private static final String DATABASE_NAME = "movieDatabase";

    public static MovieDatabase getInstance(Context context) {
        if(sInstance == null){
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class,
                        DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract MovieDao movieDao();
}
