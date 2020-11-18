package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Note;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.dao.NoteDao;

@Database(entities = {Note.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return  instance;
    }
}
