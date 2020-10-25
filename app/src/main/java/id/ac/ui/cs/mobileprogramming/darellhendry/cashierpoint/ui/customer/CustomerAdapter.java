package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Customer;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Item;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item.ItemAdapter;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerHolder> {

    private List<Customer> customers = new ArrayList<>();


    @NonNull
    @Override
    public CustomerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_customer_card, parent, false);
        return new CustomerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerHolder holder, int position) {
        Customer customer = customers.get(position);
        holder.mCustName.setText(customer.getName());
        holder.mCustEmail.setText(customer.getEmail());
    }

    public void setItems(List<Customer> customers) {
        this.customers = customers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    class CustomerHolder extends RecyclerView.ViewHolder {
        TextView mCustName;
        TextView mCustEmail;

        public CustomerHolder(@NonNull View view) {
            super(view);
            mCustName = view.findViewById(R.id.text_view_cust_name);
            mCustEmail = view.findViewById(R.id.text_view_email);
        }
    }
}
