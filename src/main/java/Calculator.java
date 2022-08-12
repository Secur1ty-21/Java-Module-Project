import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private float totalAmount;
    private final List<String> products;

    /**
     * Добавление товаров в калькулятор.
     *
     * @param name  имя товара.
     * @param price цена товара.
     */
    public void addProduct(String name, double price) {
        totalAmount += price;
        products.add(name);
    }

    public List<String> getProducts() {
        return products;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    Calculator() {
        totalAmount = 0;
        products = new ArrayList<>();
    }
}
