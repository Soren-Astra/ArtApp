package com.example.artapp.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.example.artapp.R

class NewChallengeActivity : Activity() {
    private lateinit var editChallengeView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_challenge)
        editChallengeView = findViewById<EditText>(R.id.edit_challenge)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editChallengeView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val challenge = editChallengeView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, challenge)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}