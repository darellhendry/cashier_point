package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.cashier;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Customer;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Item;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Receipt;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.repository.ReceiptRepository;

public class CashierViewModel extends ViewModel {

    private MutableLiveData<List<Item>> mItems;
    private MutableLiveData<Customer> mCustomer;
    private ReceiptRepository repository;

    public CashierViewModel() {
        repository = new ReceiptRepository();
        mItems = new MutableLiveData<>();
        mItems.setValue(new ArrayList<Item>());
        mCustomer = new MutableLiveData<>();
        mCustomer.setValue(new Customer("",""));
    }

    public void addItem(Item item) {
        List<Item> newItem = mItems.getValue();
        newItem.add(item);
        mItems.setValue(newItem);
    }

    public MutableLiveData<List<Item>> getItems() {
        return mItems;
    }

    public MutableLiveData<Customer> getCustomer() {
        return mCustomer;
    }

    public void insertReceipt(String cash) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(date);
        Log.d("test",  sdf.format(resultdate));
        Receipt receipt = new Receipt(id, mCustomer.getValue().getEmail(), mItems.getValue(), Integer.parseInt(cash), sdf.format(resultdate));
        repository.insertReceipt(receipt);
    }

    public int getSumPrice() {
        List<Item> selectedItems = mItems.getValue();
        int sum = 0;
        for (Item item: selectedItems) {
            sum += item.getPrice();
        }
        return sum;
    }

    public List<Item> getStaticItems() {
        return mItems.getValue();
    }

    public Customer getStaticCustomer() {
        return mCustomer.getValue();
    }

    public void setCustomer(Customer customer) {
        mCustomer = new MutableLiveData<>();
        mCustomer.setValue(customer);
    }
}