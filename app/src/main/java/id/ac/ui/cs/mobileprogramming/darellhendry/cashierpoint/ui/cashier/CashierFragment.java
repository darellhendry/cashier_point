package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.cashier;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.MainActivity;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;

public class CashierFragment extends Fragment {

    private CashierViewModel cashierViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.pay, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_pay:
                MainActivity.navController.navigate(R.id.nav_cashier_pay);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cashierViewModel =
                ViewModelProviders.of(this).get(CashierViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cashier, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        cashierViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}