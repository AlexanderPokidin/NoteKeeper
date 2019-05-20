package com.pokidin.a.notekeeper.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.pokidin.a.notekeeper.dao.NoteDao;
import com.pokidin.a.notekeeper.entity.Note;

@Database(entities = {Note.class}, version = 4, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NoteDao getNoteDao();

    private static NoteRoomDatabase sInstance;

    public static NoteRoomDatabase getInstance(final Context context) {
        if (sInstance == null) {
            // Create database
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteRoomDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sInstance;
    }
}