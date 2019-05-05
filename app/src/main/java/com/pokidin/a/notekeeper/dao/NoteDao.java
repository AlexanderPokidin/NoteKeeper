package com.pokidin.a.notekeeper.dao;

import com.pokidin.a.notekeeper.entity.Note;

import java.util.List;

public interface NoteDao {

    void insert(Note note);

    List<Note> getAllNotes();

}
