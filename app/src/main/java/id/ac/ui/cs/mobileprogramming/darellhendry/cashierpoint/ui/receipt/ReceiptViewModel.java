package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.receipt;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Receipt;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.repository.ReceiptRepository;

public class ReceiptViewModel extends ViewModel {
    private ReceiptRepository repository;
    private MutableLiveData<List<Receipt>> receipts;

    public ReceiptViewModel() {
        this.repository = new ReceiptRepository();
        receipts = repository.getReceiptsLiveData();
    }

    public LiveData<List<Receipt>> getReceipts() {
        return receipts;
    }

    public void updateReceips(CharSequence charSequence) {
        Log.d("test", "updateReceips");
        List<Receipt> newReceipts = repository.getQueryReceiptsLiveData(String.valueOf(charSequence));
        if (newReceipts == null)
            newReceipts = new ArrayList<>();
        receipts.setValue(newReceipts);
    }
}