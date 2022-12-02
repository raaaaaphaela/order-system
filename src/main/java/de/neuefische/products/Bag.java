package de.neuefische.products;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bag implements Product {

    private int id;
    private String name;
}
