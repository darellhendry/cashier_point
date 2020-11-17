package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.note;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Note;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.repository.NoteRepository;

public class NoteViewModel extends AndroidViewModel {
    NoteRepository repository;
    LiveData<List<Note>> notes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        notes = repository.getAllINotes();
    }
    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }
}