package ru.clevertec.product.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.clevertec.product.util.TestConstant.UUID_MY;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.entity.Product.Fields;
import ru.clevertec.product.util.ProductTestData;

class InMemoryProductRepositoryTest {

    private InMemoryProductRepository productRepository;


    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
    }

    @Test
    void findByIdShouldReturnExpectedProductWithUUID() {
        // given (подготовка тестовых данных)
        UUID uuid = UUID.fromString(UUID_MY);
        Product expected = new Product(uuid, "Яблоко", "Описание яблока", BigDecimal.valueOf(1.01), LocalDateTime.MIN);

        // when (вызов метода)
        Product actual = productRepository.findById(uuid)
                .orElseThrow();
        // then (проверка результата)
        assertEquals(expected, actual);

        assertThat(actual)
                .hasFieldOrPropertyWithValue(Fields.uuid, expected.getUuid());
    }

    @Test
    void findByIdShouldReturnExpectedProductEqualsWithoutUUID() {
        // given
        UUID uuid = UUID.fromString(UUID_MY);
        Product expected = new Product(uuid, "Яблоко", "Описание яблока", BigDecimal.valueOf(1.01), LocalDateTime.MIN);

        // when
        Product actual = productRepository.findById(uuid)
                .orElseThrow();

        // then (вариант хуже читаемый)
        assertAll(
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getDescription(), actual.getDescription()),
                () -> assertEquals(expected.getPrice(), actual.getPrice()),
                () -> assertEquals(expected.getCreated(), actual.getCreated())
        );

        // then (вариант лучше читаемый (без ломбока))
        // Пример одного поля (под капотом)
        assertThat(actual)
                .extracting("uuid")
                .isEqualTo(uuid);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("uuid", expected.getUuid())
                .hasFieldOrPropertyWithValue("name", expected.getName())
                .hasFieldOrPropertyWithValue("description", expected.getDescription())
                .hasFieldOrPropertyWithValue("price", expected.getPrice());

        // then (вариант наилучший читаемый (с ломбоком))
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Fields.uuid, expected.getUuid())
                .hasFieldOrPropertyWithValue(Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Fields.price, expected.getPrice());
    }

    @Test
    void findByIdShouldReturnExpectedProductWithName() {
        // given
        UUID uuid = UUID.fromString(UUID_MY);
        Product expected = new Product(uuid, "Яблоко", "Описание яблока", BigDecimal.valueOf(1.01), LocalDateTime.MIN);

        // when
        Product actual = productRepository.findById(uuid)
                .orElseThrow();
        // then
        assertEquals(expected, actual);

        assertThat(actual)
                .hasFieldOrPropertyWithValue(Fields.uuid, expected.getUuid());
    }

    @Test
    void findByIdShouldReturnExpectedProductWithUUIDAssertEquals() {
        // given
        UUID uuid = UUID.fromString(UUID_MY);
        Product expected = new Product(uuid, "Яблоко", "Описание яблока", BigDecimal.valueOf(1.01), LocalDateTime.MIN);

        // when
        Product actual = productRepository.findById(uuid)
                .orElseThrow();
        // then
        assertEquals(expected.getUuid(), actual.getUuid());

    }

    @Test
    void findByIdShouldReturnExpectedProductWithUUIDAssertEqualsWithTestDataBuilder() {
        // given
        ProductTestData expected = ProductTestData.builder()
//                .withName("TestName")
                .build();
        // when
        Product actual = productRepository.findById(expected.getUuid())
                .orElseThrow();
        // then
        assertEquals(expected.getUuid(), actual.getUuid());

    }

    @Test
    @DisplayName("displayNameFindByIdShouldReturnOptionalEmpty")
    void findByIdShouldReturnOptionalEmpty() {
        // given
        UUID uuid = UUID.fromString("e8ff854a-e951-4c50-87c3-03a8657f6f28");
        Optional<Product> expected = Optional.empty();

        // when
        Optional<Product> actual = productRepository.findById(uuid);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnEmptyList() {
        // given

        // when
        List<Product> actual = productRepository.findAll();

        // then
        assertNull(actual);
    }

    @Test
    void save() {

    }

    @Test
    void delete() {

    }
}