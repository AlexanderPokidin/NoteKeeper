package com.pokidin.a.notekeeper.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.pokidin.a.notekeeper.R;
import com.pokidin.a.notekeeper.entity.Note;
import com.pokidin.a.notekeeper.repo.NoteRepository;

public class NoteDetailsActivity extends AppCompatActivity {

    private EditText mEditText;
    private NoteRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        init();
    }

    private void init() {
        mEditText = findViewById(R.id.et_text);
        mRepository = new NoteRepository(getApplication());
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNewNote(getUserText(mEditText));
    }

    private String getUserText(TextView textView) {
        return String.valueOf(textView.getText());
    }

    private void saveNewNote(String string) {
        Note note = new Note(string, "date");
        mRepository.insert(note);
    }
}
