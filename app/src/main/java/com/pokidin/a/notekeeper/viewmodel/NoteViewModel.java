package com.pokidin.a.notekeeper.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.pokidin.a.notekeeper.entity.Note;
import com.pokidin.a.notekeeper.repo.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository mNoteRepository;
//    private LiveData<List<Note>> mAllNotes;
    private LiveData<PagedList<Note>> mAllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        mNoteRepository = new NoteRepository(application);
        mAllNotes = mNoteRepository.getAllNotes();
    }

//    public LiveData<List<Note>> getAllNotes() {
//        return mAllNotes;
//    }

    public LiveData<PagedList<Note>> getAllNotes() {
        return mAllNotes;
    }

    public void insertNote(Note note) {
        mNoteRepository.insert(note);
    }

    public void deleteNote(int id) {
        mNoteRepository.deleteNote(id);
    }

    public void updateNote(Note note) {
        mNoteRepository.updateNote(note);
    }
}