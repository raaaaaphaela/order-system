package de.neuefische.products;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Shoe implements Product {

    private int id;
    private String name;
}
