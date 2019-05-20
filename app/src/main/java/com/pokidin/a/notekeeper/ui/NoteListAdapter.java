package com.pokidin.a.notekeeper.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pokidin.a.notekeeper.R;
import com.pokidin.a.notekeeper.entity.Note;

import java.util.Date;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private List<Note> mNotes;
    private final LayoutInflater mInflater;
    private static ClickListener sClickListener;

    public NoteListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (mNotes != null) {
            Note current = mNotes.get(position);
            holder.textTv.setText(current.getText());
            holder.dateTv.setText(formatDate(current.getCreateDate()));
        } else {
            holder.textTv.setText(R.string.no_word);
        }
    }

    @Override
    public int getItemCount() {
        if (mNotes != null) {
            return mNotes.size();
        } else {
            return 0;
        }
    }

    private String formatDate(long longDate){
        return (String) DateFormat.format("hh:mm:ss dd.MM.yy", new Date(longDate));
    }

    void setNotes(List<Note> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAtPosition(int position) {
        return mNotes.get(position);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTv;
        private final TextView dateTv;

        private NoteViewHolder(View itemView) {
            super(itemView);
            textTv = itemView.findViewById(R.id.tv_text);
            dateTv = itemView.findViewById(R.id.tv_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sClickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        NoteListAdapter.sClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
