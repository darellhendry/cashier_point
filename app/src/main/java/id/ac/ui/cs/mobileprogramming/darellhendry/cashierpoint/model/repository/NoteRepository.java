package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Note;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.dao.NoteDao;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.database.NoteDatabase;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note item) {
        new InsertItemAsyncTask(noteDao).execute(item);
    }

    public void update(Note item) {
        new UpdateItemAsyncTask(noteDao).execute(item);
    }

    public void delete(Note item) {
        new DeleteItemAsyncTask(noteDao).execute(item);
    }

    public LiveData<List<Note>> getAllINotes() {
        return allNotes;
    }

    private static class InsertItemAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public InsertItemAsyncTask(NoteDao itemDao) {
            this.noteDao = itemDao;
        }

        @Override
        protected Void doInBackground(Note... items) {
            noteDao.insert(items[0]);
            return null;
        }
    }

    private static class UpdateItemAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public UpdateItemAsyncTask(NoteDao itemDao) {
            this.noteDao = itemDao;
        }

        @Override
        protected Void doInBackground(Note... items) {
            noteDao.update(items[0]);
            return null;
        }
    }

    private static class DeleteItemAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public DeleteItemAsyncTask(NoteDao itemDao) {
            this.noteDao = itemDao;
        }

        @Override
        protected Void doInBackground(Note... items) {
            noteDao.delete(items[0]);
            return null;
        }
    }
}