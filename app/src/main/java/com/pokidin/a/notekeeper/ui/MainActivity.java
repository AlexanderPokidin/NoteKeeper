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
import android.widget.SearchView;
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
    private NoteListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpSearchView();
        setUpRecyclerView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteDetailsActivity.class);
                startActivityForResult(intent, NOTE_ACTIVITY_DETAILS_REQUEST_CODE);
            }
        });
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        mAdapter = new NoteListAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        mNoteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                mAdapter.setNotes(notes);
            }
        });

        mAdapter.setOnItemClickListener(new NoteListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Note note = mAdapter.getNoteAtPosition(position);
                launchUpdateNoteActivity(note);
            }
        });
    }

    private void setUpSearchView() {
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {
                mAdapter.getNoteFilter().filter(searchText);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_create:
                mAdapter.sortListByCreate();
                return true;
            case R.id.sort_by_update:
                mAdapter.sortListByUpdate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOTE_ACTIVITY_DETAILS_REQUEST_CODE && resultCode == RESULT_OK) {
            Note note = new Note(data.getStringExtra(NoteDetailsActivity.EXTRA_REPLY));
            mNoteViewModel.insertNote(note);
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String noteText = data.getStringExtra(NoteDetailsActivity.EXTRA_REPLY);
            int id = data.getIntExtra(NoteDetailsActivity.EXTRA_REPLY_ID, -1);
            if (id != -1) {
                mNoteViewModel.updateNote(new Note(id, noteText));
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
