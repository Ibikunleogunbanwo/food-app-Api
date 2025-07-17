package com.Afrochow.food_app.repository;

import com.Afrochow.food_app.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepo extends JpaRepository<Store, Long> {

    Optional<Store> findByStorePhoneNumber(String storePhoneNumber);

//    Optional<Store> findByStoreId(String storeId);

    Optional<Store> findByStoreCode(String storeCode);

    @Query("select s.storeCode from Store s WHERE s.storeCode = :storeCode")
    Optional<Store> findByStoreCodeOnly(@Param("storeCode") String storeCode);

    List<Store> findStoreByStoreNameIgnoreCase(String storeName);

    @Query("SELECT s FROM Store s ORDER BY s.storeCode DESC")
    List<Store> findAllByOrderByProductCodeDesc();

    List<Store> findAllByStoreNameContainingIgnoreCase(String storeName);

    List<Store> findAllByStoreCategoryContainingIgnoreCase(String storeCategory);

    List<Store> findByStoreCategoryContainingIgnoreCaseAndStoreNameContainingIgnoreCase(String storeCategory, String storeName);

    // Corrected searchByKeyword to reference address fields properly
    @Query("SELECT s FROM Store s WHERE " +
            "LOWER(s.storeCategory) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.storeName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.storeDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.address.province) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Store> searchByKeyword(@Param("keyword") String keyword);

    // Corrected searchByLocation to reference address fields properly
    @Query("SELECT s FROM Store s WHERE " +
            "LOWER(s.address.city) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.address.postalCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.address.streetAddress) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Store> searchByLocation(@Param("keyword") String keyword);

    // Method using property navigation on address field in method name (already correct)
    List<Store> findAllByAddress_CityContainingIgnoreCase(String city);

    @Query("SELECT s FROM Store s LEFT JOIN FETCH s.availableProducts WHERE s.storeCode = :storeCode")
    Optional<Store> findByStoreCodeWithProducts(@Param("storeCode") String storeCode);


    // With products and product details eager loaded
    @Query("SELECT s FROM Store s " +
            "LEFT JOIN FETCH s.availableProducts ap " +
            "LEFT JOIN FETCH ap.product " +
            "WHERE s.storeCode = :storeCode")
    Optional<Store> findByStoreCodeWithProductsAndDetails(@Param("storeCode") String storeCode);


    // With products, product details, and vendor
    @Query("SELECT s FROM Store s " +
            "LEFT JOIN FETCH s.availableProducts ap " +
            "LEFT JOIN FETCH ap.product " +
            "LEFT JOIN FETCH s.vendor " +
            "WHERE s.storeCode = :storeCode")
    Optional<Store> findByStoreCodeWithFullDetails(@Param("storeCode") String storeCode);

}