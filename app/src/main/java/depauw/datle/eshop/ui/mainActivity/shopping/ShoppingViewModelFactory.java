package depauw.datle.eshop.ui.mainActivity.shopping;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import depauw.datle.eshop.data.dataSource.ProductDataSource;
import depauw.datle.eshop.data.repository.ProductRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class ShoppingViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShoppingViewModel.class)) {
            return (T) new ShoppingViewModel(ProductRepository.getInstance(new ProductDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}