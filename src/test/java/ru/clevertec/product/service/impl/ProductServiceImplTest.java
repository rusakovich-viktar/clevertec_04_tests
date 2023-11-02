package ru.clevertec.product.service.impl;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.product.util.TestConstant.CORRECT_UUID;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.entity.Product.Fields;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.util.ProductTestData;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductMapper mapper;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Captor
    private ArgumentCaptor<Product> productCaptor;


//    @BeforeEach
//    void setUp() {
//        productRepository = Mockito.mock(ProductRepository.class);
//        mapper = Mockito.mock(ProductMapper.class);
//
//        productService = new ProductServiceImpl(mapper, productRepository);
//    }

    @Test
    void delete() {
        // given
        UUID uuid = CORRECT_UUID;
        // when
        productService.delete(uuid);
        // then
        verify(productRepository)
                .delete(uuid);
    }

    @Test
    void get() {
        UUID uuid = CORRECT_UUID;
        InfoProductDto expected = ProductTestData.builder().build().buildInfoProductDto();
        Product product = ProductTestData.builder().build()
                .buildProduct();
        Optional<Product> productOptional = Optional.of(product);

        when(productRepository.findById(uuid))
                .thenReturn(productOptional);
        when(mapper.toInfoProductDto(productOptional.get()))
                .thenReturn(expected);

        InfoProductDto actual = productService.get(uuid);

        Assertions.assertThat(actual)
                .hasFieldOrPropertyWithValue(Fields.name, expected.name())
                .hasFieldOrPropertyWithValue(Fields.uuid, expected.uuid())
                .hasFieldOrPropertyWithValue(Fields.description, expected.description())
                .hasFieldOrPropertyWithValue(Fields.price, expected.price());
    }

    @Test
    void getAll() {
        InfoProductDto infoProductDto = ProductTestData.builder().build().buildInfoProductDto();
        Product product = ProductTestData.builder().build().buildProduct();

        List<InfoProductDto> expected = List.of(
                infoProductDto
        );
        List<Product> products = List.of(
                product
        );

        when(productRepository.findAll()).thenReturn(products);
        when(mapper.toInfoProductDto(product)).thenReturn(infoProductDto);

        List<InfoProductDto> actual = productService.getAll();

        Assertions.assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void create() {
        ProductDto productDto = ProductTestData.builder().build().buildProductDto();
        Product product = ProductTestData.builder()
                .withCreated(null)
                .withUuid(null).build()
                .buildProduct();
        Product createdProduct = ProductTestData.builder()
                .build()
                .buildProduct();
        UUID expected = createdProduct.getUuid();

        when(mapper.toProduct(productDto))
                .thenReturn(product);
        when(productRepository.save(product))
                .thenReturn(createdProduct);

        UUID actual = productService.create(productDto);

        org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        Assertions.assertThat(actual)
                .isEqualTo(expected);

    }

    @Test
    void createShouldInvokeRepository_withoutProductUuid() {
        ProductDto productDto = ProductTestData.builder().build().buildProductDto();

        Product expected = ProductTestData.builder()
                .withUuid(null).build()
                .buildProduct();
        Product productToSave = ProductTestData.builder()
                .withUuid(null).build()
                .buildProduct();
        ProductTestData.builder()
                .withUuid(null).build()
                .buildProductDto();
        UUID uuid = CORRECT_UUID;


        doReturn(expected)
                .when(productRepository).save(productToSave);
        when(mapper.toProduct(productDto)).thenReturn(productToSave);

        productService.create(productDto);

        verify(productRepository).save(productCaptor.capture());
        Assertions.assertThat(productCaptor.getValue()).hasFieldOrPropertyWithValue(Fields.uuid, null);
    }

    @Test
    void update() {
        UUID uuid = CORRECT_UUID;
        ProductDto productDto = ProductTestData.builder()
                .withPrice(BigDecimal.valueOf(200)).build()
                .buildProductDto();
        Product product = ProductTestData.builder()
                .withUuid(uuid).build()
                .buildProduct();
        Product expectedProduct = ProductTestData.builder()
                .withUuid(uuid)
                .withPrice(BigDecimal.valueOf(200)).build()
                .buildProduct();
        Optional<Product> productOptional = Optional.of(product);

        when(productRepository.findById(uuid))
                .thenReturn(productOptional);
        when(mapper.merge(productOptional.get(), productDto))
                .thenReturn(product);
        when(productRepository.save(product))
                .thenReturn(expectedProduct);

        productService.update(uuid, productDto);

    }

}