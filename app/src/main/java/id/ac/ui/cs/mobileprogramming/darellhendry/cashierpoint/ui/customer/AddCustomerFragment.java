package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.customer;

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

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.MainActivity;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Customer;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item.ItemViewModel;

public class AddCustomerFragment extends Fragment {

    private EditText mNameInput;
    private EditText mEmailInput;
    private CustomerViewModel custViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String name = mNameInput.getText().toString();
        String email = mEmailInput.getText().toString();
        switch (item.getItemId()) {
            case R.id.action_save:
                if (name.equals("") || email.equals("")) {
                    Toast.makeText(getContext(), "Fields must not be empty", Toast.LENGTH_SHORT).show();
                    return false;
                }
                Customer newCustomer = new Customer(name, email);
                custViewModel.insert(newCustomer);
                MainActivity.navController.navigate(R.id.nav_customer);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_customer, container, false);
        custViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        mNameInput = root.findViewById(R.id.name_input);
        mEmailInput = root.findViewById(R.id.email_input);
        return root;
    }
}