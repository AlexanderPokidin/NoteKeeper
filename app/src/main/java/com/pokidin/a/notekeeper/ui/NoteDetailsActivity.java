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

    private EditText mEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        mEditText = findViewById(R.id.et_text);
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
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
