package depauw.datle.eshop.data;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class Result<T> {
    private Result() {}

    @Override
    public String toString() {
        if (this instanceof Result.Success) {
            Result.Success success = (Result.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof Result.Error) {
            Result.Error error = (Result.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success sub-class
    public final static class Success<T> extends Result {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public final static class Error extends Result {
        // ERROR CODES
        public static final int ERROR_GET_ORDER_PRODUCT_LIST= 5;
        public static final int ERROR_GET_ORDERS= 4;
        public static final int ERROR_GET_PRODUCTS = 3;
        public static final int ERROR_NO_CARD = 1;
        public static final int ERROR_LOG_OUT_FAIL = 2;

        // FIELD PARAMETERS
        private Exception error;
        private int errorCode;


        public Error(Exception error) {
            this.error = error;
        }
        public Error(int errorCode) {this.errorCode = errorCode;}

        public Exception getError() {
            return this.error;
        }
        public int getErrorCode() {return errorCode;}
    }
}