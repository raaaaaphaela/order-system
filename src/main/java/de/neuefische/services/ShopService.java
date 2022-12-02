package de.neuefische.services;

import de.neuefische.pojos.Order;
import de.neuefische.pojos.Product;
import de.neuefische.repositories.OrderRepo;
import de.neuefische.repositories.ProductRepo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ShopService {

    private ProductRepo productRepo;
    private OrderRepo orderRepo;

    public Product getProduct(int id) {
        return productRepo.get(id);
    }

    public List<Product> listProducts() {
        return productRepo.list();
    }

    public Order addOrder(List<Product> products) {

        int newOrderId = createNewOrderID();

        List <Product> orderedProducts = new ArrayList<>();

        try {
            for (Product product : products) {
                orderedProducts.add(productRepo.get(product.getId()));

            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

        return new Order(newOrderId, orderedProducts);
    }

    public Order getOrder(int id) {
        return orderRepo.get(id);
    }

    public List<Order> listOrders() {
        return orderRepo.list();
    }

    private int createNewOrderID() {
        if (orderRepo.getOrders() == null) {
            return 0;
        } else {
            return orderRepo.getOrders().size();
        }
    }

}

