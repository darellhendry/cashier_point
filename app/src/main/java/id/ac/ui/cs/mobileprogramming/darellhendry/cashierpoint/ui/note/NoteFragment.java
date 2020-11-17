package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.note;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.MainActivity;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Note;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item.ItemViewModel;

public class NoteFragment extends Fragment {

    private NoteViewModel mViewModel;

    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_note, container, false);
        root.findViewById(R.id.fab_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navController.navigate(R.id.nav_add_note);
            }
        });
        RecyclerView recyclerView = root.findViewById(R.id.note_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        mViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        LiveData<List<Note>> noteLiveData = mViewModel.getNotes();
        noteLiveData.observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                Log.d("test", String.valueOf(notes));
                adapter.setNotes(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mViewModel.delete(adapter.getItemAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}