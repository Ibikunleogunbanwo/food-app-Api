package com.Afrochow.food_app.repository;

import com.Afrochow.food_app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    Optional<Product> findProductByProductName (String productName);

    Optional<Product> findProductByStoreId(String storeId);

//
//    // 1. Add pagination to limit results
//    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
//            "OR LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
//            "ORDER BY p.createdAt DESC")
//    List<Product> findProductsByKeyword(@Param("keyword") String keyword, Pageable pageable);
//
//    // 2. Add store filter to limit scope
//    @Query("SELECT p FROM Product p WHERE p.storeId = :storeId " +
//            "AND (LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
//            "OR LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :keyword, '%')))")
//    List<Product> findProductsByStoreAndKeyword(@Param("storeId") String storeId,
//                                                @Param("keyword") String keyword);
//
//    // 3. More efficient with native query (database-specific optimizations)
//    @Query(value = "SELECT * FROM products WHERE " +
//            "MATCH(product_name, product_description) AGAINST (:keyword IN NATURAL LANGUAGE MODE)",
//            nativeQuery = true)
//    List<Product> findProductsWithFullTextSearch(@Param("keyword") String keyword);
//
//    // 4. Limit results with TOP/LIMIT
//    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
//            "OR LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
//            "ORDER BY p.createdAt DESC")
//    List<Product> findTop100ProductsByKeyword(@Param("keyword") String keyword, Pageable pageable);
//
//    // 5. Add active/status filter if you have such field
//    @Query("SELECT p FROM Product p WHERE p.isActive = true " +
//            "AND (LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
//            "OR LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :keyword, '%')))")
//    List<Product> findActiveProductsByKeyword(@Param("keyword") String keyword);

}


