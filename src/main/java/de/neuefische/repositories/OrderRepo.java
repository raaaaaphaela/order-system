package de.neuefische.repositories;

import de.neuefische.pojos.Order;
import de.neuefische.pojos.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRepo {

    private List<Order> orders;

    public List<Order> list() {
        return orders;
    }

    public Order get(int id) {
        if (orders != null) {
            return orders.get(id);
        } else {
            return null;
        }
    }

    public Order add(List<Product> orderedProducts) {
        if (orders == null) {
            orders = new ArrayList<>();
        }

        int id = orders.size();
        return new Order(id, orderedProducts);
    }
}
