package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

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

    public void insert(Item item) {
        repository.insertItem(item);
    }

}