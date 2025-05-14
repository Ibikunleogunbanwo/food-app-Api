package com.Afrochow.food_app.config;

public final class AppConstant {

    // Status Codes
    public static final String ERROR_STATUS_CODE = "400";
    public static final String SUCCESS_STATUS_CODE = "200";

    // Status Messages
    public static final String ERROR_MESSAGE = "Unsuccessful, an error occurred.";
    public static final String SUCCESS_MESSAGE = "Successful";

    // Empty Data
    public static final Object[] EMPTY_DATA = new Object[0];

    // Password Pattern: At least 1 digit, 1 lowercase, 1 uppercase, 1 special character, length 6-20
    public static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\\-\\[\\]{}:;'?/*~$^+=<>.]).{6,20}$";


    public static final String PHONE_NUMBER_PATTERN = "^\\+?[0-9\\-\\s]{10,15}$";
    public static final String PHONE_NUMBER_VALIDATION = "^\\d{10}$";

    public static final String POSTAL_CODE_VALIDATION = "^[A-Za-z]\\d[A-Za-z] ?\\d[A-Za-z]\\d$";




}
