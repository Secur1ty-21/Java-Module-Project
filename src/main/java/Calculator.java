import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private float totalAmount;
    // Не Map с сохранением цены т.к. в сохранении цены кажого товора нет необходимости по заданию.
    private final List<String> products;

    /**
     * Добавление товара в калькулятор.
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
