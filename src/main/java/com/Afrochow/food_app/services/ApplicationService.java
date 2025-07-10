package com.Afrochow.food_app.services;

import com.Afrochow.food_app.DTO.VendorDTO;
import com.Afrochow.food_app.DTO.StoreDto;
import com.Afrochow.food_app.DTO.UserDTO;
import com.Afrochow.food_app.config.ReUsableFunctions;
import com.Afrochow.food_app.model.Product;
import com.Afrochow.food_app.model.User;
import com.Afrochow.food_app.model.Vendor;
import com.Afrochow.food_app.model.Store;
import com.Afrochow.food_app.pojo.*;
import com.Afrochow.food_app.repository.ProductRepo;
import com.Afrochow.food_app.repository.VendorRepo;
import com.Afrochow.food_app.repository.StoreRepo;
import com.Afrochow.food_app.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;

import static com.Afrochow.food_app.config.AppConstant.*;

@Slf4j
@Service
public class ApplicationService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    VendorRepo vendorRepo;

    @Autowired
    StoreRepo storeRepo;

    @Autowired
    ProductRepo productRepo;

    ReUsableFunctions reUsableFunctions = new ReUsableFunctions();


    //........................User Services................................. //

    public BaseResponse testing() {
        BaseResponse baseResponse = new BaseResponse(true);
        baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
        baseResponse.setMessage("Testing successfully Done");
        baseResponse.setData(EMPTY_DATA);

        return baseResponse;
    }

    public BaseResponse createAccount(UserProfileData userProfileData) {
        BaseResponse baseResponse = new BaseResponse(true);
        try {
            //check if email address exist//
            Optional<User> emailExist = userRepo.findByEmailAddress(userProfileData.getEmailAddress());
            if (emailExist.isPresent()) {
                baseResponse.setStatusCode("409");
                baseResponse.setMessage("Email Address already exist, Kindly register with another email or login");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;
            }

            Optional<User> phoneNumberExist = userRepo.findByPhoneNumber(userProfileData.getPhoneNumber());
            if (phoneNumberExist.isPresent()) {
                baseResponse.setStatusCode("409");
                baseResponse.setMessage("Phone Number already exist, Kindly register with another phone number or login");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;
            }

            //get data and pass to entity
            User user = new User();
            user.setFirstName(userProfileData.getFirstName());
            user.setLastName(userProfileData.getLastName());
            user.setEmailAddress(userProfileData.getEmailAddress());
            user.setPassword(reUsableFunctions.encryptPassword(userProfileData.getPassword()));
            user.setStreetAddress(userProfileData.getStreetAddress());
            user.setCountry(userProfileData.getCountry());
            user.setPostalCode(userProfileData.getPostalCode());
            user.setProvince(userProfileData.getProvince());
            user.setApartmentNumber(userProfileData.getApartmentNumber());
            user.setCity(userProfileData.getCity());
            user.setPhoneNumber(userProfileData.getPhoneNumber());



            //save entity inside repository
            userRepo.save(user);
            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Account created successfully");
            baseResponse.setData(EMPTY_DATA);

        } catch (Exception e) {
            baseResponse.setStatusCode("500");
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse getallUsers() {
        BaseResponse baseResponse = new BaseResponse(true);
        try {
            List<User> fetchAllUser = userRepo.findAllByOrderByIdDesc();
            List<UserDTO> result = new ArrayList<>();
            for (User user : fetchAllUser) {
                UserDTO userDTO = new UserDTO();
                userDTO.setFirstName(user.getFirstName());
                userDTO.setLastName(user.getLastName());
                userDTO.setEmailAddress(user.getEmailAddress());
                userDTO.setCountry(user.getCountry());
                userDTO.setApartmentNumber(user.getApartmentNumber());
                userDTO.setProvince(user.getProvince());
                userDTO.setPostalCode(user.getPostalCode());
                userDTO.setStreetAddress(user.getStreetAddress());
                userDTO.setPhoneNumber(user.getPhoneNumber());
                userDTO.setCity(user.getCity());
                userDTO.setId(user.getId());

                result.add(userDTO);
            }

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("SUCCESSFUL");
            baseResponse.setData(result);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return baseResponse;


    }

    public BaseResponse deleteUser(String userId) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            if (userId == null || userId.trim().isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("User ID cannot be empty");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            long id;
            try {
                id = Long.parseLong(userId);
            } catch (NumberFormatException e) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Invalid User ID format");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            User user = userRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("No seller found with ID: " + userId));

            userRepo.delete(user);

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("User deleted successfully");
            baseResponse.setData(EMPTY_DATA);

        } catch (Exception e) {
            baseResponse.setStatusCode(ERROR_STATUS_CODE);
            baseResponse.setMessage("An error occurred while deleting the user: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse getUserById(String userId) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            if (userId == null || userId.trim().isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("User ID cannot be empty");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            long id;
            try {
                id = Long.parseLong(userId);
            } catch (NumberFormatException e) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Invalid User ID format");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            User user = userRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("No user found"));

            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmailAddress(user.getEmailAddress());
            userDTO.setCountry(user.getCountry());
            userDTO.setApartmentNumber(user.getApartmentNumber());
            userDTO.setProvince(user.getProvince());
            userDTO.setPostalCode(user.getPostalCode());
            userDTO.setStreetAddress(user.getStreetAddress());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            userDTO.setCity(user.getCity());

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage(SUCCESS_MESSAGE);
            baseResponse.setData(userDTO);

        } catch (Exception e) {
            // Logging the exception is recommended here
            baseResponse.setStatusCode(ERROR_STATUS_CODE);
            baseResponse.setMessage("An error occurred while fetching the user: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

     public BaseResponse editUser(UpdateUserProfile updateUserProfile) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            Long userId = Long.parseLong(updateUserProfile.getUserId());
            String newPhoneNumber = updateUserProfile.getPhoneNumber();

            // Check if phone number is being updated and already exists for a different user
            if (newPhoneNumber != null && !newPhoneNumber.trim().isEmpty()) {
                Optional<User> existingCustomerWithPhone = userRepo.findByPhoneNumber(newPhoneNumber.trim());

                if (existingCustomerWithPhone.isPresent() &&
                        !existingCustomerWithPhone.get().getId().equals(userId)) {
                    baseResponse.setStatusCode(ERROR_STATUS_CODE);
                    baseResponse.setMessage("Phone number already exists");
                    baseResponse.setData(EMPTY_DATA);
                    return baseResponse;
                }
            }

            // Fetch user
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("No user found"));

            // Use helper method for cleaner field updates
            user.setFirstName(reUsableFunctions.getUpdatedValue(updateUserProfile.getFirstName(), user.getFirstName()));
            user.setLastName(reUsableFunctions.getUpdatedValue(updateUserProfile.getLastName(), user.getLastName()));
            user.setStreetAddress(reUsableFunctions.getUpdatedValue(updateUserProfile.getStreetAddress(), user.getStreetAddress()));
            user.setCountry(reUsableFunctions.getUpdatedValue(updateUserProfile.getCountry(), user.getCountry()));
            user.setPostalCode(reUsableFunctions.getUpdatedValue(updateUserProfile.getPostalCode(), user.getPostalCode()));
            user.setProvince(reUsableFunctions.getUpdatedValue(updateUserProfile.getProvince(), user.getProvince()));
            user.setApartmentNumber(reUsableFunctions.getUpdatedValue(updateUserProfile.getApartmentNumber(), user.getApartmentNumber()));
            user.setCity(reUsableFunctions.getUpdatedValue(updateUserProfile.getCity(), user.getCity()));
            user.setPhoneNumber(reUsableFunctions.getUpdatedValue(updateUserProfile.getPhoneNumber(), user.getPhoneNumber()));
            user.setUpdatedAt(LocalDateTime.now());

            userRepo.save(user);

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("User updated successfully");
            baseResponse.setData(EMPTY_DATA);

        } catch (Exception e) {
            baseResponse.setStatusCode(ERROR_STATUS_CODE);
            baseResponse.setMessage("An error occurred while updating the user: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }


    //........................VENDOR SERVICE................................. //

    public BaseResponse createVendorAccount(VendorProfileData vendorProfileData) {
        BaseResponse baseResponse = new BaseResponse(true);
        try {
            // Check if email already exists
            Optional<Vendor> sellerEmailExist = vendorRepo.findByEmailAddress(vendorProfileData.getEmailAddress());
            if (sellerEmailExist.isPresent()) {
                baseResponse.setStatusCode("409");
                baseResponse.setMessage("Email Address already exist, kindly register with another email or login");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            // Check if phone number already exists
            Optional<Vendor> sellerPhoneExist = vendorRepo.findByPhoneNumber(vendorProfileData.getPhoneNumber());
            if (sellerPhoneExist.isPresent()) {
                baseResponse.setStatusCode("409");
                baseResponse.setMessage("Phone Number already exist, Kindly register with another phone number or login");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }


            // Create new seller account
            Vendor vendor = new Vendor();

            vendor.setVendorId(reUsableFunctions.generateId(vendorProfileData.getLastName()));
            vendor.setFirstName(vendorProfileData.getFirstName());
            vendor.setLastName(vendorProfileData.getLastName());
            vendor.setEmailAddress(vendorProfileData.getEmailAddress());
            vendor.setPhoneNumber(vendorProfileData.getPhoneNumber());
            vendor.setApartmentNumber(vendorProfileData.getApartmentNumber());
            vendor.setStreetAddress(vendorProfileData.getStreetAddress());
            vendor.setCountry(vendorProfileData.getCountry());
            vendor.setCity(vendorProfileData.getCity());
            vendor.setPassword(reUsableFunctions.encryptPassword(vendorProfileData.getPassword()));
            vendor.setIdCardBack(vendorProfileData.getIdCardBack());
            vendor.setIdCardFront(vendorProfileData.getIdCardFront());
            vendor.setProfilePhoto(vendorProfileData.getProfilePhoto());
            vendor.setPostalCode(vendorProfileData.getPostalCode());
            vendor.setProvince(vendorProfileData.getProvince());


            vendorRepo.save(vendor);

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Vendor Account created successfully.");
            baseResponse.setData(EMPTY_DATA);

        } catch (Exception e) {
            baseResponse.setStatusCode("500");
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse getAllVendor() {

        BaseResponse baseResponse = new BaseResponse(true);

        try {
            List<Vendor> fetchAllSellers = vendorRepo.findAllByOrderByVendorIdDesc();
            List<VendorDTO> sellerResult = new ArrayList<>();

            for (Vendor vendor : fetchAllSellers) {
                VendorDTO vendorDTO = new VendorDTO();

                vendorDTO.setVendorId(vendor.getVendorId());
                vendorDTO.setFirstName(vendor.getFirstName());
                vendorDTO.setLastName(vendor.getLastName());
                vendorDTO.setEmailAddress(vendor.getEmailAddress());
                vendorDTO.setCountry(vendor.getCountry());
                vendorDTO.setStreetAddress(vendor.getStreetAddress());
                vendorDTO.setApartmentNumber(vendor.getApartmentNumber());
                vendorDTO.setProvince(vendor.getProvince());
                vendorDTO.setCity(vendor.getCity());
                vendorDTO.setPostalCode(vendor.getPostalCode());
                vendorDTO.setPhoneNumber(vendor.getPhoneNumber());
                vendorDTO.setProfilePhoto(vendor.getProfilePhoto());

                sellerResult.add(vendorDTO);

            }
            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage(SUCCESS_MESSAGE);
            baseResponse.setData(sellerResult);


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return baseResponse;
    }

    public BaseResponse getVendorById(String vendorId) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            Optional<Vendor> getBusinessOwnerDetails = vendorRepo.findByVendorId(vendorId);
            if(getBusinessOwnerDetails.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Business Owner Id does not Exist");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;

            }

            Vendor vendor = getBusinessOwnerDetails.get();


            VendorDTO vendorDTO = new VendorDTO();

            vendorDTO.setVendorId(vendor.getVendorId());
            vendorDTO.setFirstName(vendor.getFirstName());
            vendorDTO.setLastName(vendor.getLastName());
            vendorDTO.setEmailAddress(vendor.getEmailAddress());
            vendorDTO.setCountry(vendor.getCountry());
            vendorDTO.setStreetAddress(vendor.getStreetAddress());
            vendorDTO.setApartmentNumber(vendor.getApartmentNumber());
            vendorDTO.setProvince(vendor.getProvince());
            vendorDTO.setCity(vendor.getCity());
            vendorDTO.setPostalCode(vendor.getPostalCode());
            vendorDTO.setPhoneNumber(vendor.getPhoneNumber());
            vendorDTO.setProfilePhoto(vendor.getProfilePhoto());


            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage(SUCCESS_MESSAGE);
            baseResponse.setData(vendorDTO);


        } catch (RuntimeException e) {
            // Logging the exception is recommended here
            baseResponse.setStatusCode(ERROR_STATUS_CODE);
            baseResponse.setMessage("An error occurred while fetching the user: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;

    }

    public BaseResponse editVendor(UpdateVendorProfile updateVendorProfile) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {

            Optional<Vendor> getBusinessOwnerDetails = vendorRepo.findByVendorId(updateVendorProfile.getBusinessOwnerId());
            if(getBusinessOwnerDetails.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Vendor Id does not Exist");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;

            }

            Vendor vendor = getBusinessOwnerDetails.get();


            String newPhoneNumber = updateVendorProfile.getPhoneNumber();

            if (newPhoneNumber != null && !newPhoneNumber.trim().isEmpty()) {
                Optional<Vendor> existingSellerWithPhone = vendorRepo.findByPhoneNumber(newPhoneNumber.trim());
                if (existingSellerWithPhone.isPresent() && !Objects.equals(vendor.getPhoneNumber(),newPhoneNumber)) {
                    baseResponse.setStatusCode(ERROR_STATUS_CODE);
                    baseResponse.setMessage("Phone number already exists");
                    baseResponse.setData(EMPTY_DATA);
                    return baseResponse;
                }
                vendor.setPhoneNumber(newPhoneNumber.trim());
            }

            // Conditionally update other fields
            vendor.setFirstName(reUsableFunctions.getUpdatedValue(updateVendorProfile.getFirstName(), vendor.getFirstName()));
            vendor.setLastName(reUsableFunctions.getUpdatedValue(updateVendorProfile.getLastName(), vendor.getLastName()));
            vendor.setStreetAddress(reUsableFunctions.getUpdatedValue(updateVendorProfile.getStreetAddress(), vendor.getStreetAddress()));
            vendor.setCountry(reUsableFunctions.getUpdatedValue(updateVendorProfile.getCountry(), vendor.getCountry()));
            vendor.setPostalCode(reUsableFunctions.getUpdatedValue(updateVendorProfile.getPostalCode(), vendor.getPostalCode()));
            vendor.setProvince(reUsableFunctions.getUpdatedValue(updateVendorProfile.getProvince(), vendor.getProvince()));
            vendor.setApartmentNumber(reUsableFunctions.getUpdatedValue(updateVendorProfile.getApartmentNumber(), vendor.getApartmentNumber()));
            vendor.setCity(reUsableFunctions.getUpdatedValue(updateVendorProfile.getCity(), vendor.getCity()));
            vendor.setIdCardFront(reUsableFunctions.getUpdatedValue(updateVendorProfile.getIdCardFront(), vendor.getIdCardFront()));
            vendor.setIdCardBack(reUsableFunctions.getUpdatedValue(updateVendorProfile.getIdCardBack(), vendor.getIdCardBack()));
            vendor.setProfilePhoto(reUsableFunctions.getUpdatedValue(updateVendorProfile.getProfilePhoto(), vendor.getProfilePhoto()));
            vendor.setUpdatedAt(LocalDateTime.now());

            vendorRepo.save(vendor);

            baseResponse.setMessage(SUCCESS_MESSAGE);
            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setData(EMPTY_DATA);

        } catch (Exception e) {
            baseResponse.setStatusCode(ERROR_STATUS_CODE);
            baseResponse.setMessage("An error occurred while updating the user: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse deleteVendor(String vendorId) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            Optional<Vendor> getBusinessOwnerDetails = vendorRepo.findByVendorId(vendorId);
            if(getBusinessOwnerDetails.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Vendor Id does not Exist");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;

            }

            Vendor vendor = getBusinessOwnerDetails.get();

            vendorRepo.delete(vendor);

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Vendor Deleted Successfully");
            baseResponse.setData(EMPTY_DATA);

        } catch (Exception e) {
            baseResponse.setStatusCode(ERROR_STATUS_CODE);
            baseResponse.setMessage("An error occurred while updating the user: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }




    //........................CREATING STORE INFORMATION SERVICE................................. //

    public BaseResponse createStore(StoreData storeData) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {

            Optional<Vendor> getVendorDetails = vendorRepo.findByVendorId(storeData.getVendorId());
            if(getVendorDetails.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Business Owner Id does not Exist");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;

            }

            List<Store> checkStoreName = storeRepo.findStoreByStoreNameIgnoreCase(storeData.getStoreName());
            if(!checkStoreName.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Store Name not available");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;
            }



            Vendor vendor = getVendorDetails.get();
           

            Store newStore = new Store();

            newStore.setStoreId(reUsableFunctions.generateId(storeData.getStoreName()));
            newStore.setVendor(vendor);
            newStore.setStoreLogo(storeData.getStoreLogo());
            newStore.setStoreName(storeData.getStoreName());
            newStore.setStoreDescription(storeData.getStoreDescription());
            newStore.setStreetAddress(storeData.getStreetAddress());
            newStore.setStoreCity(storeData.getStoreCity());
            newStore.setStoreCountry(storeData.getStoreCountry());
            newStore.setStorePostalCode(storeData.getStorePostalCode());
            newStore.setStoreProvince(storeData.getStoreProvince());
            newStore.setStorePhoneNumber(vendor.getPhoneNumber());
            newStore.setStoreCategory(storeData.getStoreCategory());
            newStore.setStoreHours(storeData.getStoreHours());
            newStore.setMaxDeliveryDistance(storeData.getMaxDeliveryDistance());
            newStore.setPickupAvailable(Boolean.TRUE.equals(storeData.getPickupAvailable()));
            newStore.setDeliveryAvailable(Boolean.TRUE.equals(storeData.getDeliveryAvailable()));

            storeRepo.save(newStore);

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Store created successfully.");
            baseResponse.setData(EMPTY_DATA);

        } catch (Exception e) {
            baseResponse.setStatusCode("500");
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse updateStore(UpdatedStoreData updatedStoreData) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            Optional<Vendor> businessOwner = vendorRepo.findByVendorId(updatedStoreData.getBusinessOwnerId());
            Optional<Store> getStore = storeRepo.findByStoreId(updatedStoreData.getStoreId());

            if (getStore.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Store ID does not exist");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            Store store = getStore.get();

            store.setStoreName(reUsableFunctions.getUpdatedValue(updatedStoreData.getStoreName(), store.getStoreName()));
            store.setStoreLogo(reUsableFunctions.getUpdatedValue(updatedStoreData.getStoreLogo(), store.getStoreLogo()));
            store.setStoreDescription(reUsableFunctions.getUpdatedValue(updatedStoreData.getStoreDescription(), store.getStoreDescription()));
            store.setStreetAddress(reUsableFunctions.getUpdatedValue(updatedStoreData.getStreetAddress(), store.getStreetAddress()));
            store.setStoreCity(reUsableFunctions.getUpdatedValue(updatedStoreData.getStoreCity(), store.getStoreCity()));
            store.setStorePostalCode(reUsableFunctions.getUpdatedValue(updatedStoreData.getStorePostalCode(), store.getStorePostalCode()));
            store.setStoreCountry(reUsableFunctions.getUpdatedValue(updatedStoreData.getStoreCountry(), store.getStoreCountry()));
            store.setStoreCategory(reUsableFunctions.getUpdatedValue(updatedStoreData.getStoreCategory(), store.getStoreCategory()));
            store.setStoreHours(reUsableFunctions.getUpdatedValue(updatedStoreData.getStoreHours(), store.getStoreHours()));
            store.setMaxDeliveryDistance(reUsableFunctions.getUpdatedValue(updatedStoreData.getMaxDeliveryDistance(), store.getMaxDeliveryDistance()));
            store.setPickupAvailable(reUsableFunctions.getUpdatedValueBoolean(updatedStoreData.isPickupAvailable(), store.isPickupAvailable()));
            store.setDeliveryAvailable(reUsableFunctions.getUpdatedValueBoolean(updatedStoreData.isDeliveryAvailable(), store.isDeliveryAvailable()));
            store.setUpdatedAt(LocalDateTime.now());

            storeRepo.save(store);

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Store updated successfully");
            baseResponse.setData(store);

        } catch (Exception e) {
            baseResponse.setStatusCode(ERROR_STATUS_CODE);
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse deleteStore(String storeId){

        BaseResponse baseResponse = new BaseResponse(true);

     try {

         Optional<Store> getStore = storeRepo.findByStoreId(storeId);

         if (getStore.isEmpty()){
             baseResponse.setStatusCode(ERROR_STATUS_CODE);
             baseResponse.setMessage("Store ID empty");
             baseResponse.setData(EMPTY_DATA);

             return baseResponse;
         }

                Store store = getStore.get();

                storeRepo.delete(store);

           baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
           baseResponse.setMessage(SUCCESS_MESSAGE);
           baseResponse.setData(EMPTY_DATA);

     } catch (Exception e) {
         baseResponse.setStatusCode(ERROR_STATUS_CODE);
         baseResponse.setMessage("An error occur " + e.getMessage());
         baseResponse.setData(EMPTY_DATA);
     }

     return baseResponse;
    }

    public BaseResponse getStoresByFilter(String storeCategory, String storeName) {
        BaseResponse baseResponse = new BaseResponse(true);
        List<StoreDto> allStoreResult = new ArrayList<>();

        try {
            List<Store> stores;

            if ((storeCategory == null || storeCategory.isEmpty()) &&
                    (storeName == null || storeName.isEmpty())) {
                stores = storeRepo.findAllByOrderByStoreIdDesc();
            } else if ((storeCategory != null && !storeCategory.isEmpty()) &&
                    (storeName != null && !storeName.isEmpty())) {
                stores = storeRepo.findByStoreCategoryContainingIgnoreCaseAndStoreNameContainingIgnoreCase(
                        storeCategory, storeName
                );
            } else if (storeCategory != null && !storeCategory.isEmpty()) {
                stores = storeRepo.findAllByStoreCategoryContainingIgnoreCase(storeCategory);
            } else {
                stores = storeRepo.findAllByStoreNameContainingIgnoreCase(storeName);
            }

            for (Store store : stores) {
                StoreDto storeDto = new StoreDto();

                storeDto.setStoreId(store.getStoreId());
                storeDto.setStoreLogo(store.getStoreLogo());
                storeDto.setStoreName(store.getStoreName());
                storeDto.setStoreCategory(store.getStoreCategory());
                storeDto.setStoreDescription(store.getStoreDescription());
                storeDto.setStreetAddress(store.getStreetAddress());
                storeDto.setStoreCity(store.getStoreCity());
                storeDto.setStoreCountry(store.getStoreCountry());
                storeDto.setStoreHours(store.getStoreHours());
                storeDto.setStorePhoneNumber(store.getStorePhoneNumber());
                storeDto.setDeliveryAvailable(store.isDeliveryAvailable());
                storeDto.setPickupAvailable(store.isPickupAvailable());
                storeDto.setStorePostalCode(store.getStorePostalCode());
                storeDto.setMaxDeliveryDistance(store.getMaxDeliveryDistance());

                allStoreResult.add(storeDto);
            }

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage(SUCCESS_MESSAGE);
            baseResponse.setData(allStoreResult);

        } catch (Exception e) {
            baseResponse.setStatusCode(ERROR_STATUS_CODE);
            baseResponse.setMessage("An error has occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse getStoreById (String storeId) {
        BaseResponse baseResponse = new BaseResponse(true);

     try{
         Optional<Store> fetchStore = storeRepo.findByStoreId(storeId);

         if (fetchStore.isEmpty()){
             baseResponse.setStatusCode(ERROR_STATUS_CODE);
             baseResponse.setMessage("Store Id Empty or Does Not Exist");
             baseResponse.setData(EMPTY_DATA);

             return baseResponse;
         }

            Store store = fetchStore.get();
            StoreDto storeDto = new StoreDto();

         storeDto.setStoreLogo(store.getStoreLogo());
         storeDto.setStoreName(store.getStoreName());
         storeDto.setStoreCategory(store.getStoreCategory());
         storeDto.setStoreDescription(store.getStoreDescription());
         storeDto.setStreetAddress(store.getStreetAddress());
         storeDto.setStoreCity(store.getStoreCity());
         storeDto.setStoreCountry(store.getStoreCountry());
         storeDto.setStoreHours(store.getStoreHours());
         storeDto.setStorePhoneNumber(store.getStorePhoneNumber());
         storeDto.setDeliveryAvailable(store.isDeliveryAvailable());
         storeDto.setPickupAvailable(store.isPickupAvailable());
         storeDto.setStorePostalCode(store.getStorePostalCode());
         storeDto.setMaxDeliveryDistance(store.getMaxDeliveryDistance());

         baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
         baseResponse.setMessage(SUCCESS_MESSAGE);
         baseResponse.setData(storeDto);

     } catch (Exception e) {
          baseResponse.setStatusCode(ERROR_STATUS_CODE);
          baseResponse.setMessage(ERROR_MESSAGE);
          baseResponse.setData(EMPTY_DATA);
     }

        return baseResponse;
    }

    public BaseResponse getStoreByKeyword (String keyword) {

        BaseResponse baseResponse = new BaseResponse(true);

        try {
            List<Store> storeList = storeRepo.searchByKeyword(keyword);
            List<StoreDto> allStoreResult = new ArrayList<>();

            if (storeList.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage( "No store found with the keyword " + keyword);
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }


            for (Store store : storeList){

                StoreDto storeDto = new StoreDto();

                storeDto.setStoreLogo(store.getStoreLogo());
                storeDto.setStoreName(store.getStoreName());
                storeDto.setStoreCategory(store.getStoreCategory());
                storeDto.setStoreDescription(store.getStoreDescription());
                storeDto.setStreetAddress(store.getStreetAddress());
                storeDto.setStoreCity(store.getStoreCity());
                storeDto.setStoreCountry(store.getStoreCountry());
                storeDto.setStoreHours(store.getStoreHours());
                storeDto.setStorePhoneNumber(store.getStorePhoneNumber());
                storeDto.setDeliveryAvailable(store.isDeliveryAvailable());
                storeDto.setPickupAvailable(store.isPickupAvailable());
                storeDto.setStorePostalCode(store.getStorePostalCode());
                storeDto.setMaxDeliveryDistance(store.getMaxDeliveryDistance());

                allStoreResult.add(storeDto);

                baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
                baseResponse.setMessage(SUCCESS_MESSAGE);
                baseResponse.setData(allStoreResult);
            }

        } catch (Exception e) {
            baseResponse.setStatusCode(ERROR_STATUS_CODE);
            baseResponse.setMessage(ERROR_MESSAGE);
            baseResponse.setData(EMPTY_DATA);
        }
        return baseResponse;
    }

    public BaseResponse getStoreByLocation(String location) {
        BaseResponse baseResponse = new BaseResponse(true);
        List<StoreDto> allStoreResult = new ArrayList<>();

        try {
            List<Store> storeList = storeRepo.searchByLocation(location);

            if (storeList.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("No stores found at " + location);
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            for (Store store : storeList) {
                StoreDto storeDto = new StoreDto();

                storeDto.setStoreLogo(store.getStoreLogo());
                storeDto.setStoreName(store.getStoreName());
                storeDto.setStoreCategory(store.getStoreCategory());
                storeDto.setStoreDescription(store.getStoreDescription());
                storeDto.setStreetAddress(store.getStreetAddress());
                storeDto.setStoreCity(store.getStoreCity());
                storeDto.setStoreCountry(store.getStoreCountry());
                storeDto.setStoreHours(store.getStoreHours());
                storeDto.setStorePhoneNumber(store.getStorePhoneNumber());
                storeDto.setDeliveryAvailable(store.isDeliveryAvailable());
                storeDto.setPickupAvailable(store.isPickupAvailable());
                storeDto.setStorePostalCode(store.getStorePostalCode());
                storeDto.setMaxDeliveryDistance(store.getMaxDeliveryDistance());

                allStoreResult.add(storeDto);
            }

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage(SUCCESS_MESSAGE);
            baseResponse.setData(allStoreResult);

        } catch (Exception e) {
            baseResponse.setStatusCode(ERROR_STATUS_CODE);
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }



//    .............................Create Products.....................................................

    public BaseResponse createProduct (ProductData productData){

        BaseResponse baseResponse = new BaseResponse(true);

        try {

            Optional<Store> getStoreIdDetails = storeRepo.findByStoreId(productData.getStoreId());
            if (getStoreIdDetails.isEmpty()){
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Store ID not Valid");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;
            }

            Store store  = getStoreIdDetails.get();

            Product registeredProduct = new Product();

            registeredProduct.setProductId(reUsableFunctions.generateId(productData.getProductName()));
            registeredProduct.setStore(store);
            registeredProduct.setProductName(productData.getProductName());
            registeredProduct.setProductPrice(productData.getProductPrice());
            registeredProduct.setProductDescription(productData.getProductDescription());
            registeredProduct.setProductImage(productData.getProductImage());

            productRepo.save(registeredProduct);

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Product created successfully.");
            baseResponse.setData(EMPTY_DATA);


        } catch (Exception e) {
            baseResponse.setStatusCode("500");
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }


        return baseResponse;
    }



    public BaseResponse createBulkProduct (List<ProductData> productDataList) {
        BaseResponse baseResponse = new BaseResponse();

        try {
            if (productDataList == null || productDataList.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Product list cannot be empty.");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            String storeId = productDataList.get(0).getStoreId();
            Optional<Store> getStoreIdDetails = storeRepo.findByStoreId(storeId);
            if (getStoreIdDetails.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Store ID not valid.");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            Store store = getStoreIdDetails.get();
            List<Product> productsToSave = new ArrayList<>();

            for (ProductData data : productDataList) {
                if (!storeId.equals(data.getStoreId())) {
                    continue;
                }
                Product product = new Product();
                product.setProductId(reUsableFunctions.generateId(data.getProductName()));
                product.setStore(store);
                product.setProductName(data.getProductName());
                product.setProductPrice(data.getProductPrice());
                product.setProductDescription(data.getProductDescription());
                product.setProductImage(data.getProductImage());

                productsToSave.add(product);
            }

            productRepo.saveAll(productsToSave);

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Products created successfully.");
            baseResponse.setData(EMPTY_DATA);

        } catch (Exception e) {
            baseResponse.setStatusCode("500");
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);

        }
        return baseResponse;
    }



}

