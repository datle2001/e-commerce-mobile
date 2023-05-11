package depauw.datle.eshop.data.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {
    @SerializedName("id")
    private final int id;
    @SerializedName("name")
    private final String name;
    @SerializedName("description")
    private final String description;
    @SerializedName("price")
    private final float price;
    @SerializedName("quantity_available")
    private final int quantityAvailable;
    @SerializedName("quantity_purchase")
    private int quantityPurchase;
    @SerializedName("photo")
    private final String photo;
    @SerializedName("decodedString")
    private byte[] decodedString;

    public Product(int id, String name, String description, float price, int quantityAvailable, int quantityPurchase, String photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.quantityPurchase = quantityPurchase;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }
    public float getPrice() {
        return price;
    }

    public Bitmap getPhoto() {
        if (decodedString == null) {
            try {
                decodedString = Base64.decode(photo, Base64.DEFAULT);
            } catch (Exception e) {
                return null;
            }
        }

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public int getQuantityPurchase() {
        return quantityPurchase;
    }

    public void setQuantityPurchase(int quantityPurchase) {
        this.quantityPurchase = quantityPurchase;
    }

    public Product duplicate() {
        return new Product(id, name, description, price, quantityAvailable, quantityPurchase, photo);
    }
}
