package com.Afrochow.food_app.controller;
import com.Afrochow.food_app.pojo.*;
import com.Afrochow.food_app.services.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.Afrochow.food_app.config.AppConstant.ERROR_STATUS_CODE;

@CrossOrigin("*")
@RestController
@RequestMapping("/Afrochow/api")
@Tag(name = "Afrochow Food APis", description = "APIs for managing Users, Vendors, Stores & Products")




public class ApplicationController {

    @Autowired
    ApplicationService appService;


    @Operation(summary = "Test Connection", description = "Test the endpoint connection")
    @GetMapping("/")
    public String homepage(){
        return "Connected Successfully";
    }


    @Operation(summary = "Test Base Response", description = "Test the Base Response")
    @GetMapping("/testing")
    public ResponseEntity<?> testing(){
        BaseResponse baseResponse = appService.testing();
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }



    @Operation(summary = "Create a new Vendor", description = "Add a new Vendor to the DB")
    @PostMapping("vendor/register")
    public ResponseEntity<?> createAccount(@Valid @RequestBody VendorProfileData sellerAccountData){
        BaseResponse baseResponse = appService.createSellerAccount(sellerAccountData);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);

    }



    @Operation(summary = "Create a new Product", description = "Add a new Product to a store")
    @PostMapping("product/register")
    public ResponseEntity<?> createProduct (@Valid @RequestBody ProductData productData){
        BaseResponse baseResponse = appService.createProduct(productData);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(),"200") ?HttpStatus.OK :HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }


    @Operation(summary = "Create a Bulk Product", description = "Add a Bulk Product to a store")
    @PostMapping("product/bulkRegister")
    public ResponseEntity<?> createBulkProduct (@Valid @RequestBody List<ProductData> productDataList){
        BaseResponse baseResponse = appService.createBulkProduct(productDataList);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(),"200") ?HttpStatus.OK :HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }




    @Operation(summary = "Create a new user", description = "Add a new user to the DB")
    @PostMapping ("customer/register")
    public ResponseEntity<?> createUserAccount(@Valid @RequestBody UserProfileData userProfileData){
        BaseResponse baseResponse = appService.createAccount(userProfileData);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }


    @Operation(summary = "Create a new Store", description = "Add a new Store to the DB")
    @PostMapping("store/register")
    public ResponseEntity<?> createStore (@Valid @RequestBody StoreData storeData){
        BaseResponse baseResponse = appService.createStore(storeData);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(),"200") ?HttpStatus.OK :HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }


    @Operation(summary = "Update User Profile", description = "Modify Existing User Profile")
    @PutMapping("customer/update")
    public ResponseEntity<?> editUser (@Valid @RequestBody UpdateUserProfile updateUserProfile){
        BaseResponse baseResponse = appService.editUser(updateUserProfile);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }


    @Operation(summary = "Update Vendor Profile", description = "Modify Existing Vendor Profile")
    @PutMapping("vendor/update")
    public ResponseEntity<?> editSeller (@Valid @RequestBody UpdateVendorProfile updateVendorProfile){
        BaseResponse baseResponse = appService.editSeller(updateVendorProfile);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }


    @Operation(summary = "Update Store Information", description = "Modify Existing Store Information")
    @PutMapping("store/update")
    public ResponseEntity<?> editStore (@Valid @RequestBody UpdatedStoreData updatedStoreData){
        BaseResponse baseResponse = appService.updateStore(updatedStoreData);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(),"200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }


    @Operation(summary = "Get all user", description = "Retrieve a list of all user in the system")
    @GetMapping ("customer/all")
    public ResponseEntity<?> getAllUsers(){
        BaseResponse baseResponse = appService.getallUsers();
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }


    @Operation(summary = "Get all Vendor", description = "Retrieve a list of all Vendor in the system")
    @GetMapping ("vendor/all")
    public  ResponseEntity<?> getAllSellers() {
        BaseResponse baseResponse = appService.getAllSellers();
        HttpStatus status =  Objects.equals(baseResponse.getStatusCode(),"200") ? HttpStatus.OK :HttpStatus.BAD_REQUEST;
        return  new ResponseEntity<>(baseResponse, status);

    }


    @Operation(summary = "Get all Store", description = "Retrieve a list of all store in the system and search by StoreCategory or StoreName")
    @GetMapping("store/all")
    public ResponseEntity<?> getAllStores(
            @RequestParam(value = "storeCategory", required = false) String storeCategory,
            @RequestParam(value = "storeName", required = false) String storeName
    ) {
        BaseResponse baseResponse = appService.getStoresByFilter(storeCategory, storeName);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }


    @Operation(summary = "Get user by ID", description = "Retrieve a user's details using their ID")
    @GetMapping("customer")
    public ResponseEntity<?> getUserById(@RequestParam("userId") String userId) {
        BaseResponse baseResponse = appService.getUserById(userId);

        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200")
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(baseResponse, status);
    }


    @Operation(summary = "Get Vendor by ID", description = "Retrieve a vendor's details using their ID")
    @GetMapping ("vendor")
    public ResponseEntity<?> getSellerById (@RequestParam("sellerId") String sellerId){
        BaseResponse baseResponse = appService.getSellerById(sellerId);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200")
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(baseResponse, status);

    }


    @Operation(summary = "Get store by ID", description = "Retrieve a store's details using their ID")
    @GetMapping ("stores")
    public ResponseEntity <?> getStoreById (@RequestParam("storeId") String storeId) {
        BaseResponse baseResponse = appService.getStoreById(storeId);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200")
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(baseResponse, status);

    }


    @Operation(summary = "Get stores by keyword", description = "Retrieve store's details using keyword")
    @GetMapping("stores/search")
    public ResponseEntity <?> getStoreByKeyword(@RequestParam("q") String keyword) {
        BaseResponse baseResponse = appService.getStoreByKeyword(keyword);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);

    }


    @Operation(summary = "Get stores by location", description = "Retrieve store's details using their location")
    @GetMapping("stores/location")
    public ResponseEntity <?> getStoreByLocation(@RequestParam("location") String location) {
        BaseResponse baseResponse = appService.getStoreByLocation(location);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);

    }

    @Operation(summary = "Delete a user", description = "Delete a user from the system using their ID")
    @DeleteMapping("customer/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam("userId") String userId) {
        BaseResponse baseResponse = appService.deleteUser(userId);
        HttpStatus status = "200".equals(baseResponse.getStatusCode()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }

    @Operation(summary = "Delete a vendor", description = "Delete a vendor from the system using their ID")
    @DeleteMapping("vendor/delete")
    public ResponseEntity<?> deleteSeller (@RequestParam("sellerId") String sellerId) {
        BaseResponse baseResponse = appService.deleteSeller(sellerId);
        HttpStatus status = "200".equals(baseResponse.getStatusCode()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }

    @Operation(summary = "Delete a store", description = "Delete a store from the system using their ID")
    @DeleteMapping("store/delete-store")
    public ResponseEntity<?> deleteStore (@RequestParam("storeId") String storeId) {
        BaseResponse baseResponse = appService.deleteStore(storeId);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }


    

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(ERROR_STATUS_CODE);
        baseResponse.setMessage("An error occurred");
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        baseResponse.setData(errors);
        return baseResponse;

    }


}
