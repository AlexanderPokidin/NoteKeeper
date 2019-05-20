package com.pokidin.a.notekeeper.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Objects;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    @NonNull
    @ColumnInfo(name = "note")
    private String mText;

    @ColumnInfo(name = "date")
    private long mDate;

    public Note(@NonNull String text) {
        mText = text;
        mDate = getCurrentDate();
    }

    @Ignore
    public Note(int id, @NonNull String text) {
        mId = id;
        mText = text;
        mDate = getCurrentDate();
    }

    private long getCurrentDate() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    @NonNull
    public String getText() {
        return mText;
    }

    public void setText(@NonNull String text) {
        mText = text;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Note)) {
            return false;
        }
        Note note = (Note) obj;
        return mId == note.getId()
                && mDate == note.getDate()
                && Objects.equals(mText, note.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mText, mDate);
    }
}
