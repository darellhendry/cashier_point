package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.cashier;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.MainActivity;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item.ItemViewModel;

public class CashierFragment extends Fragment {

    private CashierViewModel cashierViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://cashier-point.appspot.com");

        // Points to the root reference
        StorageReference storageRef = storage.getReference();
        Log.d("test", String.valueOf(storageRef));
        // Points to "images"
        StorageReference imagesRef = storageRef.child("images");
        Log.d("test", String.valueOf(imagesRef));

        // Points to "images/space.jpg"
        // Note that you can use variables to create child values
        String fileName = "store.png";
        StorageReference spaceRef = imagesRef.child(fileName);
        Log.d("test", String.valueOf(spaceRef));

        // File path is "images/space.jpg"
        String path = spaceRef.getPath();
        Log.d("test", String.valueOf(path));

        // File name is "space.jpg"
        String name = spaceRef.getName();
        Log.d("test", String.valueOf(name));

        // Points to "images"
        imagesRef = spaceRef.getParent();
        Log.d("test", String.valueOf(imagesRef));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.pay, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_pay:
                MainActivity.navController.navigate(R.id.nav_cashier_pay);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cashierViewModel = ViewModelProviders.of(this).get(CashierViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cashier, container, false);
        return root;
    }
}