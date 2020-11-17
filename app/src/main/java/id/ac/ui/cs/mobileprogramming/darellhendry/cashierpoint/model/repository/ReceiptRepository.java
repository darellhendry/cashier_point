package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Customer;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.FirebaseQueryLiveData;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Receipt;

public class ReceiptRepository {
    private static final DatabaseReference RECEIPT_REF =
            FirebaseDatabase.getInstance().getReference("/receipt");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(RECEIPT_REF);
    private final MutableLiveData<List<Receipt>> itemsLiveData =
            (MutableLiveData<List<Receipt>>) Transformations.map(liveData, new Deserializer());
    private List<Receipt> receipts;

    public List<Receipt> getQueryReceiptsLiveData(String query) {



        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/receipt");
        Query q = mDatabase.orderByChild("date").startAt(query).endAt(query+"\uf8ff");
        Log.d("test", "getQueryReceiptsLiveData");
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                receipts = new ArrayList<>();
                for (DataSnapshot data: snapshot.getChildren()) {
                    receipts.add(data.getValue(Receipt.class));
                    Log.d("test", data.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return receipts;
    }


    private class Deserializer implements Function<DataSnapshot, List<Receipt>> {
        @Override
        public List<Receipt> apply(DataSnapshot dataSnapshot) {
            List<Receipt> getItems = new ArrayList<>();
            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                getItems.add(snapshot.getValue(Receipt.class));
            }
            return getItems;
        }
    }

    public MutableLiveData<List<Receipt>> getReceiptsLiveData() {
        return itemsLiveData;
    }

    public void insertReceipt(Receipt receipt) {
        RECEIPT_REF.child(receipt.getId()).setValue(receipt);
    }

}
