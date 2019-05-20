package com.pokidin.a.notekeeper.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.pokidin.a.notekeeper.dao.NoteDao;
import com.pokidin.a.notekeeper.entity.Note;

@Database(entities = {Note.class}, version = 3, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NoteDao getNoteDao();

    private static NoteRoomDatabase sInstance;

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE note_table ADD COLUMN update_date LONG");
        }
    };

    public static NoteRoomDatabase getInstance(final Context context) {
        if (sInstance == null) {
            // Create database
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteRoomDatabase.class, "note_database")
//                    .addCallback(sNoteDbCallback)    // TODO: Remove after testing
                    .fallbackToDestructiveMigration()
//                    .addMigrations(NoteRoomDatabase.MIGRATION_1_2)
                    .build();
        }
        return sInstance;
    }


    // Class and method for filling the database with fake data
    // TODO: Remove after testing
//    private static NoteRoomDatabase.Callback sNoteDbCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//            new PopulateDbAsync(sInstance).execute();
//        }
//    };
//
//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//        private final NoteDao mNoteDao;
//        Note[] notes = new Note[0];
////                {new Note("First note", "101010"), new Note("Second note", "202020"), new Note("Third note", "303030"),};
//
//        PopulateDbAsync(NoteRoomDatabase db) {
//            mNoteDao = db.getNoteDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            if (mNoteDao.getAnyNote().length < 1) {
//                for (Note note : notes) {
//                    mNoteDao.insertNote(note);
//                }
//            }
//            return null;
//        }
//    }
}