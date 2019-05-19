package com.pokidin.a.notekeeper.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pokidin.a.notekeeper.R;
import com.pokidin.a.notekeeper.entity.Note;
import com.pokidin.a.notekeeper.viewmodel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NOTE_ACTIVITY_DETAILS_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    public static final String EXTRA_DATA_UPDATE_NOTE = "extra_note_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private NoteViewModel mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        final NoteListAdapter adapter = new NoteListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        mNoteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                // Update the cached copy of the notes in the adapter.
                adapter.setNotes(notes);
            }
        });

        adapter.setOnItemClickListener(new NoteListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Note note = adapter.getNoteAtPosition(position);
                launchUpdateNoteActivity(note);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteDetailsActivity.class);
                startActivityForResult(intent, NOTE_ACTIVITY_DETAILS_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sort_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOTE_ACTIVITY_DETAILS_REQUEST_CODE && resultCode == RESULT_OK) {
            Note note = new Note(data.getStringExtra(NoteDetailsActivity.EXTRA_REPLY), "030303");
            mNoteViewModel.insertNote(note);
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String noteText = data.getStringExtra(NoteDetailsActivity.EXTRA_REPLY);
            int id = data.getIntExtra(NoteDetailsActivity.EXTRA_REPLY_ID, -1);
            if (id != -1) {
                mNoteViewModel.updateNote(new Note(id, noteText, "040404"));
            } else {
                Toast.makeText(this, R.string.unable_to_update, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_FIRST_USER) {
            int id = data.getIntExtra(NoteDetailsActivity.EXTRA_REPLY_ID, -1);
            if (id != -1) {
                mNoteViewModel.deleteNote(id);
            } else {
                Toast.makeText(this, R.string.unable_to_delete, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_word, Toast.LENGTH_SHORT).show();
        }
    }

    private void launchUpdateNoteActivity(Note note) {
        Intent intent = new Intent(this, NoteDetailsActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_NOTE, note.getText());
        intent.putExtra(EXTRA_DATA_ID, note.getId());
        startActivityForResult(intent, UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
    }
}
