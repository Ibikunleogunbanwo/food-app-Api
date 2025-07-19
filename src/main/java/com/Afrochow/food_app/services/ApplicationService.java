package com.Afrochow.food_app.services;

import com.Afrochow.food_app.model.*;
import com.Afrochow.food_app.repository.*;
import com.Afrochow.food_app.response_dto.*;
import com.Afrochow.food_app.config.ReUsableFunctions;
import com.Afrochow.food_app.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static com.Afrochow.food_app.config.AppConstant.*;

@Slf4j
@Service
public class ApplicationService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    VendorRepo vendorRepo;

    @Autowired
    StoreRepo storeRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    FileStorageService fileStorageService;

    ReUsableFunctions reUsableFunctions = new ReUsableFunctions();


    //........................Customer Services................................. //

    public BaseResponse testing() {
        BaseResponse baseResponse = new BaseResponse(true);
        baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
        baseResponse.setMessage("Testing successfully Done");
        baseResponse.setData(EMPTY_DATA);

        return baseResponse;
    }

    public BaseResponse createCustomerAccount(CustomerRegistration customerRegistration) {
        BaseResponse baseResponse = new BaseResponse(true);
        try {
            //check if email address exist//
            Optional<User> emailExist = userRepo.findByEmail(customerRegistration.getEmail());
            if (emailExist.isPresent()) {
                baseResponse.setStatusCode("409");
                baseResponse.setMessage("Email Address already exist, Kindly register with another email or login");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;
            }

            Optional<User> phoneNumberExist = userRepo.findByPhoneNumber(customerRegistration.getPhoneNumber());
            if (phoneNumberExist.isPresent()) {
                baseResponse.setStatusCode("409");
                baseResponse.setMessage("Phone Number already exist, Kindly register with another phone number or login");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;
            }


            Customer customer = new Customer();

            customer.setFirstName(customerRegistration.getFirstName());
            customer.setLastName(customerRegistration.getLastName());
            customer.setUserId(reUsableFunctions.generateId(customer.getFirstName()));
            customer.setCustomerId(reUsableFunctions.generateId(customerRegistration.getLastName()));
            customer.setEmail(customerRegistration.getEmail());
            customer.setPassword(reUsableFunctions.encryptPassword(customerRegistration.getPassword()));
            customer.setPhoneNumber(customerRegistration.getPhoneNumber());
            customer.setLoyaltyPoints(
                    customerRegistration.getLoyaltyPoints() != null ? customerRegistration.getLoyaltyPoints() : 0
            );
            customer.setPreferredDeliveryTime(customerRegistration.getPreferredDeliveryTime());


            Address address = new Address();
            if (customerRegistration.getAddress() != null) {
                address.setApartmentNumber(customerRegistration.getAddress().getApartmentNumber());
                address.setStreetAddress(customerRegistration.getAddress().getStreetAddress());
                address.setCountry(customerRegistration.getAddress().getCountry());
                address.setCity(customerRegistration.getAddress().getCity());
                address.setPostalCode(customerRegistration.getAddress().getPostalCode());
                address.setProvince(customerRegistration.getAddress().getProvince());
            }


            customer.setAddress(address);
            userRepo.save(customer);


            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Account created successfully");
            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Vendor account created successfully.");
            baseResponse.setData(Map.of(
                    "CustomerId", customer.getCustomerId(),
                    "email", customer.getEmail(),
                    "First Name", customer.getFirstName(),
                    "Last Name", customer.getLastName()
            ));

        } catch (Exception e) {
            baseResponse.setStatusCode("500");
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse getAllCustomers() {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            List<Customer> fetchAllCustomer = customerRepo.findAllByOrderByIdDesc();
            List<CustomerDTO> result = new ArrayList<>();

            for (Customer customer : fetchAllCustomer) {
                CustomerDTO customerDTO = new CustomerDTO();
                AddressDTO addressDTO = new AddressDTO();

                customerDTO.setUserId(customer.getId().toString());
                customerDTO.setFirstName(customer.getFirstName());
                customerDTO.setLastName(customer.getLastName());
                customerDTO.setEmail(customer.getEmail());
                customerDTO.setPhoneNumber(customer.getPhoneNumber());
                customerDTO.setLoyaltyPoints(customer.getLoyaltyPoints());
                customerDTO.setPreferredDeliveryTime(customer.getPreferredDeliveryTime());


                if (customer.getAddress() != null) {
                    Address address = customer.getAddress();

                    addressDTO.setStreetAddress(address.getStreetAddress());
                    addressDTO.setApartmentNumber(address.getApartmentNumber());
                    addressDTO.setCity(address.getCity());
                    addressDTO.setProvince(address.getProvince());
                    addressDTO.setCountry(address.getCountry());
                    addressDTO.setPostalCode(address.getPostalCode());

                    customerDTO.setAddress(addressDTO);
                }

                result.add(customerDTO);
            }

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Customers retrieved successfully");
            baseResponse.setData(result);

        } catch (Exception e) {
            baseResponse.setStatusCode("500");
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse deleteCustomer(String userId) {
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
                    .orElseThrow(() -> new RuntimeException("No User found with ID: " + userId));

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

    public BaseResponse getCustomerById(String userId) {
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

            Customer customer = customerRepo.findCustomerById(id)
                    .orElseThrow(() -> new RuntimeException("No user found"));

            CustomerDTO customerDTO = new CustomerDTO();

            customerDTO.setUserId(customer.getUserId());
            customerDTO.setFirstName(customer.getFirstName());
            customerDTO.setLastName(customer.getLastName());
            customerDTO.setEmail(customer.getEmail());
            customerDTO.setPhoneNumber(customer.getPhoneNumber());

            if (customer.getAddress() != null) {
                Address address = customer.getAddress();
                AddressDTO addressDTO = new AddressDTO();

                addressDTO.setCountry(address.getCountry());
                addressDTO.setApartmentNumber(address.getApartmentNumber());
                addressDTO.setProvince(address.getProvince());
                addressDTO.setPostalCode(address.getPostalCode());
                addressDTO.setStreetAddress(address.getStreetAddress());
                addressDTO.setCity(address.getCity());

                customerDTO.setAddress(addressDTO);
            }

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage(SUCCESS_MESSAGE);
            baseResponse.setData(customerDTO);

        } catch (Exception e) {
            // Logging the exception is recommended here
            baseResponse.setStatusCode(ERROR_STATUS_CODE);
            baseResponse.setMessage("An error occurred while fetching the user: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse editCustomer(EditCustomer editCustomer) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            Long userId = Long.parseLong(editCustomer.getUserId());
            String newPhoneNumber = editCustomer.getPhoneNumber();

            // Check for existing phone number assigned to a different customer
            if (newPhoneNumber != null && !newPhoneNumber.trim().isEmpty()) {
                Optional<Customer> existingCustomerWithPhone = customerRepo.findCustomerByPhoneNumber(newPhoneNumber.trim());

                if (existingCustomerWithPhone.isPresent() &&
                        !existingCustomerWithPhone.get().getId().equals(userId)) {
                    baseResponse.setStatusCode(ERROR_STATUS_CODE);
                    baseResponse.setMessage("Phone number already exists");
                    baseResponse.setData(EMPTY_DATA);
                    return baseResponse;
                }
            }

            // Fetch existing customer
            Customer customer = customerRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("No user found"));

            // Update basic fields
            customer.setFirstName(reUsableFunctions.getUpdatedValue(editCustomer.getFirstName(), customer.getFirstName()));
            customer.setLastName(reUsableFunctions.getUpdatedValue(editCustomer.getLastName(), customer.getLastName()));
            customer.setPhoneNumber(reUsableFunctions.getUpdatedValue(editCustomer.getPhoneNumber(), customer.getPhoneNumber()));
            customer.setUpdatedAt(LocalDateTime.now());

            // Update address correctly — use existing address
            if (customer.getAddress() != null && editCustomer.getAddress() != null) {
                Address address = customer.getAddress();
                EditAddress dto = editCustomer.getAddress();

                address.setStreetAddress(reUsableFunctions.getUpdatedValue(dto.getStreetAddress(), address.getStreetAddress()));
                address.setCountry(reUsableFunctions.getUpdatedValue(dto.getCountry(), address.getCountry()));
                address.setPostalCode(reUsableFunctions.getUpdatedValue(dto.getPostalCode(), address.getPostalCode()));
                address.setProvince(reUsableFunctions.getUpdatedValue(dto.getProvince(), address.getProvince()));
                address.setApartmentNumber(reUsableFunctions.getUpdatedValue(dto.getApartmentNumber(), address.getApartmentNumber()));
                address.setCity(reUsableFunctions.getUpdatedValue(dto.getCity(), address.getCity()));
            }

            userRepo.save(customer);

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("User updated successfully");
            baseResponse.setData(Map.of(
                    "CustomerId", customer.getCustomerId(),
                    "email", customer.getEmail(),
                    "First Name", customer.getFirstName(),
                    "Last Name", customer.getLastName()
            ));

        } catch (Exception e) {
            baseResponse.setStatusCode(ERROR_STATUS_CODE);
            baseResponse.setMessage("An error occurred while updating the user: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }


    //........................VENDOR SERVICE................................. //

    public BaseResponse uploadVendorImages(
            MultipartFile idCardFrontFile,
            MultipartFile idCardBackFile,
            MultipartFile businessLogoFile) {
        BaseResponse response = new BaseResponse(true);

        try {

            if (idCardFrontFile.isEmpty() || idCardBackFile.isEmpty() || businessLogoFile.isEmpty()) {
                response.setStatusCode("400");
                response.setMessage("One or more files are empty.");
                response.setData(EMPTY_DATA);
                return response;
            }


            String idCardFrontFileUrl = fileStorageService.saveIdCardFront(idCardFrontFile);
            String idCardBackFileUrl = fileStorageService.saveIdCardBack(idCardBackFile);
            String businessLogoFileUrl = fileStorageService.saveBusinessLogo(businessLogoFile);

            // Return all URLs in response
            Map<String, String> urls = Map.of(
                    "idCardFrontUrl", idCardFrontFileUrl,
                    "idCardBackUrl", idCardBackFileUrl,
                    "businessLogoUrl", businessLogoFileUrl
            );

            response.setStatusCode(SUCCESS_STATUS_CODE);
            response.setMessage("All media uploaded successfully.");
            response.setData(urls);

        } catch (Exception e) {
            log.error("Error uploading store media", e);
            response.setStatusCode("500");
            response.setMessage("Upload failed: " + e.getMessage());
            response.setData(EMPTY_DATA);
        }

        return response;
    }



    public BaseResponse createVendorAccount(VendorRegistration vendorRegistration) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            // Check if email already exists
            Optional<User> sellerEmailExist = userRepo.findByEmail(vendorRegistration.getEmail());
            if (sellerEmailExist.isPresent()) {
                baseResponse.setStatusCode("409");
                baseResponse.setMessage("Email Address already exists, kindly register with another email or login");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            // Check if phone number already exists
            Optional<User> sellerPhoneExist = userRepo.findByPhoneNumber(vendorRegistration.getPhoneNumber());
            if (sellerPhoneExist.isPresent()) {
                baseResponse.setStatusCode("409");
                baseResponse.setMessage("Phone Number already exists, kindly register with another phone number or login");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }


            Vendor vendor = new Vendor();
            vendor.setVendorId(reUsableFunctions.generateId(vendorRegistration.getLastName()));
            vendor.setUserId(reUsableFunctions.generateId(vendorRegistration.getFirstName()));
            vendor.setFirstName(vendorRegistration.getFirstName());
            vendor.setLastName(vendorRegistration.getLastName());
            vendor.setEmail(vendorRegistration.getEmail());
            vendor.setPhoneNumber(vendorRegistration.getPhoneNumber());
            vendor.setPassword(reUsableFunctions.encryptPassword(vendorRegistration.getPassword()));
            vendor.setBusinessLicenseNumber(vendorRegistration.getBusinessLicenseNumber());
            vendor.setBusinessName(vendorRegistration.getBusinessName());
            vendor.setTaxId(vendorRegistration.getTaxId());
            vendor.setIdCardFrontUrl(vendor.getIdCardFrontUrl());
            vendor.setIdCardBackUrl(vendor.getIdCardBackUrl());
            vendor.setBusinessLogoUrl(vendor.getBusinessLogoUrl());

            if (vendorRegistration.getAddress() != null) {
                Address address = new Address();
                address.setApartmentNumber(vendorRegistration.getAddress().getApartmentNumber());
                address.setStreetAddress(vendorRegistration.getAddress().getStreetAddress());
                address.setCountry(vendorRegistration.getAddress().getCountry());
                address.setCity(vendorRegistration.getAddress().getCity());
                address.setPostalCode(vendorRegistration.getAddress().getPostalCode());
                address.setProvince(vendorRegistration.getAddress().getProvince());
                vendor.setAddress(address);
            }

            vendorRepo.save(vendor);


            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Vendor account created successfully.");
            baseResponse.setData(Map.of(
                    "vendorId", vendor.getVendorId(),
                    "email", vendor.getEmail(),
                    "businessName", vendor.getBusinessName()
            ));

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

                vendorDTO.setUserId(vendor.getUserId());
                vendorDTO.setVendorId(vendor.getVendorId());
                vendorDTO.setFirstName(vendor.getFirstName());
                vendorDTO.setLastName(vendor.getLastName());
                vendorDTO.setPhoneNumber(vendor.getPhoneNumber());
                vendorDTO.setEmail(vendor.getEmail());
                vendorDTO.setBusinessName(vendor.getBusinessName());
                vendorDTO.setBusinessLogoUrl(vendor.getBusinessLogoUrl());
                vendorDTO.setTaxId(vendor.getTaxId());
                vendorDTO.setBusinessLicenseNumber(vendor.getBusinessLicenseNumber());
                vendorDTO.setIdCardBackUrl(vendor.getIdCardBackUrl());
                vendorDTO.setIdCardFrontUrl(vendor.getIdCardFrontUrl());

                if (vendor.getAddress() != null) {
                    AddressDTO addressDTO = new AddressDTO();
                    addressDTO.setCountry(vendor.getAddress().getCountry());
                    addressDTO.setStreetAddress(vendor.getAddress().getStreetAddress());
                    addressDTO.setApartmentNumber(vendor.getAddress().getApartmentNumber());
                    addressDTO.setProvince(vendor.getAddress().getProvince());
                    addressDTO.setCity(vendor.getAddress().getCity());
                    addressDTO.setPostalCode(vendor.getAddress().getPostalCode());

                    vendorDTO.setAddress(addressDTO);
                }


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
            Optional<Vendor> getVendorDetails = vendorRepo.findByVendorId(vendorId);
            if (getVendorDetails.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Business Owner Id does not Exist");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;

            }

            Vendor vendor = getVendorDetails.get();


            VendorDTO vendorDTO = new VendorDTO();


            vendorDTO.setUserId(vendor.getUserId());
            vendorDTO.setVendorId(vendor.getVendorId());
            vendorDTO.setFirstName(vendor.getFirstName());
            vendorDTO.setLastName(vendor.getLastName());
            vendorDTO.setPhoneNumber(vendor.getPhoneNumber());
            vendorDTO.setEmail(vendor.getEmail());
            vendorDTO.setBusinessName(vendor.getBusinessName());
            vendorDTO.setBusinessLogoUrl(vendor.getBusinessLogoUrl());
            vendorDTO.setTaxId(vendor.getTaxId());
            vendorDTO.setBusinessLicenseNumber(vendor.getBusinessLicenseNumber());
            vendorDTO.setIdCardBackUrl(vendor.getIdCardBackUrl());
            vendorDTO.setIdCardFrontUrl(vendor.getIdCardFrontUrl());

            if (vendor.getAddress() != null) {
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setCountry(vendor.getAddress().getCountry());
                addressDTO.setStreetAddress(vendor.getAddress().getStreetAddress());
                addressDTO.setApartmentNumber(vendor.getAddress().getApartmentNumber());
                addressDTO.setProvince(vendor.getAddress().getProvince());
                addressDTO.setCity(vendor.getAddress().getCity());
                addressDTO.setPostalCode(vendor.getAddress().getPostalCode());

                vendorDTO.setAddress(addressDTO);
            }


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

    public BaseResponse editVendor(EditVendor editVendor) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {

            Optional<Vendor> getBusinessOwnerDetails = vendorRepo.findByVendorId(editVendor.getVendorId());
            if (getBusinessOwnerDetails.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Vendor Id does not Exist");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;

            }

            Vendor vendor = getBusinessOwnerDetails.get();


            String newPhoneNumber = editVendor.getPhoneNumber();

            if (newPhoneNumber != null && !newPhoneNumber.trim().isEmpty()) {
                Optional<Vendor> existingSellerWithPhone = vendorRepo.findByPhoneNumber(newPhoneNumber.trim());
                if (existingSellerWithPhone.isPresent() && !Objects.equals(vendor.getPhoneNumber(), newPhoneNumber)) {
                    baseResponse.setStatusCode(ERROR_STATUS_CODE);
                    baseResponse.setMessage("Phone number already exists");
                    baseResponse.setData(EMPTY_DATA);
                    return baseResponse;
                }
                vendor.setPhoneNumber(newPhoneNumber.trim());
            }


            // Conditionally update other fields
            vendor.setUserId(reUsableFunctions.getUpdatedValue(editVendor.getUserId(), vendor.getUserId()));
            vendor.setVendorId(reUsableFunctions.getUpdatedValue(editVendor.getVendorId(), vendor.getPhoneNumber()));
            vendor.setPhoneNumber(reUsableFunctions.getUpdatedValue(editVendor.getPhoneNumber(), vendor.getFirstName()));
            vendor.setFirstName(reUsableFunctions.getUpdatedValue(editVendor.getFirstName(), vendor.getFirstName()));
            vendor.setLastName(reUsableFunctions.getUpdatedValue(editVendor.getLastName(), vendor.getLastName()));
            vendor.setBusinessName(reUsableFunctions.getUpdatedValue(editVendor.getBusinessName(), vendor.getBusinessName()));
            vendor.setTaxId(reUsableFunctions.getUpdatedValue(editVendor.getTaxId(), vendor.getTaxId()));
            vendor.setBusinessLogoUrl(reUsableFunctions.getUpdatedValue(editVendor.getBusinessLogoFile(), vendor.getBusinessLogoUrl()));
            vendor.setBusinessLicenseNumber(reUsableFunctions.getUpdatedValue(editVendor.getBusinessLicenseNumber(), vendor.getBusinessLicenseNumber()));
            vendor.setIdCardBackUrl(reUsableFunctions.getUpdatedValue(editVendor.getIdCardBackFile(), vendor.getIdCardBackUrl()));
            vendor.setIdCardFrontUrl(reUsableFunctions.getUpdatedValue(editVendor.getIdCardFrontFile(), vendor.getIdCardFrontUrl()));

            if (vendor.getAddress() != null && editVendor.getAddress() != null) {
                Address address = vendor.getAddress();
                EditAddress dto = editVendor.getAddress();

                address.setStreetAddress(reUsableFunctions.getUpdatedValue(dto.getStreetAddress(), address.getStreetAddress()));
                address.setCountry(reUsableFunctions.getUpdatedValue(dto.getCountry(), address.getCountry()));
                address.setPostalCode(reUsableFunctions.getUpdatedValue(dto.getPostalCode(), address.getPostalCode()));
                address.setProvince(reUsableFunctions.getUpdatedValue(dto.getProvince(), address.getProvince()));
                address.setApartmentNumber(reUsableFunctions.getUpdatedValue(dto.getApartmentNumber(), address.getApartmentNumber()));
                address.setCity(reUsableFunctions.getUpdatedValue(dto.getCity(), address.getCity()));
            }

            vendor.setUpdatedAt(LocalDateTime.now());

            vendorRepo.save(vendor);

            baseResponse.setMessage(SUCCESS_MESSAGE);
            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Vendor account created successfully.");
            baseResponse.setData(Map.of(
                    "vendorId", vendor.getVendorId(),
                    "email", vendor.getEmail(),
                    "businessName", vendor.getBusinessName()
            ));

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
            if (getBusinessOwnerDetails.isEmpty()) {
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

    public BaseResponse uploadStoreLogo(MultipartFile file) {
        BaseResponse response = new BaseResponse(true);

        try {
            if (file.isEmpty()) {
                response.setStatusCode("400");
                response.setMessage("File is empty.");
                response.setData(EMPTY_DATA);
                return response;
            }

            String fileUrl = fileStorageService.saveVendorLogo(file);
            response.setStatusCode(SUCCESS_STATUS_CODE);
            response.setMessage("Image uploaded successfully.");
            response.setData(Map.of("StoreLogoUrl", fileUrl));

        } catch (Exception e) {
            log.error("Error uploading store logo", e);
            response.setStatusCode("500");
            response.setMessage("Upload failed: " + e.getMessage());
            response.setData(EMPTY_DATA);
        }
        return response;
    }

    public BaseResponse createStore(StoreRegistration storeRegistration) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {

            Optional<Vendor> getVendorDetails = vendorRepo.findByVendorId(storeRegistration.getVendorId());
            if (getVendorDetails.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Business Owner Id does not Exist");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;

            }

            List<Store> checkStoreName = storeRepo.findStoreByStoreNameIgnoreCase(storeRegistration.getStoreName());
            if (!checkStoreName.isEmpty()) {
                baseResponse.setStatusCode("409");
                baseResponse.setMessage("Store Name exist for another user");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;
            }


            Vendor vendor = getVendorDetails.get();


            Store newStore = new Store();

            newStore.setStoreCode(reUsableFunctions.generateId(storeRegistration.getStoreName()));
            newStore.setVendor(vendor);
            newStore.setVendorCode(vendor.getVendorId());
            newStore.setStoreLogo(storeRegistration.getStoreLogo());
            newStore.setStoreName(storeRegistration.getStoreName());
            newStore.setStoreDescription(storeRegistration.getStoreDescription());
            newStore.setMaxDeliveryDistance(storeRegistration.getMaxDeliveryDistance());
            newStore.setPickupAvailable(Boolean.TRUE.equals(storeRegistration.getPickupAvailable()));
            newStore.setDeliveryAvailable(Boolean.TRUE.equals(storeRegistration.getDeliveryAvailable()));
            newStore.setStoreOpeningHours(LocalTime.parse(storeRegistration.getStoreOpeningHours()));
            newStore.setStoreClosingHours(LocalTime.parse(storeRegistration.getStoreClosingHours()));
            newStore.setStorePhoneNumber(vendor.getPhoneNumber());
            newStore.setStoreCategory(storeRegistration.getStoreCategory());


            Address address = new Address();
            if (storeRegistration.getAddress() != null) {
                address.setApartmentNumber(storeRegistration.getAddress().getApartmentNumber());
                address.setStreetAddress(storeRegistration.getAddress().getStreetAddress());
                address.setCountry(storeRegistration.getAddress().getCountry());
                address.setCity(storeRegistration.getAddress().getCity());
                address.setPostalCode(storeRegistration.getAddress().getPostalCode());
                address.setProvince(storeRegistration.getAddress().getProvince());
            }
            vendor.setAddress(address);


            storeRepo.save(newStore);

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Store created successfully.");
            baseResponse.setData(Map.of(
                    "StoreId", newStore.getStoreCode(),
                    "Vendor Code", newStore.getVendorCode(),
                    "Store Name", newStore.getStoreName()
            ));

        } catch (Exception e) {
            baseResponse.setStatusCode("500");
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse updateStore(EditStore editStore) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            Optional<Vendor> businessOwner = vendorRepo.findByVendorId(editStore.getVendorId());
            Optional<Store> getStore = storeRepo.findByStoreCode(editStore.getStoreCode());

            if (getStore.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Store ID does not exist");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            Store store = getStore.get();

            store.setStoreName(reUsableFunctions.getUpdatedValue(editStore.getStoreName(), store.getStoreName()));
            store.setStoreLogo(reUsableFunctions.getUpdatedValue(editStore.getStoreLogo(), store.getStoreLogo()));
            store.setStoreDescription(reUsableFunctions.getUpdatedValue(editStore.getStoreDescription(), store.getStoreDescription()));
            store.setMaxDeliveryDistance(reUsableFunctions.getUpdatedValue(editStore.getMaxDeliveryDistance(), store.getMaxDeliveryDistance()));
            store.setPickupAvailable(reUsableFunctions.getUpdatedValueBoolean(editStore.isPickupAvailable(), store.isPickupAvailable()));
            store.setDeliveryAvailable(reUsableFunctions.getUpdatedValueBoolean(editStore.isDeliveryAvailable(), store.isDeliveryAvailable()));
            store.setStoreCategory(reUsableFunctions.getUpdatedValue(editStore.getStoreCategory(), store.getStoreCategory()));
            store.setStoreOpeningHours(LocalTime.parse(reUsableFunctions.getUpdatedValue(editStore.getStoreOpeningHours(), String.valueOf(store.getStoreOpeningHours()))));
            store.setStoreClosingHours(LocalTime.parse(reUsableFunctions.getUpdatedValue(editStore.getStoreClosingHours(), String.valueOf(store.getStoreClosingHours()))));
            store.setUpdatedAt(LocalDateTime.now());


            if (store.getAddress() != null && editStore.getAddress() != null) {
                Address address = store.getAddress();
                EditAddress dto = editStore.getAddress();

                address.setStreetAddress(reUsableFunctions.getUpdatedValue(dto.getStreetAddress(), address.getStreetAddress()));
                address.setCountry(reUsableFunctions.getUpdatedValue(dto.getCountry(), address.getCountry()));
                address.setPostalCode(reUsableFunctions.getUpdatedValue(dto.getPostalCode(), address.getPostalCode()));
                address.setProvince(reUsableFunctions.getUpdatedValue(dto.getProvince(), address.getProvince()));
                address.setApartmentNumber(reUsableFunctions.getUpdatedValue(dto.getApartmentNumber(), address.getApartmentNumber()));
                address.setCity(reUsableFunctions.getUpdatedValue(dto.getCity(), address.getCity()));
            }


            storeRepo.save(store);

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Store updated successfully");
            baseResponse.setData(Map.of(
                    "StoreId", store.getStoreCode(),
                    "Vendor Code", store.getVendorCode(),
                    "Store Name", store.getStoreName()
            ));

        } catch (Exception e) {
            baseResponse.setStatusCode(ERROR_STATUS_CODE);
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse deleteStore(String storeCode) {

        BaseResponse baseResponse = new BaseResponse(true);

        try {

            Optional<Store> getStore = storeRepo.findByStoreCode(storeCode);

            if (getStore.isEmpty()) {
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
                    (storeName == null || storeName.isEmpty()))
            {
                stores = storeRepo.findAllByOrderByProductCodeDesc();
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

                storeDto.setStoreId(store.getStoreCode());
                storeDto.setStoreLogo(store.getStoreLogo());
                storeDto.setStoreName(store.getStoreName());
                storeDto.setStoreCategory(store.getStoreCategory());
                storeDto.setStoreDescription(store.getStoreDescription());
                storeDto.setStorePhoneNumber(store.getStorePhoneNumber());
                storeDto.setDeliveryAvailable(store.isDeliveryAvailable());
                storeDto.setPickupAvailable(store.isPickupAvailable());
                storeDto.setMaxDeliveryDistance(store.getMaxDeliveryDistance());
                storeDto.setStoreClosingHours(String.valueOf(store.getStoreClosingHours()));
                storeDto.setStoreOpeningHours(String.valueOf(store.getStoreOpeningHours()));


                if (store.getAddress() != null) {
                    AddressDTO addressDTO = new AddressDTO();
                    addressDTO.setCountry(store.getAddress().getCountry());
                    addressDTO.setStreetAddress(store.getAddress().getStreetAddress());
                    addressDTO.setApartmentNumber(store.getAddress().getApartmentNumber());
                    addressDTO.setProvince(store.getAddress().getProvince());
                    addressDTO.setCity(store.getAddress().getCity());
                    addressDTO.setPostalCode(store.getAddress().getPostalCode());

                    storeDto.setAddress(addressDTO);
                }


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

    public BaseResponse getStoreById(String storeCode) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            Optional<Store> fetchStore = storeRepo.findByStoreCode(storeCode);

            if (fetchStore.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Store Id Empty or Does Not Exist");
                baseResponse.setData(EMPTY_DATA);

                return baseResponse;
            }

            Store store = fetchStore.get();
            StoreDto storeDto = new StoreDto();

            storeDto.setStoreId(store.getStoreCode());
            storeDto.setStoreLogo(store.getStoreLogo());
            storeDto.setStoreName(store.getStoreName());
            storeDto.setStoreCategory(store.getStoreCategory());
            storeDto.setStoreDescription(store.getStoreDescription());
            storeDto.setStorePhoneNumber(store.getStorePhoneNumber());
            storeDto.setDeliveryAvailable(store.isDeliveryAvailable());
            storeDto.setPickupAvailable(store.isPickupAvailable());
            storeDto.setMaxDeliveryDistance(store.getMaxDeliveryDistance());
            storeDto.setStoreClosingHours(String.valueOf(store.getStoreClosingHours()));
            storeDto.setStoreOpeningHours(String.valueOf(store.getStoreOpeningHours()));


            if (store.getAddress() != null) {
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setCountry(store.getAddress().getCountry());
                addressDTO.setStreetAddress(store.getAddress().getStreetAddress());
                addressDTO.setApartmentNumber(store.getAddress().getApartmentNumber());
                addressDTO.setProvince(store.getAddress().getProvince());
                addressDTO.setCity(store.getAddress().getCity());
                addressDTO.setPostalCode(store.getAddress().getPostalCode());

                storeDto.setAddress(addressDTO);
            }


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

    public BaseResponse getStoreByKeyword(String keyword) {

        BaseResponse baseResponse = new BaseResponse(true);

        try {
            List<Store> storeList = storeRepo.searchByKeyword(keyword);
            List<StoreDto> allStoreResult = new ArrayList<>();

            if (storeList.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("No store found with the keyword " + keyword);
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }


            for (Store store : storeList) {

                StoreDto storeDto = new StoreDto();

                storeDto.setStoreId(store.getStoreCode());
                storeDto.setStoreLogo(store.getStoreLogo());
                storeDto.setStoreName(store.getStoreName());
                storeDto.setStoreCategory(store.getStoreCategory());
                storeDto.setStoreDescription(store.getStoreDescription());
                storeDto.setStorePhoneNumber(store.getStorePhoneNumber());
                storeDto.setDeliveryAvailable(store.isDeliveryAvailable());
                storeDto.setPickupAvailable(store.isPickupAvailable());
                storeDto.setMaxDeliveryDistance(store.getMaxDeliveryDistance());
                storeDto.setStoreClosingHours(String.valueOf(store.getStoreClosingHours()));
                storeDto.setStoreOpeningHours(String.valueOf(store.getStoreOpeningHours()));


                if (store.getAddress() != null) {
                    AddressDTO addressDTO = new AddressDTO();
                    addressDTO.setCountry(store.getAddress().getCountry());
                    addressDTO.setStreetAddress(store.getAddress().getStreetAddress());
                    addressDTO.setApartmentNumber(store.getAddress().getApartmentNumber());
                    addressDTO.setProvince(store.getAddress().getProvince());
                    addressDTO.setCity(store.getAddress().getCity());
                    addressDTO.setPostalCode(store.getAddress().getPostalCode());

                    storeDto.setAddress(addressDTO);
                }


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

                storeDto.setStoreId(store.getStoreCode());
                storeDto.setStoreLogo(store.getStoreLogo());
                storeDto.setStoreName(store.getStoreName());
                storeDto.setStoreCategory(store.getStoreCategory());
                storeDto.setStoreDescription(store.getStoreDescription());
                storeDto.setStorePhoneNumber(store.getStorePhoneNumber());
                storeDto.setDeliveryAvailable(store.isDeliveryAvailable());
                storeDto.setPickupAvailable(store.isPickupAvailable());
                storeDto.setMaxDeliveryDistance(store.getMaxDeliveryDistance());
                storeDto.setStoreClosingHours(String.valueOf(store.getStoreClosingHours()));
                storeDto.setStoreOpeningHours(String.valueOf(store.getStoreOpeningHours()));


                if (store.getAddress() != null) {
                    AddressDTO addressDTO = new AddressDTO();
                    addressDTO.setCountry(store.getAddress().getCountry());
                    addressDTO.setStreetAddress(store.getAddress().getStreetAddress());
                    addressDTO.setApartmentNumber(store.getAddress().getApartmentNumber());
                    addressDTO.setProvince(store.getAddress().getProvince());
                    addressDTO.setCity(store.getAddress().getCity());
                    addressDTO.setPostalCode(store.getAddress().getPostalCode());

                    storeDto.setAddress(addressDTO);
                }

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

    public BaseResponse uploadProductImage(MultipartFile file) {
        BaseResponse response = new BaseResponse(true);

        try {
            if (file.isEmpty()) {
                response.setStatusCode("400");
                response.setMessage("File is empty.");
                response.setData(EMPTY_DATA);
                return response;
            }

            String fileUrl = fileStorageService.saveProductImage(file);
            response.setStatusCode(SUCCESS_STATUS_CODE);
            response.setMessage("Image uploaded successfully.");
            response.setData(Map.of("productImageUrl", fileUrl));

        } catch (Exception e) {
            log.error("Error uploading store logo", e);
            response.setStatusCode("500");
            response.setMessage("Upload failed: " + e.getMessage());
            response.setData(EMPTY_DATA);
        }
        return response;
    }

    public BaseResponse createProduct(ProductRegistration productRegistration) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            Optional<Store> optionalStore = storeRepo.findByStoreCode(productRegistration.getStoreCode());
            if (optionalStore.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Store ID not valid");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            Store store = optionalStore.get();

            Product registeredProduct = new Product();
            registeredProduct.setProductCode(reUsableFunctions.generateId(productRegistration.getProductName()));
            registeredProduct.setProductName(productRegistration.getProductName());
            registeredProduct.setStoreCode(productRegistration.getStoreCode());
            registeredProduct.setBasePrice(productRegistration.getBasePrice());
            registeredProduct.setProductDescription(productRegistration.getProductDescription());
            registeredProduct.setProductImage(productRegistration.getProductImage());
            registeredProduct.setCategory(productRegistration.getProductCategory());



            registeredProduct.setVendor(store.getVendor());

//            registeredProduct.addStore(store);

            productRepo.save(registeredProduct);

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Product created successfully.");
            baseResponse.setData(Map.of(
                    "StoreCode", store.getStoreCode(),
                    "Product Code", registeredProduct.getProductCode(),
                    "Product Name", registeredProduct.getProductName(),
                    "Product Category", registeredProduct.getCategory()
            ));

        } catch (Exception e) {
            baseResponse.setStatusCode("500");
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse createBulkProduct(List<ProductRegistration> productRegistrationList) {
        BaseResponse baseResponse = new BaseResponse();

        try {
            if (productRegistrationList == null || productRegistrationList.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Product list cannot be empty.");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            String storeCode = productRegistrationList.get(0).getStoreCode();
            Optional<Store> optionalStore = storeRepo.findByStoreCode(storeCode);

            if (optionalStore.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Store ID not valid.");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            Store store = optionalStore.get();
            Vendor vendor = store.getVendor();

            List<Product> productsToSave = new ArrayList<>();
            List<Map<String, String>> createdProducts = new ArrayList<>();
            List<String> skippedProducts = new ArrayList<>();

            for (ProductRegistration data : productRegistrationList) {
                if (!storeCode.equals(data.getStoreCode())) {
                    skippedProducts.add(data.getProductName());
                    continue;
                }


                Product product = new Product();
                product.setProductCode(reUsableFunctions.generateId(data.getProductName()));
                product.setProductName(data.getProductName());
                product.setStoreCode(store.getStoreCode());
                product.setProductDescription(data.getProductDescription());
                product.setProductImage(data.getProductDescription());
                product.setCategory(data.getProductCategory());
                product.setBasePrice(data.getBasePrice());
                product.setVendor(vendor);
                product.addStore(store);

                productsToSave.add(product);

                createdProducts.add(Map.of(
                        "Product Code", product.getProductCode(),
                        "Product Name", product.getProductName(),
                        "Product Category" , product.getCategory()
                ));
            }

            productRepo.saveAll(productsToSave);

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Products processed successfully.");
            baseResponse.setData(EMPTY_DATA);

        } catch (Exception e) {
            baseResponse.setStatusCode("500");
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse editProduct(EditProduct editProduct) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            Optional<Product> existingProduct = productRepo.findProductByProductCode(editProduct.getProductCode());

            if (existingProduct.isEmpty()) {
                baseResponse.setStatusCode(ERROR_STATUS_CODE);
                baseResponse.setMessage("Product not found");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }



            Product product = existingProduct.get();
            // Update fields only if new values are provided
            if (editProduct.getProductName() != null) {
                product.setProductName(editProduct.getProductName());
            }
            if (editProduct.getProductDescription() != null) {
                product.setProductDescription(editProduct.getProductDescription());
            }
            if (editProduct.getProductImage() != null) {
                product.setProductImage(editProduct.getProductDescription());
            }
            if (editProduct.getCategory() != null) {
                product.setCategory(editProduct.getCategory());
            }
            if (editProduct.getBasePrice() != null) {
                product.setBasePrice(editProduct.getBasePrice());
            }

            productRepo.save(product);

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Product updated successfully.");
            baseResponse.setData(Map.of(
                    "StoreId", product.getStoreCode(),
                    "Product Code", product.getProductCode(),
                    "Product Name", product.getProductName()
            ));

        } catch (Exception e) {
            baseResponse.setStatusCode("500");
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse deleteProduct(String productCode) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            Optional<Product> existingProduct = productRepo.findProductByProductCode(productCode);

            if (existingProduct.isEmpty()) {
                baseResponse.setStatusCode("500");
                baseResponse.setMessage("Product not found");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            Product product = existingProduct.get();

            productRepo.delete(product);

            baseResponse.setStatusCode("200");
            baseResponse.setMessage("Product deleted successfully");
            baseResponse.setData(EMPTY_DATA);

        } catch (Exception e) {
            baseResponse.setStatusCode("500");
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse getAllProducts() {
        BaseResponse baseResponse = new BaseResponse(true);
        try {
            List<Product> fetchAllProducts = productRepo.findAll();

            if (fetchAllProducts.isEmpty()) {
                baseResponse.setStatusCode("404");
                baseResponse.setMessage("No products found");
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }



            List<ProductDTO> result = new ArrayList<>();

            for (Product product : fetchAllProducts) {
                ProductDTO productDTO = new ProductDTO();

                productDTO.setProductCode(product.getProductCode());
                productDTO.setProductName(product.getProductName());
                productDTO.setProductDescription(product.getProductDescription());
                productDTO.setProductImage(product.getProductImage());
                productDTO.setProductCategory(product.getCategory());
                productDTO.setBasePrice(product.getBasePrice().toString());


            result.add(productDTO);
            }

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE);
            baseResponse.setMessage("Products retrieved successfully");
            baseResponse.setData(result);

        } catch (Exception e) {
            baseResponse.setStatusCode("500");
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }

    public BaseResponse getAllProductsByName(String productName) {
        BaseResponse baseResponse = new BaseResponse(true);

        try {
            List<Product> targetProducts = productRepo.findProductByProductNameContainingIgnoreCase(productName);

            if (targetProducts.isEmpty()) {
                baseResponse.setStatusCode("404");
                baseResponse.setMessage("No products found matching: " + productName);
                baseResponse.setData(EMPTY_DATA);
                return baseResponse;
            }

            List<ProductDTO> result = new ArrayList<>();
            for (Product product : targetProducts) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProductCode(product.getProductCode());
                productDTO.setProductName(product.getProductName());
                productDTO.setProductDescription(product.getProductDescription());
                productDTO.setProductImage(product.getProductImage());
                productDTO.setProductCategory(product.getCategory());
                productDTO.setBasePrice(product.getBasePrice().toString());

                result.add(productDTO);
            }

            baseResponse.setStatusCode(SUCCESS_STATUS_CODE); // likely "200"
            baseResponse.setMessage("Products retrieved successfully");
            baseResponse.setData(result);

        } catch (Exception e) {
            baseResponse.setStatusCode("500");
            baseResponse.setMessage("An error occurred: " + e.getMessage());
            baseResponse.setData(EMPTY_DATA);
        }

        return baseResponse;
    }



// ---------------------------------Admin API'S-----------------------------







}
