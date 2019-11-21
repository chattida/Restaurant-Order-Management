import java.util.HashMap;

public class Total {
    private static HashMap<String, Double> allprice = new HashMap<>();

    public static void addPrice(String key, Double value) {
        allprice.put(key, value);
    }

    public static void removePrice(String key) {
        allprice.remove(key);
    }

    public static Double getTotal() {
        Double totalPrice = 0.0;
        for (String key: allprice.keySet()) {
            totalPrice += allprice.get(key);
        }
        return totalPrice;
    }

    public static void reset() {
        allprice = new HashMap<>();
    }

    public static HashMap<String, Double> getAllprice() {
        return allprice;
    }
}
