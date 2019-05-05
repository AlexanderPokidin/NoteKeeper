package com.pokidin.a.notekeeper.repo;

import android.app.Application;

import com.pokidin.a.notekeeper.dao.NoteDao;
import com.pokidin.a.notekeeper.db.NoteDatabase;
import com.pokidin.a.notekeeper.entity.Note;

import java.util.List;

public class NoteRepository {
    private NoteDatabase db;

    public NoteRepository(Application application) {
        db = NoteDatabase.getInstance(application);
    }

    public void insert(Note note) {
        db.insert(note);
    }

    public List<Note> getAllNotes() {
        return db.getAllNotes();
    }
}
