package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.repository;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Customer;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.FirebaseQueryLiveData;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Receipt;

public class ReceiptRepository {
    private static final DatabaseReference RECEIPT_REF =
            FirebaseDatabase.getInstance().getReference("/receipt");
    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(RECEIPT_REF);
    private final LiveData<List<Receipt>> itemsLiveData =
            Transformations.map(liveData, new ReceiptRepository.Deserializer());


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

    public LiveData<List<Receipt>> getReceiptsLiveData() {
        return itemsLiveData;
    }

    public void insertReceipt(Receipt receipt) {
        RECEIPT_REF.child(receipt.getId()).setValue(receipt);
    }

}
