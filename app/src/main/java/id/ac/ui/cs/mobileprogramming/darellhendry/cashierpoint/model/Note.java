package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int noteId;
    private String title;
    private String description;
    private Date createdAt;

    public Note(String title, String description, Date createdAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }
}
