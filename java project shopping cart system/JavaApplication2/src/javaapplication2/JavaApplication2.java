package javaapplication2;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JavaApplication2 {

    private static ShopManager shop = new ShopManager();
    private static Cart cart = new Cart();
    private static JTextArea outputArea;

    public static void main(String[] args) {

        // <-- Only initialize products the first time
        if (shop.getAllProducts().isEmpty()) {
            shop.addProduct(new Product(1, "laptop", 999.99, 5));
            shop.addProduct(new Product(2, "Phone", 599.49, 15));
            shop.addProduct(new Product(3, "Headphones", 99.99, 30));
            shop.addProduct(new Product(4, "mouse", 29.99, 20));
        }

        JFrame mainFrame = new JFrame("Shopping Cart");
        JLabel label1 = new JLabel("Welcome to my Shopping Cart", SwingConstants.CENTER);
        label1.setFont(new Font("Arial", Font.BOLD, 24));
        label1.setBounds(50, 10, 500, 40);

        JButton addButton = new JButton("Add to Cart");
        JButton viewButton = new JButton("View Cart");
        JButton removeButton = new JButton("Remove from Cart");
        JButton SaveCartItemsToFile = new JButton("Save Cart items to a file");
        JButton exitButton = new JButton("Exit");

        addButton.setBounds(170, 80, 240, 40);
        viewButton.setBounds(170, 130, 240, 40);
        removeButton.setBounds(170, 180, 240, 40);
        SaveCartItemsToFile.setBounds(170, 230, 240, 40);
        exitButton.setBounds(170, 280, 240, 40);

        viewButton.setFont(new Font("Arial", Font.BOLD, 17));
        addButton.setFont(new Font("Arial", Font.BOLD, 17));
        removeButton.setFont(new Font("Arial", Font.BOLD, 17));
        SaveCartItemsToFile.setFont(new Font("Arial", Font.BOLD, 17));
        exitButton.setFont(new Font("Arial", Font.BOLD, 17));

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setBounds(50, 340, 500, 100);

        mainFrame.setLayout(null);
        mainFrame.setSize(600, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.add(label1);
        mainFrame.add(addButton);
        mainFrame.add(viewButton);
        mainFrame.add(removeButton);
        mainFrame.add(SaveCartItemsToFile);
        mainFrame.add(exitButton);
        mainFrame.add(scroll);

        // <-- Hide main menu, show product window; do NOT dispose mainFrame
        addButton.addActionListener(e -> {
            mainFrame.setVisible(false);
            showProductWindow(mainFrame);
        });

        // Pass mainFrame so removeFromCart can hide/show it correctly
        removeButton.addActionListener(e -> removeFromCart(mainFrame));
        viewButton.addActionListener(e -> viewCart());
        SaveCartItemsToFile.addActionListener(e -> SaveCartItemsToFile());
        exitButton.addActionListener(e -> System.exit(0));

        mainFrame.setVisible(true);
    }

    // Accept the mainFrame so we can restore it when user returns
    private static void showProductWindow(JFrame mainFrame) {
        JFrame productFrame = new JFrame("Available Products");
        productFrame.setLayout(new BorderLayout());

        JPanel productsPanel = new JPanel(new GridLayout(0, 1,5,5));

        for (Product product : shop.getAllProducts()) {
            JPanel panel = new JPanel(new BorderLayout());
            JLabel infoLabel = new JLabel(product.toString(), SwingConstants.CENTER);
            infoLabel.setFont(new Font("Arial", Font.BOLD, 17));

            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JTextField quantityField = new JTextField("1", 5);
            JButton addBtn = new JButton("Add to Cart");
            bottomPanel.add(new JLabel("Quantity: "));
            bottomPanel.add(quantityField);
            bottomPanel.add(addBtn);

            // Add to cart and update product stock & label
            addBtn.addActionListener(e -> {
                try {
                    int quantity = Integer.parseInt(quantityField.getText());
                    if (quantity <= 0 || quantity > product.getStock()) {
                        JOptionPane.showMessageDialog(productFrame, "Invalid quantity");
                        return;
                    }
                    cart.addItem(product, quantity);
                    product.setStock(product.getStock() - quantity);
                    infoLabel.setText(product.toString()); // update displayed stock
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(productFrame, "Please enter a valid number.");
                }
            });

            panel.add(infoLabel, BorderLayout.CENTER);
            panel.add(bottomPanel, BorderLayout.SOUTH);
            productsPanel.add(panel);
        }

        JButton backButton = new JButton("Return to Main Menu");
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 18));

        // <-- Close productFrame and show the already-existing mainFrame
        backButton.addActionListener(e -> {
            productFrame.dispose();
            mainFrame.setVisible(true);
        });

        productFrame.add(new JScrollPane(productsPanel), BorderLayout.CENTER);
        productFrame.add(backButton, BorderLayout.SOUTH);
        productFrame.setSize(400, 600);
        productFrame.setLocationRelativeTo(null);
        productFrame.setVisible(true);
    }

    private static void viewCart() {
        if (cart.getItems().isEmpty()) {
            outputArea.setText("Cart is empty.");
            return;
        }

        StringBuilder message = new StringBuilder();
        for (CartItem item : cart.getItems()) {
            message.append(item.toString()).append("\n");
        }
        message.append(String.format("Total Price: $%.2f", cart.calculateTotal()));
        outputArea.setText(message.toString());
    }

    private static void removeFromCart(JFrame mainFrame) {
        if (cart.getItems().isEmpty()) {
            // Cart empty: show message and keep main menu visible
            JOptionPane.showMessageDialog(mainFrame, "Cart is empty. Nothing to remove.");
            mainFrame.setVisible(true);
            return;
        }

        // Hide main menu when remove window opens
        mainFrame.setVisible(false);

        JFrame removeFrame = new JFrame("Manage Cart");
        removeFrame.setLayout(new BorderLayout());

        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS)); // vertical list

        for (CartItem item : new ArrayList<>(cart.getItems())) {
            // Use GridBagLayout for clean alignment
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;

            // Product info
            JLabel label = new JLabel(item.getProduct().getName() +
                    " (Stock: " + item.getProduct().getStock() +
                    ") - Quantity in cart: " + item.getQuantity());
            label.setFont(new Font("Arial", Font.BOLD, 14));

            // Quantity field
            JTextField quantityField = new JTextField(String.valueOf(item.getQuantity()), 5);

            // Buttons
            JButton updateBtn = new JButton("Update");
            JButton removeBtn = new JButton("Remove");

            // ==== Update button ====
            updateBtn.addActionListener(e -> {
                try {
                    int newQty = Integer.parseInt(quantityField.getText());
                    if (newQty <= 0) {
                        JOptionPane.showMessageDialog(removeFrame, "Quantity must be greater than 0.");
                        return;
                    }
                    if (newQty > item.getProduct().getStock() + item.getQuantity()) {
                        JOptionPane.showMessageDialog(removeFrame, "Not enough stock available.");
                        return;
                    }
                    // restore stock first
                    item.getProduct().setStock(item.getProduct().getStock() + item.getQuantity());
                    item.setQuantity(newQty);
                    item.getProduct().setStock(item.getProduct().getStock() - newQty);
                    label.setText(item.getProduct().getName() +
                            " (Stock: " + item.getProduct().getStock() +
                            ") - Quantity in cart: " + item.getQuantity());
                    JOptionPane.showMessageDialog(removeFrame, "Quantity updated!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(removeFrame, "Please enter a valid number.");
                }
            });

            // ==== Remove button ====
            removeBtn.addActionListener(e -> {
                item.getProduct().setStock(item.getProduct().getStock() + item.getQuantity());
                cart.getItems().remove(item);
                itemsPanel.remove(panel);
                itemsPanel.revalidate();
                itemsPanel.repaint();
                if (cart.getItems().isEmpty()) {
                    JOptionPane.showMessageDialog(removeFrame, "Cart is now empty.");
                    removeFrame.dispose();
                    mainFrame.setVisible(true);
                }
            });

            // ==== Layout row ====
            gbc.gridx = 0;
            gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(label, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            panel.add(new JLabel("Qty:"), gbc);

            gbc.gridx = 2;
            panel.add(quantityField, gbc);

            gbc.gridx = 3;
            panel.add(updateBtn, gbc);

            gbc.gridx = 4;
            panel.add(removeBtn, gbc);

            itemsPanel.add(panel);
        }

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton removeAllBtn = new JButton("Remove All");
        JButton backBtn = new JButton("Return to Main Menu");

        removeAllBtn.addActionListener(e -> {
            for (CartItem item : cart.getItems()) {
                item.getProduct().setStock(item.getProduct().getStock() + item.getQuantity());
            }
            cart.getItems().clear();
            itemsPanel.removeAll();
            itemsPanel.revalidate();
            itemsPanel.repaint();
            JOptionPane.showMessageDialog(removeFrame, "All items removed from the cart.");
            removeFrame.dispose();
            mainFrame.setVisible(true);
        });

        backBtn.addActionListener(e -> {
            removeFrame.dispose();
            mainFrame.setVisible(true);
        });

        bottomPanel.add(removeAllBtn);
        bottomPanel.add(backBtn);

        removeFrame.add(new JScrollPane(itemsPanel), BorderLayout.CENTER);
        removeFrame.add(bottomPanel, BorderLayout.SOUTH);
        removeFrame.setSize(750, 400);
        removeFrame.setLocationRelativeTo(null);
        removeFrame.setVisible(true);
    }

    private static void SaveCartItemsToFile() {
        if (cart.getItems().isEmpty()) {
            outputArea.setText("Cart is empty. Nothing to save in the file.");
            return;
        }
        try {
            cart.saveToFile("cart.txt");
            outputArea.setText("Cart saved to cart.txt");
        } catch (IOException e) {
            outputArea.setText("Error saving file.");
        }
    }
}