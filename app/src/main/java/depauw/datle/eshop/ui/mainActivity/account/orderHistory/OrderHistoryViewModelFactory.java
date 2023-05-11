package depauw.datle.eshop.ui.mainActivity.account.orderHistory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import depauw.datle.eshop.data.dataSource.OrderDataSource;
import depauw.datle.eshop.data.repository.OrderRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class OrderHistoryViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(OrderHistoryViewModel.class)) {
            return (T) new OrderHistoryViewModel(OrderRepository.getInstance(new OrderDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}