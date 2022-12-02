package de.neuefische.repositories;

import de.neuefische.pojos.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductRepo {

    private List<Product> products;

    public List<Product> list() {
        return products;
    }

    public Product get(int id) {
        try {
            if (products != null) {
                Product product = products.get(id);
                System.out.println(product);
                return product;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Produkt mit der ID: " + id + " nicht vorhanden. Die ID ist zu groß.");
            return null;
        }
        return null;
    }
}
