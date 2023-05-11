package depauw.datle.eshop.ui.mainActivity.shopping.filter;

public class Filters {
    private static Filters filters;
    private String keyword;
    public boolean isPriceMaxInvalid() {
        return priceMin > priceMax;
    }

    private Filters() {
        this.keyword = "";
        this.priceMin = 0;
        this.priceMax = Float.MAX_VALUE;
    }

    public static Filters getInstance() {
        if(filters == null) {
            filters = new Filters();
        }

        return filters;
    }

    private float priceMin;
    private float priceMax;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public float getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(float priceMin) {
        this.priceMin = priceMin;
    }

    public float getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(float priceMax) {
        this.priceMax = priceMax;
    }
}
