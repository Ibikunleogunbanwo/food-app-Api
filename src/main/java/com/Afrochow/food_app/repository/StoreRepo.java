package com.Afrochow.food_app.repository;

import com.Afrochow.food_app.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepo extends JpaRepository<Store, Long> {

    Optional<Store> findByStorePhoneNumber(String storePhoneNumber);

    Optional<Store>  findByStoreId (String storeId);
    List<Store> findStoreByStoreNameIgnoreCase(String storeName);
    List<Store> findAllByOrderByStoreIdDesc();
    List<Store> findAllByStoreNameContainingIgnoreCase(String storeName);
    List<Store> findAllByStoreCategoryContainingIgnoreCase(String storeCategory);
    List<Store> findByStoreCategoryContainingIgnoreCaseAndStoreNameContainingIgnoreCase(String storeCategory, String storeName);

    @Query("SELECT s FROM Store s WHERE LOWER(s.storeCategory) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.storeName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.storeDescription) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            "OR LOWER(s.storeProvince) LIKE LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Store> searchByKeyword(@Param("keyword") String keyword);


    @Query("SELECT s FROM Store s WHERE " +
            "LOWER(s.storeCity) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.storePostalCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.streetAddress) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Store> searchByLocation(@Param("keyword") String keyword);

    List<Store> findAllByStoreCityContainingIgnoreCase(String storeCity);

}