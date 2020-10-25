package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.customer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Customer;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Item;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.repository.CustomerRepository;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.repository.ItemRepository;

public class CustomerViewModel extends ViewModel {

    private CustomerRepository repository;

    public CustomerViewModel() {
        this.repository = new CustomerRepository();
    }

    public LiveData<List<Customer>> getCustomers() {
        return repository.getCustomersLiveData();
    }

    public void insert(Customer customer){
        repository.insertCustomer(customer);
    }
}