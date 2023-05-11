package depauw.datle.eshop;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import java.time.OffsetDateTime;
import java.util.List;

import depauw.datle.eshop.data.model.Product;
import depauw.datle.eshop.data.repository.ProductRepository;

public class Helper {
    public static String dollarize(float amount) {
        return "$" + amount;
    }
    public static String addPoundKey(int num) {return "#" + num;}
    public static boolean isValidMonth(int month) {
        return month <= 12 && month > 0;
    }
    public static float getTotalCharge(List<Product> productList, boolean isProductComplete) {
        float totalCharge = 0;

        for (Product product : productList) {
            float productPrice = isProductComplete ? product.getPrice() : findProductByID(product.getId()).getPrice();
            totalCharge += product.getQuantityPurchase() * productPrice;
        }

        return totalCharge;
    }
    public static Product findProductByID(int productID) {
        for(Product product : ProductRepository.getInstance(null).getProducts()) {
            if(product.getId() == productID) {
                return product;
            }
        }
        Log.i("Helper", String.valueOf(productID));
        return null;
    }
    public static String formatDate(String dateTime) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTime);

        return String.format("%d/%d/%d",
                offsetDateTime.getMonthValue(),
                offsetDateTime.getDayOfMonth(),
                offsetDateTime.getYear()
        );
    }

    public static void hideKeyboard(Context context, IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, 0);
    }

    public static boolean isValidPrice(String priceText) {
        return !priceText.isBlank() && !priceText.equals(".");
    }
}
