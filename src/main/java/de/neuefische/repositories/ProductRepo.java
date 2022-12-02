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
        if (products != null) {
            Product product = products.get(id);
            System.out.println(product);
            return product;
        } else {
            throw new IndexOutOfBoundsException("Produkt mit der ID nicht vorhanden.");
        }
    }
}
