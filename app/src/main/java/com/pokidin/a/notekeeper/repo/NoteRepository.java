package com.pokidin.a.notekeeper.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.pokidin.a.notekeeper.dao.NoteDao;
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
        new InsertNoteAsyncTask(mNoteDao).execute(note);
    }

    public void deleteNote(int id) {
        new DeleteNoteAsyncTask(mNoteDao).execute(id);
    }

    public void updateNote(Note note) {
        new UpdateNoteAsyncTask(mNoteDao).execute(note);
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao mAsyncNoteDao;

        InsertNoteAsyncTask(NoteDao dao) {
            mAsyncNoteDao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncNoteDao.insertNote(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private NoteDao mAsyncTaskDao;

        DeleteNoteAsyncTask(NoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mAsyncTaskDao.deleteNote(integers[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao mAsyncTaskDao;

        UpdateNoteAsyncTask(NoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.update(notes[0]);
            return null;
        }
    }
}