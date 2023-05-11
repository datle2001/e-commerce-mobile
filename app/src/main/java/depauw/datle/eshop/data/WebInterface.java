package depauw.datle.eshop.data;

import org.json.JSONObject;

import java.util.List;

import depauw.datle.eshop.data.model.Card;
import depauw.datle.eshop.data.model.LoggedInUser;
import depauw.datle.eshop.data.model.Order;
import depauw.datle.eshop.data.model.Product;
import depauw.datle.eshop.data.model.Session;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebInterface {
    @PATCH("users/{id}")
    Call<JSONObject> updateUser(@Header("Authorization") String token, @Path("id") int id, @Body LoggedInUser user);
    @POST("login")
    Call<Session> login(@Body LoggedInUser user);
    @POST("loginWithToken")
    Call<Session> loginWithToken(@Header("Authorization") String token);
    @POST("register")
    Call<Session> register(@Body LoggedInUser user);
    @POST("logout")
    Call<JSONObject> logout(@Header("Authorization") String token);
    @GET("products")
    Call<List<Product>> getProducts();
    @POST("card")
    Call<Card> addCard(@Header("Authorization") String token, @Body Card card);
    @POST("checkout")
    Call<JSONObject> checkout(@Header("Authorization") String token, @Body List<Product> checkoutList);
    @GET("orders")
    Call<List<Order>> getOrders(@Header("Authorization") String token);
    @GET("orders/{id}")
    Call<List<Product>> getOrderProductList(@Header("Authorization") String token, @Path("id") int orderID);
}
