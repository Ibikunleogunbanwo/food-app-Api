package com.Afrochow.food_app.controller;
import com.Afrochow.food_app.pojo.*;
import com.Afrochow.food_app.services.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.Afrochow.food_app.config.AppConstant.ERROR_STATUS_CODE;


@RestController
@RequestMapping("/food-api")


public class ApplicationController {
    //defining routes//
    @Autowired
    ApplicationService appService;


    @GetMapping("/testing")
    public ResponseEntity<?> testing(){
        BaseResponse baseResponse = appService.testing();
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }

    @PostMapping("vendor/register")
    public ResponseEntity<?> createAccount(@Valid @RequestBody VendorProfileData sellerAccountData){
        BaseResponse baseResponse = appService.createSellerAccount(sellerAccountData);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);

    }

    @PostMapping ("customer/register")
    public ResponseEntity<?> createUserAccount(@Valid @RequestBody UserProfileData userProfileData){
        BaseResponse baseResponse = appService.createAccount(userProfileData);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }


    @PostMapping("store/register")
    public ResponseEntity<?> createStore (@Valid @RequestBody StoreData storeData){
        BaseResponse baseResponse = appService.createStore(storeData);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(),"200") ?HttpStatus.OK :HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }


    @PutMapping("customer/update")
    public ResponseEntity<?> editUser (@Valid @RequestBody UpdateUserProfile updateUserProfile){
        BaseResponse baseResponse = appService.editUser(updateUserProfile);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }

    @PutMapping("vendor/update")
    public ResponseEntity<?> editSeller (@Valid @RequestBody UpdateVendorProfile updateVendorProfile){
        BaseResponse baseResponse = appService.editSeller(updateVendorProfile);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }


    @PutMapping("store/update")
    public ResponseEntity<?> editStore (@Valid @RequestBody UpdatedStoreData updatedStoreData){
        BaseResponse baseResponse = appService.updateStore(updatedStoreData);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(),"200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }


    @GetMapping ("customer")
    public ResponseEntity<?> getAllUsers(){
        BaseResponse baseResponse = appService.getallUsers();
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }

    @GetMapping ("vendor")
    public  ResponseEntity<?> getAllSellers() {
        BaseResponse baseResponse = appService.getAllSellers();
        HttpStatus status =  Objects.equals(baseResponse.getStatusCode(),"200") ? HttpStatus.OK :HttpStatus.BAD_REQUEST;
        return  new ResponseEntity<>(baseResponse, status);

    }

    @GetMapping("store")
    public ResponseEntity<?> getAllStores(
            @RequestParam(value = "storeCategory", required = false) String storeCategory,
            @RequestParam(value = "storeName", required = false) String storeName
    ) {
        BaseResponse baseResponse = appService.getStoresByFilter(storeCategory, storeName);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }



    @GetMapping ("customer/:id")
    public ResponseEntity<?> getUserById (@RequestParam("userId") String userId){
        BaseResponse baseResponse = appService.getUserById(userId);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }

    @GetMapping ("vendor/:id")
    public ResponseEntity<?> getSellerById (@RequestParam("sellerId") String sellerId){
        BaseResponse baseResponse = appService.getSellerById(sellerId);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);

    }


    @GetMapping ("stores/:id")
    public ResponseEntity <?> getStoreById (@RequestParam("storeId") String storeId) {
        BaseResponse baseResponse = appService.getStoreById(storeId);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(),"200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);

    }

    @GetMapping("stores/search")
    public ResponseEntity <?> getStoreByKeyword(@RequestParam("q") String keyword) {
        BaseResponse baseResponse = appService.getStoreByKeyword(keyword);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);

    }

    @GetMapping("stores/location")
    public ResponseEntity <?> getStoreByLocation(@RequestParam("location") String location) {
        BaseResponse baseResponse = appService.getStoreByLocation(location);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);

    }


    @DeleteMapping("customer/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam("userId") String userId) {
        BaseResponse baseResponse = appService.deleteUser(userId);
        HttpStatus status = "200".equals(baseResponse.getStatusCode()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }

    @DeleteMapping("vendor/delete")
    public ResponseEntity<?> deleteSeller (@RequestParam("sellerId") String sellerId) {
        BaseResponse baseResponse = appService.deleteSeller(sellerId);
        HttpStatus status = "200".equals(baseResponse.getStatusCode()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }


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
