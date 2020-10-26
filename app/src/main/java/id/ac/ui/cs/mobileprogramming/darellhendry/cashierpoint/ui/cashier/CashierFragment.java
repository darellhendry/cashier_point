package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.cashier;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.MainActivity;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Customer;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Item;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.customer.CustomerViewModel;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item.ItemAdapter;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item.ItemViewModel;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.utils.NetworkUtils;

public class CashierFragment extends Fragment {

    public static CashierViewModel cashierViewModel;
    private CustomerViewModel customerViewModel;
    private ItemViewModel itemViewModel;
    private ArrayAdapter spinnerAdapter;

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
        cashierViewModel = new ViewModelProvider(this).get(CashierViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_cashier, container, false);

        if (!NetworkUtils.isNetworkConnected(getActivity())){
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            return root;
        }

        final Spinner spinner = root.findViewById(R.id.spinner);
        spinnerAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        customerViewModel.getCustomers().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(final List<Customer> customers) {
                spinnerAdapter.clear();
                spinnerAdapter.addAll(customers);
                cashierViewModel.setCustomer(customers.get(0));
                spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        Log.d("test", customers.get(position).toString());
                        cashierViewModel.setCustomer(customers.get(position));
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) { }
                });
            }
        });




        RecyclerView recyclerView = root.findViewById(R.id.item_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final ItemAdapter itemAdapter = new ItemAdapter(this.getClass().getName());
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListner(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                cashierViewModel.addItem(item);
            }
        });

        LiveData<List<Item>> itemsLiveData = itemViewModel.getItems();
        itemsLiveData.observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                itemAdapter.setItems(items);
            }
        });

        LiveData<List<Item>> pickedItems = cashierViewModel.getItems();
        pickedItems.observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                Button quantityButton = (Button) root.findViewById(R.id.button_quantity);
                quantityButton.setText("QUANTITY\n" + items.size());
                int charged = cashierViewModel.getSumPrice();
                Button chargeButton = (Button) root.findViewById(R.id.button_charge);
                chargeButton.setText("CHARGE\n" + String.valueOf(charged));
            }
        });


        return root;
    }
}