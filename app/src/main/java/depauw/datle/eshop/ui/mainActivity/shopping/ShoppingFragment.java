package depauw.datle.eshop.ui.mainActivity.shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import depauw.datle.eshop.R;
import depauw.datle.eshop.data.dataSource.CartDataSource;
import depauw.datle.eshop.data.repository.CartRepository;
import depauw.datle.eshop.databinding.FragmentShoppingBinding;
import depauw.datle.eshop.ui.mainActivity.shopping.filter.FilterDialog;
import depauw.datle.eshop.ui.mainActivity.shopping.filter.Filters;

public class ShoppingFragment extends Fragment {
    private final String tag = "SF";
    private FragmentShoppingBinding binding;
    private ShoppingViewModel shoppingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentShoppingBinding.inflate(inflater, container, false);
        shoppingViewModel = new ViewModelProvider(this, new ShoppingViewModelFactory()).get(ShoppingViewModel.class);
        CartRepository.getInstance(new CartDataSource());
        binding.shoppingLoading.setVisibility(View.VISIBLE);
        shoppingViewModel.getProductList();
        binding.recyclerviewProductList.setLayoutManager(
                new GridLayoutManager(this.getContext(), 2)
        );
        ShoppingFragmentAdapter shoppingFragmentAdapter = new ShoppingFragmentAdapter();
        binding.recyclerviewProductList.setAdapter(shoppingFragmentAdapter);
        shoppingViewModel.getLiveProducts().observe(this, products -> {
            binding.shoppingLoading.setVisibility(View.GONE);
            shoppingFragmentAdapter.setProducts(products);
        });
        binding.searchView.setOnClickListener(view -> binding.searchView.setIconified(false));
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Filters.getInstance().setKeyword(s.toLowerCase());
                shoppingViewModel.filterProducts();
                return false;
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_shopping_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.product_filter) {
            FilterDialog filterDialog = new FilterDialog(getContext(), shoppingViewModel);
            filterDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}