package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.cashier;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.MainActivity;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.customer.CustomerViewModel;

public class CashierPayFragment extends Fragment {
    private EditText inputCash;
    private TextView totalTextView;
    private CashierViewModel cashierViewModel;
    private CustomerViewModel customerViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cashierViewModel = CashierFragment.cashierViewModel;
        View root = inflater.inflate(R.layout.fragment_cashier_pay, container, false);
        totalTextView = root.findViewById(R.id.total_charge);
        totalTextView.setText(String.valueOf(cashierViewModel.getSumPrice()));
        inputCash = root.findViewById(R.id.input_cash);
        inputCash.setText(String.valueOf(cashierViewModel.getSumPrice()));
        root.findViewById(R.id.fab_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(inputCash.getText()).equals("")) {
                    Toast.makeText(getContext(), "Field must not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                cashierViewModel.insertReceipt(String.valueOf(inputCash.getText()));
                MainActivity.navController.navigate(R.id.nav_home);
                Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}