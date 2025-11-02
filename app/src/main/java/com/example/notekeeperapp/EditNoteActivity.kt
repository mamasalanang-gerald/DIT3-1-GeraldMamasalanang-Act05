package com.example.notekeeperapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class EditNoteActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var db: NoteDatabaseHelper
    private var noteId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        titleEditText = findViewById(R.id.titleEditText)
        contentEditText = findViewById(R.id.contentEditText)
        saveButton = findViewById(R.id.saveButton)

        db = NoteDatabaseHelper(this)

        noteId = intent.getLongExtra("note_id", -1)
        if (noteId == -1L) {
            finish()
            return
        }

        val note = db.getNoteById(noteId)
        if (note != null) {
            titleEditText.setText(note.title)
            contentEditText.setText(note.content)
        }

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()
            val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
            val updatedNote = Note(noteId, title, content, timestamp)
            db.updateNote(updatedNote)
            finish()
        }
    }
}