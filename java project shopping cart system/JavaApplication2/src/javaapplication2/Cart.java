package javaapplication2;
import java.util.ArrayList;
import java.io.*;

public class Cart {
    private ArrayList<CartItem> items = new ArrayList<>();

    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
        if (item.getProduct().getId() == product.getId()) {  
            //If product already exists, just increase quantity
            item.setQuantity(item.getQuantity() + quantity);
            return;
        }
    }
    //If product not found, add new CartItem
    items.add(new CartItem(product, quantity));
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (CartItem item : items) {
                writer.write(item.toString());
                writer.newLine();
            }
            writer.write("Total Price: $" + String.format("%.2f", calculateTotal()));
        }
    }
}
