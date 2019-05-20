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

    @ColumnInfo(name = "create_date")
    private long mCreateDate;

    @ColumnInfo(name = "update_date")
    private long mUpdateDate;

    public Note(@NonNull String text) {
        mText = text;
        mCreateDate = getCurrentDate();
    }

    @Ignore
    public Note(int id, @NonNull String text) {
        mId = id;
        mText = text;
        mCreateDate = getCurrentDate();
    }

    @Ignore
    public Note(int id, @NonNull String text, long updateDate) {
        mId = id;
        mText = text;
        mCreateDate = getCurrentDate();
        mUpdateDate = updateDate;
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

    public long getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(long createDate) {
        mCreateDate = createDate;
    }

    public long getUpdateDate() {
        return mUpdateDate;
    }

    public void setUpdateDate(long updateDate) {
        mUpdateDate = updateDate;
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
                && mCreateDate == note.getCreateDate()
                && mUpdateDate == note.getUpdateDate()
                && Objects.equals(mText, note.getText());

    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mText, mCreateDate, mUpdateDate);
    }
}
