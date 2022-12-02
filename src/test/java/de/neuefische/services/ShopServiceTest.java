package de.neuefische.services;

import de.neuefische.pojos.Order;
import de.neuefische.pojos.Product;
import de.neuefische.repositories.OrderRepo;
import de.neuefische.repositories.ProductRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        Assertions.assertEquals(productRepo.get(0), actual);
    }

    @Test
    void getProduct_WithInvalidId_ThrowException() {
        // given
        ShopService shop = new ShopService(new ProductRepo(), new OrderRepo());

        try {
            Product actual = shop.getProduct(1);
            Assertions.fail();
        } catch (IndexOutOfBoundsException e) {
            Assertions.assertEquals("Produkt mit der ID nicht vorhanden.", e.getMessage());
        }
    }

    @Test
    void listAllProducts() {
        // given
        ProductRepo productRepo = createProductRepoWithMultipleItems();
        ShopService shop = new ShopService(productRepo, new OrderRepo());

        // when
        List<Product> actual = shop.listProducts();

        // then
        Assertions.assertEquals(productRepo.getProducts(), actual);
    }

    @Test
    void listAllProducts_NoProductsAvailable_ReturnNull() {
        // given
        ShopService shop = new ShopService(new ProductRepo(), new OrderRepo());

        // when
        List<Product> actual = shop.listProducts();

        // then
        assertNull(actual);
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
        Assertions.assertEquals(orderTest, actual);

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
        Assertions.assertEquals(orderTest, actual);
    }

    @Test
    void orderOneProduct_WithValidId_AndOtherOrdersExist() {
        // given
        ProductRepo productRepo = createProductRepoWithOneItem();
        OrderRepo orderRepo = createOrderRepoWithOneItem();

        ShopService shop = new ShopService(productRepo, orderRepo);

        Order orderTest = new Order(1, productRepo.getProducts());

        // when
        Order actual = shop.addOrder(productRepo.getProducts());

        // then
        Assertions.assertEquals(orderTest, actual);
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
        Assertions.assertEquals(orderTest, actual);
    }

    @Test
    void orderMultipleProducts_WithOneInvalidProduct_throwException() {
        // given
        ProductRepo productRepo = createProductRepoWithOneItem();

        Product cup = new Product(0, "Tasse");
        Product invalidProduct = new Product(3, "Teller");

        List<Product> orderList = new ArrayList<>();
        orderList.add(cup);
        orderList.add(invalidProduct);

        ShopService shop = new ShopService(productRepo, new OrderRepo());

        try {
            Order actual = shop.addOrder(orderList);
            Assertions.fail();
        } catch (IndexOutOfBoundsException e) {
            Assertions.assertEquals("Produkt Teller nicht im Shop vorhanden.", e.getMessage());
        }
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
        Product fork = new Product(0, "Gabel");
        Product cup = new Product(1, "Tasse");

        List<Product> products = new ArrayList<>();
        products.add(fork);
        products.add(cup);

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
        Product cup = new Product(0, "Tasse");

        List<Product> products = new ArrayList<>();
        products.add(cup);

        ProductRepo productRepo = new ProductRepo();
        productRepo.setProducts(products);
        return productRepo;
    }

    private OrderRepo createOrderRepoWithOneItem() {

        Product fork = new Product(0, "Gabel");
        List<Product> products = new ArrayList<>();
        products.add(fork);

        Order order = new Order(0, products);
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        OrderRepo orderRepo = new OrderRepo();
        orderRepo.setOrders(orders);

        return orderRepo;
    }

    private ProductRepo createProductRepoWithMultipleItems() {
        Product cup = new Product(0, "Tasse");
        Product fork = new Product(1, "Gabel");

        List<Product> products = new ArrayList<>();
        products.add(cup);
        products.add(fork);

        ProductRepo productRepo = new ProductRepo();
        productRepo.setProducts(products);
        return productRepo;
    }
}