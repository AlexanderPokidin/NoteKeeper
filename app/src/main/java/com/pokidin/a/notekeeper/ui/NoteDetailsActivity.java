package com.pokidin.a.notekeeper.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pokidin.a.notekeeper.R;

public class NoteDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "reply";
    public static final String EXTRA_REPLY_ID = "reply_id";

    private EditText mEditText;
    private Button mButton;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        mEditText = findViewById(R.id.et_text);
        extras = getIntent().getExtras();
        if (extras != null) {
            String text = extras.getString(MainActivity.EXTRA_DATA_UPDATE_NOTE, "");
            if (!text.isEmpty()) {
                mEditText.setText(text);
                mEditText.setSelection(text.length());
                mEditText.requestFocus();
            }
        }

        mButton = findViewById(R.id.btn_done);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        setExtraReply();
        super.onBackPressed();
    }

    private void setExtraReply() {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditText.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String note = mEditText.getText().toString();
            replyIntent.putExtra(EXTRA_REPLY, note);
            if (extras != null && extras.containsKey(MainActivity.EXTRA_DATA_ID)) {
                int id = extras.getInt(MainActivity.EXTRA_DATA_ID, -1);
                if (id != -1) {
                    replyIntent.putExtra(EXTRA_REPLY_ID, id);
                }
            }
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
