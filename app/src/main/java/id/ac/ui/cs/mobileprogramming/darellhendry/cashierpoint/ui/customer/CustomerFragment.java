package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.MainActivity;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Customer;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Item;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item.ItemAdapter;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item.ItemViewModel;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.utils.NetworkUtils;

public class CustomerFragment extends Fragment {

    private CustomerViewModel customerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_customer, container, false);
        root.findViewById(R.id.fab_customer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navController.navigate(R.id.nav_add_customer);
            }
        });
        RecyclerView recyclerView = root.findViewById(R.id.customer_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final CustomerAdapter adapter = new CustomerAdapter();
        recyclerView.setAdapter(adapter);

        if (!NetworkUtils.isNetworkConnected(getActivity())){
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            return root;
        }

        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);

        LiveData<List<Customer>> itemsLiveData = customerViewModel.getCustomers();
        itemsLiveData.observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                adapter.setItems(customers);
            }
        });

        return root;
    }
}