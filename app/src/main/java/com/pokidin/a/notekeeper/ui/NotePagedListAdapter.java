package com.pokidin.a.notekeeper.ui;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.pokidin.a.notekeeper.R;
import com.pokidin.a.notekeeper.entity.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NotePagedListAdapter extends PagedListAdapter<Note, NotePagedListAdapter.NotePagedViewHolder> {

    private static final String DATE_PATTERN = "HH:mm:ss dd.MM.yy";
    private static NoteListAdapter.ClickListener sClickListener;
    private List<Note> mNotes;

    protected NotePagedListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NotePagedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotePagedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotePagedViewHolder holder, int position) {
        Note current = getItem(position);
        if (current != null) {
            holder.textTv.setText(current.getText());
            holder.dateTv.setText(formatDate(current.getDate()));
        } else {
            holder.textTv.setText(R.string.no_word);
        }
    }

    private String formatDate(long longDate) {
        return (String) DateFormat.format(DATE_PATTERN, new Date(longDate));
    }

    public static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldNote, @NonNull Note newNote) {
            return oldNote.getId() == newNote.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldNote, @NonNull Note newNote) {
            return oldNote.equals(newNote);
        }
    };

    Note getNoteAtPosition(int position) {
        return getItem(position);
    }

    Filter getNoteFilter() {
        return noteFilter;
    }

    private Filter noteFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Note> filteredNotes = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredNotes.addAll(mNotes);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Note note : mNotes) {
                    if (note.getText().toLowerCase().contains(filterPattern)) {
                        filteredNotes.add(note);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredNotes;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mNotes.clear();
            mNotes.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    // At the top of the list are the most recently added notes.
    void sortListByCreate() {
        Collections.sort(mNotes, new Comparator<Note>() {
            @Override
            public int compare(Note n1, Note n2) {
                return Integer.compare(n2.getId(), n1.getId());
            }
        });
        notifyDataSetChanged();
    }

    // At the top of the list are the last edited notes.
    void sortListByUpdate() {
        Collections.sort(mNotes, new Comparator<Note>() {
            @Override
            public int compare(Note n1, Note n2) {
                return Long.compare(n2.getDate(), n1.getDate());
            }
        });
        notifyDataSetChanged();
    }

    class NotePagedViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTv;
        private final TextView dateTv;

        private NotePagedViewHolder(View itemView) {
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

    void setOnItemClickListener(NoteListAdapter.ClickListener clickListener) {
        NotePagedListAdapter.sClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
