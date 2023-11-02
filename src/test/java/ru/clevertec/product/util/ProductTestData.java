package ru.clevertec.product.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;

@Data
@Builder(setterPrefix = "with")
//@Builder(setterPrefix = "with", toBuilder = true) //тут можем допилить своими значениями билдер на основе другого билдера
//@Accessors(chain = true) //цепочка из сеттеров и каждый раз возвращается сам объект
public class ProductTestData {
    @Builder.Default
    private UUID uuid = UUID.fromString(TestConstant.UUID_MY);

    @Builder.Default
    private String name = "Яблоко";

    @Builder.Default
    private String description = "Описание яблока";

    @Builder.Default
    private BigDecimal price = BigDecimal.valueOf(1.01);

    @Builder.Default
    private LocalDateTime created = LocalDateTime.of(2023, Month.OCTOBER, 30, 22, 33);

    public ProductDto buildProductDto() {
        return new ProductDto(name, description, price);
    }

    public Product buildProduct() {
        return new Product(uuid, name, description, price, created);
    }

    public InfoProductDto buildInfoProductDto() {
        return new InfoProductDto(uuid, name, description, price);
    }
}
