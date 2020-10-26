package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.receipt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Receipt;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder> {
    private List<Receipt> receipts = new ArrayList<>();;

    @NonNull
    @Override
    public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_receipt_card, parent, false);
        return new ReceiptViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position) {
        Receipt currentReceipt = receipts.get(position);
        holder.mCustomer.setText(currentReceipt.getCustomerEmail());
        holder.mPrice.setText(String.valueOf(currentReceipt.getPrice()));
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    public void setList(List<Receipt> receipts) {
        this.receipts = receipts;
        notifyDataSetChanged();
    }

    class ReceiptViewHolder extends RecyclerView.ViewHolder {
        private TextView mPrice;
        private TextView mCustomer;

        public ReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            mPrice = itemView.findViewById(R.id.text_view_price);
            mCustomer = itemView.findViewById(R.id.text_view_customer);
        }
    }
}
