package com.Afrochow.food_app.response_dto;

import lombok.Data;

@Data
public class VendorDTO {

    private String userId;
    private String vendorId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private String businessName;
    private String businessLicenseNumber;
    private String taxId;

    private String idCardFrontUrl;
    private String idCardBackUrl;
    private String businessLogoUrl;

    private AddressDTO address;

}