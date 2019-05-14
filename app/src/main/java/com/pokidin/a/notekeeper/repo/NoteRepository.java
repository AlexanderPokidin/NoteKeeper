package com.pokidin.a.notekeeper.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.pokidin.a.notekeeper.dao.NoteDao;
//import com.pokidin.a.notekeeper.db.NoteDatabase;
import com.pokidin.a.notekeeper.db.NoteRoomDatabase;
import com.pokidin.a.notekeeper.entity.Note;

import java.util.List;

public class NoteRepository {
    private NoteDao mNoteDao;
    private LiveData<List<Note>> mAllNotes;

    public NoteRepository(Application application) {
        NoteRoomDatabase database = NoteRoomDatabase.getInstance(application);
        mNoteDao = database.getNoteDao();
        mAllNotes = mNoteDao.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    public void insert(Note note) {
        new InsertAsyncTask(mNoteDao).execute(note);
    }

    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao mAsyncNoteDao;

        public InsertAsyncTask(NoteDao dao) {
            mAsyncNoteDao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncNoteDao.insert(notes[0]);
            return null;
        }
    }
}