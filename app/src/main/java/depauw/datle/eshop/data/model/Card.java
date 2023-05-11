package depauw.datle.eshop.data.model;

import com.google.gson.annotations.SerializedName;

public class Card {
    @SerializedName("cardholderName")
    private String cardholderName;
    @SerializedName("cardNumber")
    private String cardNumber;
    @SerializedName("effDate")
    private String effDate;
    @SerializedName("cvc")
    private int cvc;

    public Card(String cardholderName, String cardNumber, String effDate, int cvc) {
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.effDate = effDate;
        this.cvc = cvc;
    }
}
