package ru.clevertec.product.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;

public class InMemoryProductRepository implements ProductRepository {

//    private final Map<UUID, Product> productMap = Map.of(
//            UUID.fromString(TestConst), new Product(UUID.fromString(UUID_MY), "Яблоко", "Описание яблока", BigDecimal.valueOf(1.01), LocalDateTime.MIN)
//    );


    @Override
    public Optional<Product> findById(UUID uuid) {
//        return Optional.ofNullable(productMap.get(uuid));
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }
}
