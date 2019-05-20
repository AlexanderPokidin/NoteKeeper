package com.pokidin.a.notekeeper.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pokidin.a.notekeeper.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNote(Note note);

    @Query("DELETE from note_table WHERE mId = :id")
    void deleteNote(int id);

    @Update
    void update(Note note);

    @Query("SELECT * from note_table LIMIT 1")
    Note[] getAnyNote();

    @Query("SELECT * FROM note_table ORDER BY create_date DESC")
    LiveData<List<Note>> getAllNotes();
}