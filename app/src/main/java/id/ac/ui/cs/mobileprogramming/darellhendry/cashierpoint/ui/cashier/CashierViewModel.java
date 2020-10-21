package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.cashier;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CashierViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CashierViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}