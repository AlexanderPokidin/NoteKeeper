package com.pokidin.a.notekeeper.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.pokidin.a.notekeeper.R;
import com.pokidin.a.notekeeper.entity.Note;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    public static ClickListener sClickListener;
    private List<Note> mAllNotes;

    public NoteListAdapter(List<Note> allNotes) {
        mAllNotes = allNotes;
    }

    public Note getNoteAtPosition(int position) {
        return mAllNotes.get(position);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (mAllNotes != null) {
            Note current = mAllNotes.get(position);
            holder.mText.setText(current.getText());
            holder.mDate.setText(current.getDate());
        }
    }

    @Override
    public int getItemCount() {
        if (mAllNotes != null) {
            return mAllNotes.size();
        } else {
            return 0;
        }
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView mText;
        private TextView mDate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.tv_text);
            mDate = itemView.findViewById(R.id.tv_date);
        }
    }

    public void setOnItemClickListener(ClickListener listener) {
        NoteListAdapter.sClickListener = listener;
    }

    public interface ClickListener {
        void onItemClick(View view, int position);
    }
}
