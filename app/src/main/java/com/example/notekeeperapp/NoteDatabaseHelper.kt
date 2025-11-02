package com.example.notekeeperapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notes.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NOTES = "notes"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_TIMESTAMP = "timestamp"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NOTES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_CONTENT TEXT,
                $COLUMN_TIMESTAMP TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NOTES")
        onCreate(db)
    }

    fun addNote(note: Note) {
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_TIMESTAMP, note.timestamp)
        }
        writableDatabase.use { db ->
            db.insert(TABLE_NOTES, null, values)
        }
    }

    fun getAllNotes(): List<Note> {
        val noteList = mutableListOf<Note>()
        readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM $TABLE_NOTES", null)
            cursor.use { c ->
                if (c.moveToFirst()) {
                    val idCol = c.getColumnIndexOrThrow(COLUMN_ID)
                    val titleCol = c.getColumnIndexOrThrow(COLUMN_TITLE)
                    val contentCol = c.getColumnIndexOrThrow(COLUMN_CONTENT)
                    val timestampCol = c.getColumnIndexOrThrow(COLUMN_TIMESTAMP)
                    do {
                        noteList.add(
                            Note(
                                c.getLong(idCol),
                                c.getString(titleCol),
                                c.getString(contentCol),
                                c.getString(timestampCol)
                            )
                        )
                    } while (c.moveToNext())
                }
            }
        }
        return noteList
    }

    fun getNoteById(noteId: Long): Note? {
        var note: Note? = null
        readableDatabase.use { db ->
            val cursor = db.query(
                TABLE_NOTES,
                null,
                "$COLUMN_ID = ?",
                arrayOf(noteId.toString()),
                null, null, null
            )
            cursor.use { c ->
                if (c.moveToFirst()) {
                    val idCol = c.getColumnIndexOrThrow(COLUMN_ID)
                    val titleCol = c.getColumnIndexOrThrow(COLUMN_TITLE)
                    val contentCol = c.getColumnIndexOrThrow(COLUMN_CONTENT)
                    val timestampCol = c.getColumnIndexOrThrow(COLUMN_TIMESTAMP)
                    note = Note(
                        c.getLong(idCol),
                        c.getString(titleCol),
                        c.getString(contentCol),
                        c.getString(timestampCol)
                    )
                }
            }
        }
        return note
    }


    fun updateNote(note: Note): Int {
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_TIMESTAMP, note.timestamp)
        }
        return writableDatabase.use { db ->
            db.update(TABLE_NOTES, values, "$COLUMN_ID = ?", arrayOf(note.id.toString()))
        }
    }

    fun deleteNote(note: Note) {
        writableDatabase.use { db ->
            db.delete(TABLE_NOTES, "$COLUMN_ID = ?", arrayOf(note.id.toString()))
        }
    }
}