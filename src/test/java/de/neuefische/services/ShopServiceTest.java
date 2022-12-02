package de.neuefische.services;

import de.neuefische.products.Bag;
import de.neuefische.products.Order;
import de.neuefische.products.Product;
import de.neuefische.products.Shoe;
import de.neuefische.repositories.OrderRepo;
import de.neuefische.repositories.ProductRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class ShopServiceTest {

    @Test
    void getProduct_WithValidId_ReturnProduct() {
        // given
        ProductRepo productRepo = createProductRepoWithOneItem();

        ShopService shop = new ShopService(productRepo, new OrderRepo());

        //when
        Product actual = shop.getProduct(0);

        // then
        assertThat(productRepo.get(0))
                .as("Check if product with valid id is returned")
                .isEqualTo(actual);
    }

    @Test
    void getProduct_WithInvalidId_ThrowException() {
        // given
        ShopService shop = new ShopService(new ProductRepo(), new OrderRepo());

        assertThatThrownBy(() -> {
            Product actual = shop.getProduct(1);
        }).isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Produkt mit der ID nicht vorhanden.");
    }

    @Test
    void listAllProducts() {
        // given
        ProductRepo productRepo = createProductRepoWithMultipleItems();
        ShopService shop = new ShopService(productRepo, new OrderRepo());

        // when
        List<Product> actual = shop.listProducts();

        // then
        assertThat(productRepo.getProducts()).isEqualTo(actual);
    }

    @Test
    void listAllProducts_NoProductsAvailable_ReturnNull() {
        // given
        ShopService shop = new ShopService(new ProductRepo(), new OrderRepo());

        // when
        List<Product> actual = shop.listProducts();

        // then
        assertThat(actual).isNull();
    }

    @Test
    void orderOneProduct_WithValidId() {
        // given
        ProductRepo productRepo = createProductRepoWithOneItem();

        ShopService shop = new ShopService(productRepo, new OrderRepo());

        Order orderTest = new Order(0, productRepo.getProducts());

        // when
        Order actual = shop.addOrder(productRepo.getProducts());

        // then
        assertThat(orderTest).isEqualTo(actual);
    }

    @Test
    void orderOneProduct_WithInvalidId() {
        // given
        ProductRepo productRepo = createProductRepoWithOneItem();

        ShopService shop = new ShopService(productRepo, new OrderRepo());

        Order orderTest = new Order(0, productRepo.getProducts());

        // when
        Order actual = shop.addOrder(productRepo.getProducts());

        // then
        assertThat(orderTest).isEqualTo(actual);
    }

    @Test
    void orderOneProduct_WithValidProductId_AndOtherOrdersExist() {
        // given
        ProductRepo productRepo = createProductRepoWithOneItem();
        OrderRepo orderRepo = createOrderRepoWithOneItem();

        ShopService shop = new ShopService(productRepo, orderRepo);

        Order orderTest = new Order(1, productRepo.getProducts());

        // when
        Order actual = shop.addOrder(productRepo.getProducts());

        // then
        assertThat(orderTest).isEqualTo(actual);
    }

    @Test
    void orderMultipleProducts_WithValidIds() {
        // given
        ProductRepo productRepo = createProductRepoWithMultipleItems();

        ShopService shop = new ShopService(productRepo, new OrderRepo());

        Order orderTest = new Order(0, productRepo.getProducts());

        // when
        Order actual = shop.addOrder(productRepo.getProducts());

        // then
        assertThat(orderTest).isEqualTo(actual);
    }

    @Test
    void orderMultipleProducts_WithOneInvalidProduct_throwException() {
        // given
        ProductRepo productRepo = createProductRepoWithOneItem();

        Product superstar = new Shoe(0, "Superstar");
        Product backpack = new Bag(3, "Rucksack");

        List<Product> orderList = new ArrayList<>();
        orderList.add(superstar);
        orderList.add(backpack);

        ShopService shop = new ShopService(productRepo, new OrderRepo());

        Throwable thrown = catchThrowable(() -> {
            Order actual = shop.addOrder(orderList);
        });

        assertThat(thrown)
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Produkt Rucksack nicht im Shop vorhanden.");
    }

    @Test
    void getOrderWithValidId_returnOrder() {
        // given
        OrderRepo orderRepo = createOrderRepoWithOneItem();

        ShopService shop = new ShopService(new ProductRepo(), orderRepo);

        // when
        Order actual = shop.getOrder(0);

        // then
        Assertions.assertEquals(orderRepo.get(0), actual);
    }

    @Test
    void getOrderWithInvalidId_returnNull() {
        // given
        ShopService shop = new ShopService(new ProductRepo(), new OrderRepo());

        // when
        Order actual = shop.getOrder(1);

        // then
        assertNull(actual);
    }

    @Test
    void listOrders_RepoHasOrders() {
        // given
        Product superstar = new Shoe(0, "Superstar");
        Product backpack = new Bag(1, "Rucksack");

        List<Product> products = new ArrayList<>();
        products.add(superstar);
        products.add(backpack);

        Order order1 = new Order(0, products);
        Order order2 = new Order(1, products);

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        OrderRepo orderRepo = new OrderRepo();
        orderRepo.setOrders(orders);

        ShopService shop = new ShopService(new ProductRepo(), orderRepo);

        // when
        List<Order> actual = shop.listOrders();

        // then
        Assertions.assertEquals(orders, actual);
    }

    @Test
    void listOrders_RepoHasNoOrders_ReturnNull() {
        // given
        ShopService shop = new ShopService(new ProductRepo(), new OrderRepo());

        // when
        List<Order> actual = shop.listOrders();

        // then
        assertNull(actual);
    }

    // helper
    private ProductRepo createProductRepoWithOneItem() {
        Product superstar = new Shoe(0, "Superstar");

        List<Product> products = new ArrayList<>();
        products.add(superstar);

        ProductRepo productRepo = new ProductRepo();
        productRepo.setProducts(products);
        return productRepo;
    }

    private OrderRepo createOrderRepoWithOneItem() {

        Product superstar = new Shoe(0, "Superstar");
        List<Product> products = new ArrayList<>();
        products.add(superstar);

        Order order = new Order(0, products);
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        OrderRepo orderRepo = new OrderRepo();
        orderRepo.setOrders(orders);

        return orderRepo;
    }

    private ProductRepo createProductRepoWithMultipleItems() {
        Product superstar = new Shoe(0, "Superstar");
        Product chucks = new Bag(1, "Rucksack");

        List<Product> products = new ArrayList<>();
        products.add(superstar);
        products.add(chucks);

        ProductRepo productRepo = new ProductRepo();
        productRepo.setProducts(products);
        return productRepo;
    }
}