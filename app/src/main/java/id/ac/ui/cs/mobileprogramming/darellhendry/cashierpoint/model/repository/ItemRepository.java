package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.repository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.FirebaseQueryLiveData;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Item;

public class ItemRepository {

    private static final DatabaseReference ITEM_REF =
            FirebaseDatabase.getInstance().getReference("/item");
    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(ITEM_REF);
    private final LiveData<List<Item>> itemsLiveData =
            Transformations.map(liveData, new Deserializer());
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private MutableLiveData<Bitmap> image;
    private Item tempItem;

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

    public void insertItem(Item item, byte[] dataImage) {
        tempItem = item;
        StorageReference imageRef = storageReference.child(item.getImageUrl());
        UploadTask uploadTask = imageRef.putBytes(dataImage);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d("test", "onFailure");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("test", "onSuccess");
                ITEM_REF.child(tempItem.getName()).setValue(tempItem);
            }
        });
    }

    public void deleteItem(Item item) {
        tempItem = item;
        ITEM_REF.child(item.getName()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Log.d("test", "Item deleted");
                if (tempItem.getImageUrl().equals("store.png"))
                    return;
                StorageReference imageRef = storageReference.child(tempItem.getImageUrl());
                imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("test", "image deleted");
                    }
                });
            }
        });
    }

}
