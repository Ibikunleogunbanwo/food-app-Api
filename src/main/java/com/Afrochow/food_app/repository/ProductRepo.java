package com.Afrochow.food_app.repository;

import com.Afrochow.food_app.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    Optional<Product> findProductByProductName(String productName);
    Optional<Product> findProductByProductCode(String productCode);


    // Find all products available in a store by storeCode via the join entity StoreProduct
    @Query("SELECT sp.product FROM StoreProduct sp WHERE sp.store.storeCode = :storeCode")
    List<Product> findAllByStoreId(@Param("storeCode") String storeId);

    // Search products by keyword in name or description, with pagination support
    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "ORDER BY p.createdAt DESC")

    List<Product> findProductsByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // Search products by storeCode and keyword in name or description
    @Query("SELECT sp.product FROM StoreProduct sp WHERE sp.store.storeCode = :storeCode " +
            "AND (LOWER(sp.product.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(sp.product.productDescription) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Product> findProductsByStoreAndKeyword(@Param("storeCode") String storeId,
                                                @Param("keyword") String keyword);

    // Optional: If your DB supports full text search, this is an example native query
    @Query(value = "SELECT * FROM products WHERE MATCH(product_name, product_description) AGAINST (:keyword IN NATURAL LANGUAGE MODE)",
            nativeQuery = true)
    List<Product> findProductsWithFullTextSearch(@Param("keyword") String keyword);

    // Find top N products by keyword, paginated by Pageable
    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "ORDER BY p.createdAt DESC")

    List<Product> findTopProductsByKeyword(@Param("keyword") String keyword, Pageable pageable);

}