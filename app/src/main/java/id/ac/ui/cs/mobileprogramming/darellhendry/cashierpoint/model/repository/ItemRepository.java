package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.repository;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.FirebaseQueryLiveData;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Item;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item.ItemViewModel;

public class ItemRepository {

    private static final DatabaseReference ITEM_REF =
            FirebaseDatabase.getInstance().getReference("/item");
    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(ITEM_REF);
    private final LiveData<List<Item>> itemsLiveData =
            Transformations.map(liveData, new Deserializer());

    private class Deserializer implements Function<DataSnapshot, List<Item>> {
        @Override
        public List<Item> apply(DataSnapshot dataSnapshot) {
            List<Item> getItems = new ArrayList<>();
            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                getItems.add(snapshot.getValue(Item.class));
            }
            return getItems;
        }
    }



    public LiveData<List<Item>> getItemsLiveData() {
        return itemsLiveData;
    }

    public void insertItem(Item item) {
        ITEM_REF.child(item.getName()).setValue(item);
    }
}
