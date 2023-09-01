package com.comp3102.backend.core.utils;

import com.comp3102.backend.reservation.Reservation;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;

public class GenericUtilities {
    public static String decodeUrl(String givenUrl) {
        String decodedText = "";

        try {
            byte[] decodedUrl = Base64.getUrlDecoder().decode(givenUrl.getBytes(StandardCharsets.UTF_8));
            decodedText = new String(decodedUrl, StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return decodedText;
    }

    public static String encode(String givenUrl) {
        String encodedUrl = givenUrl;
        try {
            encodedUrl = Base64.getUrlEncoder().encodeToString(givenUrl.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedUrl;
    }
    public static LocalDateTime longToLocalDateConvert(long timestamp){
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static String calculateRemainingTimeToDeleteReservation(Reservation reservation, int durationInMinutes) {

        if (!reservation.getIsPaid()){
            LocalDateTime currentTime = LocalDateTime.now();
            long minutesDifference = Duration.between(reservation.getBookedAt(), currentTime).toMinutes()+1;
            long secondsDifference = Duration.between(reservation.getBookedAt(), currentTime).toSeconds() % 60;

            long remainingMinutes = durationInMinutes - minutesDifference;
            long remainingSeconds = 60 - secondsDifference;

            if (remainingSeconds == 60) {
                remainingMinutes++;
                remainingSeconds = 0;
            }
            if (remainingMinutes < 0){
                remainingMinutes = 0;
            }
            return  String.format("%02d:%02d", remainingMinutes, remainingSeconds);
        }else {
            return "00:00";
        }



    }


}
