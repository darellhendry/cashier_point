package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.io.ByteArrayOutputStream;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Item;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.repository.ItemRepository;

public class ItemViewModel extends ViewModel {
    private ItemRepository repository;

    public ItemViewModel() {
        repository = new ItemRepository();
    }

    public LiveData<List<Item>> getItems() {
        return repository.getItemsLiveData();
    }

    public void insert(Item item, Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        repository.insertItem(item, data);
    }

    public void delete(Item item) {
        repository.deleteItem(item);
        Log.d("test", item.getName());
    }
}