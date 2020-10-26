package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.receipt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Receipt;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.repository.ReceiptRepository;

public class ReceiptViewModel extends ViewModel {
    private ReceiptRepository repository;

    public ReceiptViewModel() {
        this.repository = new ReceiptRepository();
    }

    public LiveData<List<Receipt>> getReceipts() {
        return repository.getReceiptsLiveData();
    }
}