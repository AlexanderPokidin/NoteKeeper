package com.pokidin.a.notekeeper.db;

import android.content.Context;

import com.pokidin.a.notekeeper.dao.NoteDao;
import com.pokidin.a.notekeeper.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase implements NoteDao {

    private List<Note> allNotes = new ArrayList<>();
    private static NoteDatabase INSTANCE;

    public static NoteDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NoteDatabase();
        }
        return INSTANCE;
    }

    @Override
    public void insert(Note note) {
        allNotes.add(note);
    }

    @Override
    public List<Note> getAllNotes() {
        return allNotes;
    }
}
