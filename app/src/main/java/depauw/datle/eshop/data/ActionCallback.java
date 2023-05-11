package depauw.datle.eshop.data;

public interface ActionCallback {
    void onActionSuccess(Result.Success success);
    void onActionFailure(Result.Error error);
}
