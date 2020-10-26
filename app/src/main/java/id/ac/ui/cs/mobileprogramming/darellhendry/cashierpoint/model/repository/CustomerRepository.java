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
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Item;

public class CustomerRepository {

    private static final DatabaseReference CUSTOMER_REF =
            FirebaseDatabase.getInstance().getReference("/customer");
    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(CUSTOMER_REF);
    private final LiveData<List<Customer>> itemsLiveData =
            Transformations.map(liveData, new CustomerRepository.Deserializer());


    private class Deserializer implements Function<DataSnapshot, List<Customer>> {
        @Override
        public List<Customer> apply(DataSnapshot dataSnapshot) {
            List<Customer> getItems = new ArrayList<>();
            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                getItems.add(snapshot.getValue(Customer.class));
            }
            return getItems;
        }
    }

    public LiveData<List<Customer>> getCustomersLiveData() {
        return itemsLiveData;
    }

    public void insertCustomer(Customer customer) {
        CUSTOMER_REF.child(encodeUserEmail(customer.getEmail())).setValue(customer);
    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    static String decodeUserEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }

}
