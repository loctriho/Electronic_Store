package com.triloc.webapp.electonicstore.controller;

import com.triloc.webapp.electonicstore.dto.ProductDTO;
import com.triloc.webapp.electonicstore.repository.ProductRepository;
import com.triloc.webapp.electonicstore.service.ProductSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductSearchService productSearchService;

    // ... (other test methods)

    @BeforeEach
    public void setup() throws InterruptedException {
        // Define the behavior of the mocked search service
        List<ProductDTO> mockProductDTOs = new ArrayList<>();
        mockProductDTOs.add(new ProductDTO(1L, "Test Product 1", "Description 1", 9.99, "image1.jpg", "Electronics", "Test Manufacturer 1"));
        mockProductDTOs.add(new ProductDTO(2L, "Test Product 2", "Description 2", 19.99, "image2.jpg", "Electronics", "Test Manufacturer 2"));
        // Add more mock ProductDTOs as needed for your test cases

        when(productSearchService.search(anyString())).thenReturn(mockProductDTOs);
    }

        @Test
    public void testGetProductsWhenCategoryIsProvidedThenReturnProducts() throws Exception {
        // Arrange
        List<ProductDTO> products = Collections.singletonList(new ProductDTO(1L, "Test Product", "Description", 9.99, "image.jpg", "Electronics", "Test Manufacturer"));
        Page<ProductDTO> productPage = new PageImpl<>(products);
        when(productRepository.getProductsByCategoryName(eq("Electronics"), any(PageRequest.class))).thenReturn(productPage);

        // Act & Assert
        mockMvc.perform(get("/products")
                        .param("category", "Electronics")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].productName").value("Test Product"));
    }

    @Test
    public void testGetProductsWhenNoCategoryIsProvidedThenReturnProducts() throws Exception {
        // Arrange
        List<ProductDTO> products = Collections.singletonList(new ProductDTO(1L, "Test Product", "Description", 9.99, "image.jpg", "Electronics", "Test Manufacturer"));
        Page<ProductDTO> productPage = new PageImpl<>(products);
        when(productRepository.getAllProducts(any(PageRequest.class))).thenReturn(productPage);

        // Act & Assert
        mockMvc.perform(get("/products")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].productName").value("Test Product"));
    }

    @Test
    public void testGetTotalCountByCategory() throws Exception {
        // Arrange
        when(productRepository.countByCategoryName("someCategory")).thenReturn(5L);

        // Act & Assert
        mockMvc.perform(get("/products/someCategory/count"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalCount").value(5));
    }

    @Test
    public void testSearchProducts() throws Exception {
        // Arrange
        List<ProductDTO> products = Collections.singletonList(new ProductDTO(1L, "Test Product", "Description", 9.99, "image.jpg", "Electronics", "Test Manufacturer"));
        when(productSearchService.search("someQuery")).thenReturn(products);

        // Act & Assert
        mockMvc.perform(get("/products/search")
                        .param("query", "someQuery"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].productName").value("Test Product"));
        verify(productSearchService).search("someQuery");

    }

}
