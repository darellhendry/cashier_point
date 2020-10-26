package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    private List<Item> items = new ArrayList<>();
    private String fragment;
    private OnItemClickListener listener;

    public ItemAdapter(String fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_card, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, int position) {
        final Item currentItem = items.get(position);
        holder.mItemName.setText(currentItem.getName());
        holder.mItemPrice.setText(String.valueOf(currentItem.getPrice()));
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference = storageReference.child(currentItem.getImageUrl());
        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.mItemImage.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("test", exception.toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public Item getItemAt(int position) {
        return items.get(position);
    }
    class ItemHolder extends RecyclerView.ViewHolder {
        ImageView mItemImage;
        TextView mItemName;
        TextView mItemPrice;
        View mParentLayout;

        public ItemHolder(@NonNull View view) {
            super(view);
            mItemImage = view.findViewById(R.id.image_view_icon);
            mItemName = view.findViewById(R.id.text_view_name);
            mItemPrice = view.findViewById(R.id.text_view_price);
            mParentLayout = view;
            if (fragment.equals("id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.cashier.CashierFragment")) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        if (listener != null && position != RecyclerView.NO_POSITION)
                            listener.onItemClick(items.get(position));
                    }
                });
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public void setOnItemClickListner(OnItemClickListener onItemClickListner) {
        this.listener = onItemClickListner;
    }
}
