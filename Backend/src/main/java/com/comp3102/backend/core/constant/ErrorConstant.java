package com.comp3102.backend.core.constant;

public class ErrorConstant {

    public static final String COLOR_NOT_FOUND_MESSAGE = "Color Not Found: ";
    public static final String COLOR_ALREADY_EXISTS_MESSAGE = "Color Already Exists: ";
    public static final String BRAND_NOT_FOUND_MESSAGE = "Brand Not Found: ";
    public static final String CAR_NOT_FOUND_MESSAGE = "Car Not Found: ";
    public static final String BRAND_ALREADY_EXISTS_MESSAGE = "Brand Already Exists: ";
    public static final String JOB_APPLICATION_ALREADY_EXISTS_MESSAGE = "Job Application Already Exists: ";
    public static final String JOB_NOT_FOUND_MESSAGE = "Job Application Not Found: ";
    public static final String USER_NOT_FOUND_MESSAGE = "User Not Found: ";
    public static final String USER_ALREADY_EXISTS_MESSAGE = "User Already Exists: ";
    public static final String RESERVATION_NOT_FOUND_MESSAGE = "Reservation Not Found: ";
    public static final String RESULT_NOT_FOUND_MESSAGE = "Result Not Found: ";
    public static final String START_DATE_BIGGER_THAN_END_DATE_ERROR_MESSAGE = "Start Date should not be bigger or equal to End Date.";
    public static final String USER_EMAIL_ALREADY_EXISTS = "User Email Already Exists: ";
    public static final String USER_IDENTITY_NUMBER_ALREADY_EXISTS = "User Identity Number Already Exists: ";
    public static final String USER_PHONE_NUMBER_ALREADY_EXISTS = "User Phone Number Already Exists: ";
    public static final String USER_HAS_PAID_RESERVATIONS_ERROR_MESSAGE = "User has paid reservations: ";
    public static final String USER_EMAIL_NOT_FOUND_ERROR_MESSAGE = "User Email Not Found: ";
    public static final String ADMIN_ROLE_CHANGE_ERROR_MESSAGE = "ADMIN role change operation does not allowed!!!";
    public static final String PROFILE_PICTURE_ERROR_MESSAGE = "Profile Picture Not Found: ";
    public static String errorMessageParser(String message, int data){
        return String.format("%s%d", message, data);
    }
    public static String errorMessageParser(String message, String data){
        return String.format("%s%s", message, data);
    }
}
