package com.Afrochow.food_app.controller;
import com.Afrochow.food_app.pojo.*;
import com.Afrochow.food_app.services.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


//    ----------------Image Upload Endpoints---------

    @PostMapping(value = "product/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProductImage(@RequestPart("file") MultipartFile file) {
        BaseResponse baseResponse = appService.uploadProductImage(file);
        HttpStatus status = "200".equals(baseResponse.getStatusCode()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }

    @PostMapping(value = "store/logo-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadStoreLogo(@RequestPart("file") MultipartFile file) {
        BaseResponse baseResponse = appService.uploadStoreLogo(file);
        HttpStatus status = "200".equals(baseResponse.getStatusCode()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }

    @PostMapping(value = "vendor/vendor-media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadVendorMedia(
            @RequestPart("idCardFront") MultipartFile idCardFrontFile,
            @RequestPart("idCardBack") MultipartFile idCardBackFile,
            @RequestPart("businessLogo") MultipartFile businessLogoFile
    ) {
        BaseResponse response = appService.uploadVendorImages(idCardFrontFile, idCardBackFile, businessLogoFile);

        HttpStatus status;
        switch (response.getStatusCode()) {
            case "200" -> status = HttpStatus.OK;
            case "413" -> status = HttpStatus.PAYLOAD_TOO_LARGE;
            case "500" -> status = HttpStatus.INTERNAL_SERVER_ERROR;
            default -> status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(response, status);
    }





//    ...................CREATING ENDPOINTS...................

    @Operation(summary = "Register a vendor")
    @PostMapping("/vendor/register")
    public ResponseEntity<BaseResponse> createVendorAccount(@Valid @RequestBody VendorRegistration vendorRegistration) {
        BaseResponse baseResponse = appService.createVendorAccount(vendorRegistration);
        HttpStatus status;
        switch (baseResponse.getStatusCode()) {
            case "200" -> status = HttpStatus.OK;
            case "409" -> status = HttpStatus.CONFLICT;
            case "500" -> status = HttpStatus.INTERNAL_SERVER_ERROR;
            default -> status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(baseResponse, status);
    }


    @Operation(summary = "Create a new Product", description = "Add a new Product to a store")
    @PostMapping(value = "product/register")
    public ResponseEntity<?> createProduct(
            @Valid @ModelAttribute ProductRegistration productRegistration) {
        BaseResponse baseResponse = appService.createProduct(productRegistration);
        HttpStatus status = "200".equals(baseResponse.getStatusCode()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }

    @Operation(summary = "Create a Bulk Product", description = "Add a Bulk Product to a store")
    @PostMapping("product/bulkRegister")
    public ResponseEntity<?> createBulkProduct (@Valid @RequestBody List<ProductRegistration> productRegistrationList){
        BaseResponse baseResponse = appService.createBulkProduct(productRegistrationList);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(),"200") ?HttpStatus.OK :HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }


    @Operation(summary = "Create a new Customer", description = "Add a new Customer to the DB")
    @PostMapping ("customer/register")
    public ResponseEntity<?> createCustomerAccount(@Valid @RequestBody CustomerRegistration customerRegistration){
        BaseResponse baseResponse = appService.createCustomerAccount(customerRegistration);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }


    @Operation(summary = "Create a new Store", description = "Add a new Store to the DB")
    @PostMapping("store/register")
    public ResponseEntity<?> createStore (@Valid @RequestBody StoreRegistration storeRegistration){
        BaseResponse baseResponse = appService.createStore(storeRegistration);
        HttpStatus status;
        switch (baseResponse.getStatusCode()) {
            case "200" -> status = HttpStatus.OK;
            case "409" -> status = HttpStatus.CONFLICT;
            case "500" -> status = HttpStatus.INTERNAL_SERVER_ERROR;
            default -> status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(baseResponse,status);
    }




//...........................UPDATING ENDPOINTS....................

    @Operation(summary = "Update User Profile", description = "Modify Existing User Profile")
    @PutMapping("customer/update")
    public ResponseEntity<?> editUser (@Valid @RequestBody EditCustomer editCustomer){
        BaseResponse baseResponse = appService.editCustomer(editCustomer);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }


    @Operation(summary = "Update Vendor Profile", description = "Modify Existing Vendor Profile")
    @PutMapping("vendor/update")
    public ResponseEntity<?> editSeller (@Valid @RequestBody EditVendor editVendor){
        BaseResponse baseResponse = appService.editVendor(editVendor);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }


    @Operation(summary = "Update Store Information", description = "Modify Existing Store Information")
    @PutMapping("store/update")
    public ResponseEntity<?> editStore (@Valid @RequestBody EditStore editStore){
        BaseResponse baseResponse = appService.updateStore(editStore);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(),"200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }


    @Operation(summary = "Update Product", description = "Modify Existing Product")
    @PutMapping("store/update-product")
    public ResponseEntity<?> editProduct (@Valid @RequestBody EditProduct editProduct){
        BaseResponse baseResponse = appService.editProduct(editProduct);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(),"200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse,status);
    }



//    .................FETCHING DATA FROM ALL ENDPOINTS.............

    @Operation(summary = "Get all user", description = "Retrieve a list of all user in the system")
    @GetMapping ("customer/all")
    public ResponseEntity<?> getAllCustomers(){
        BaseResponse baseResponse = appService.getAllCustomers();
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }

    @Operation(summary = "Get all Vendor", description = "Retrieve a list of all Vendor in the system")
    @GetMapping ("vendors/all")
    public  ResponseEntity<?> getAllVendor() {
        BaseResponse baseResponse = appService.getAllVendor();
        HttpStatus status =  Objects.equals(baseResponse.getStatusCode(),"200") ? HttpStatus.OK :HttpStatus.BAD_REQUEST;
        return  new ResponseEntity<>(baseResponse, status);

    }

    @Operation(summary = "Get all Product", description = "Retrieve a list of all Product in the system")
    @GetMapping ("products/all")
    public  ResponseEntity<?> getAllProducts() {
        BaseResponse baseResponse = appService.getAllProducts();
        HttpStatus status =  Objects.equals(baseResponse.getStatusCode(),"200") ? HttpStatus.OK :HttpStatus.BAD_REQUEST;
        return  new ResponseEntity<>(baseResponse, status);
    }


    @Operation(summary = "Get Product by Name", description = "Retrieve a products details using their name")
    @GetMapping("product/name")
    public ResponseEntity<?> getProductByName(@RequestParam("productName") String productName) {
        BaseResponse baseResponse = appService.getAllProductsByName(productName);

        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200")
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(baseResponse, status);
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
    public ResponseEntity<?> getCustomerById(@RequestParam("userId") String userCode) {
        BaseResponse baseResponse = appService.getCustomerById(userCode);

        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200")
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(baseResponse, status);
    }


    @Operation(summary = "Get Vendor by ID", description = "Retrieve a vendor's details using their ID")
    @GetMapping ("vendor")
    public ResponseEntity<?> getVendorById(@RequestParam("vendorId") String vendorCode){
        BaseResponse baseResponse = appService.getVendorById(vendorCode);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200")
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(baseResponse, status);

    }


    @Operation(summary = "Get store by ID", description = "Retrieve a store's details using their ID")
    @GetMapping ("stores")
    public ResponseEntity <?> getStoreById (@RequestParam("storeCode") String storeCode) {
        BaseResponse baseResponse = appService.getStoreById(storeCode);
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


//    ...............DELETE ENDPOINTS................

    @Operation(summary = "Delete a user", description = "Delete a user from the system using their ID")
    @DeleteMapping("customer/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam("userCode") String userCode) {
        BaseResponse baseResponse = appService.deleteCustomer(userCode);
        HttpStatus status = "200".equals(baseResponse.getStatusCode()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }

    @Operation(summary = "Delete a vendor", description = "Delete a vendor from the system using their ID")
    @DeleteMapping("vendor/delete-vendor")
    public ResponseEntity<?> deleteVendor(@RequestParam("Vendor Code") String vendorCode) {
        BaseResponse baseResponse = appService.deleteVendor(vendorCode);
        HttpStatus status = "200".equals(baseResponse.getStatusCode()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }

    @Operation(summary = "Delete a Store", description = "Delete a Store from the system using their ID")
    @DeleteMapping("store/delete-store")
    public ResponseEntity<?> deleteStore (@RequestParam("storeCode") String storeCode) {
        BaseResponse baseResponse = appService.deleteStore(storeCode);
        HttpStatus status = Objects.equals(baseResponse.getStatusCode(), "200") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(baseResponse, status);
    }

    @Operation(summary = "Delete a Product", description = "Delete a Product from the system using their ID")
    @DeleteMapping("store/delete-product")
    public ResponseEntity<?> deleteProduct (@RequestParam("productCode") String productCode) {
        BaseResponse baseResponse = appService.deleteProduct(productCode);
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
