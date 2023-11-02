package ru.clevertec.product.mapper.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.entity.Product.Fields;
import ru.clevertec.product.util.ProductTestData;

@ExtendWith(MockitoExtension.class)
class ProductMapperImplTest {

    @InjectMocks
    private ProductMapperImpl productMapperImpl;

    @Nested
    class ToProductTest {

        @Test
        void toProductShouldReturnProduct_whenSentCorrectProductDto() {
            // given
            Product expected = ProductTestData.builder()
                    .withUuid(null)
                    .withCreated(null).build()
                    .buildProduct();
            ProductDto productDto = ProductTestData.builder().build()
                    .buildProductDto();

            // when
            Product actual = productMapperImpl.toProduct(productDto);

            // then
            Assertions.assertThat(actual)
                    .hasFieldOrPropertyWithValue(Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Fields.price, expected.getPrice());
        }

        @Test
        void toProductShouldReturnNull_whenSentNull() {
            // given
            ProductDto productDto = null;

            // when
            Product actual = productMapperImpl.toProduct(productDto);

            // then
            Assertions.assertThat(actual)
                    .isNull();

        }

        @Test
        void toProductShouldReturnProductWithNullField_whenSentProductDtoWithNullField() {
            // given
            Product expected = ProductTestData.builder()
                    .withUuid(null)
                    .withCreated(null)
                    .withName(null).build()
                    .buildProduct();

            ProductDto productDto = ProductTestData.builder()
                    .withName(null).build()
                    .buildProductDto();

            // when
            Product actual = productMapperImpl.toProduct(productDto);

            // then
            Assertions.assertThat(actual)
                    .hasFieldOrPropertyWithValue(Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Fields.price, expected.getPrice());
        }

    }

    @Nested
    class ToInfoProductTest {


        @Test
        void toInfoProductDtoShouldReturnInfoProductDto_whenSentCorrectProduct() {
            // given
            InfoProductDto expected = ProductTestData.builder().build()
                    .buildInfoProductDto();

            Product product = ProductTestData.builder().build()
                    .buildProduct();

            // when
            InfoProductDto actual = productMapperImpl.toInfoProductDto(product);

            // then
            Assertions.assertThat(actual)
                    .hasFieldOrPropertyWithValue(Fields.uuid, expected.uuid())
                    .hasFieldOrPropertyWithValue(Fields.name, expected.name())
                    .hasFieldOrPropertyWithValue(Fields.description, expected.description())
                    .hasFieldOrPropertyWithValue(Fields.price, expected.price());
        }

        @Test
        void toInfoProductDtoShouldReturnNull_whenSentNull() {
            // given
            Product product = null;

            // when
            InfoProductDto actual = productMapperImpl.toInfoProductDto(product);

            // then
            Assertions.assertThat(actual)
                    .isNull();

        }

        @Test
        void toInfoProductDtoShouldReturnInfoProductDtoWithNullField_whenSentProductWithNullField() {
            // given
            Product product = ProductTestData.builder()
                    .withName(null).build()
                    .buildProduct();

            InfoProductDto expected = ProductTestData.builder()
                    .withName(null).build()
                    .buildInfoProductDto();

            // when
            InfoProductDto actual = productMapperImpl.toInfoProductDto(product);

            // then
            Assertions.assertThat(actual)
                    .hasFieldOrPropertyWithValue(Fields.name, expected.name())
                    .hasFieldOrPropertyWithValue(Fields.uuid, expected.uuid())
                    .hasFieldOrPropertyWithValue(Fields.description, expected.description())
                    .hasFieldOrPropertyWithValue(Fields.price, expected.price());
        }

    }

    @Test
    void merge() {
        // given
        Product product = ProductTestData.builder().withName("Яблоко").build()
                .buildProduct();
        ProductDto productDto = ProductTestData.builder().withName("Груша").build()
                .buildProductDto();
        Product expected = ProductTestData.builder().withName("Груша").build().buildProduct();

        // when
        Product actual = productMapperImpl.merge(product, productDto);

        // then
        Assertions.assertThat(actual)
                .hasFieldOrPropertyWithValue(Fields.name, expected.getName());

    }
}