package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.note;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.MainActivity;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Customer;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Note;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.customer.CustomerViewModel;

public class AddNoteFragment extends Fragment {

    private EditText mTitle;
    private EditText mDescription;
    private NoteViewModel noteViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_note, container, false);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        mTitle = root.findViewById(R.id.title_input);
        mDescription = root.findViewById(R.id.description_input);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title = mTitle.getText().toString();
        String description = mDescription.getText().toString();
        switch (item.getItemId()) {
            case R.id.action_save:
                if (title.equals("") || description.equals("")) {
                    Toast.makeText(getContext(), R.string.no_empty_field, Toast.LENGTH_SHORT).show();
                    return false;
                }
                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date resultdate = new Date(date);
                Note newNote = new Note(title, description, resultdate);
                noteViewModel.insert(newNote);
                MainActivity.navController.navigate(R.id.nav_note);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}