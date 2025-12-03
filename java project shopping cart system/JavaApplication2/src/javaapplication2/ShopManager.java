package javaapplication2;
import java.util.*;

public class ShopManager {
    
    private HashMap<Integer, Product> inventory = new HashMap<>();

    public void addProduct(Product product) {
        int id = product.getId(); 
        inventory.put(id, product); 
    }

    public Product getProduct(int id) {
        return inventory.get(id); 
    }

    public ArrayList<Product> getAllProducts() {
        return new ArrayList<>(inventory.values());
    }
}
