package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.receipt;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Receipt;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item.ItemAdapter;

public class ReceiptFragment extends Fragment {

    private ReceiptViewModel mViewModel;

    public static ReceiptFragment newInstance() {
        return new ReceiptFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_receipt, container, false);
        mViewModel = new ViewModelProvider(this).get(ReceiptViewModel.class);
        RecyclerView recyclerView = root.findViewById(R.id.receipt_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final ReceiptAdapter adapter = new ReceiptAdapter();
        recyclerView.setAdapter(adapter);

        mViewModel.getReceipts().observe(getViewLifecycleOwner(), new Observer<List<Receipt>>() {
            @Override
            public void onChanged(List<Receipt> receipts) {
                adapter.setList(receipts);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}