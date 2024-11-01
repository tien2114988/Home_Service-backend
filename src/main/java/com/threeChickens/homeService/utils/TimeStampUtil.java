//package com.larkEWA.utils;
//
//import org.springframework.stereotype.Component;
//
//import java.time.*;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//
//@Component
//public class TimeStampUtil {
//    public int convertDateToInt(ZonedDateTime date){
//        // Define the desired format
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//
//        // Format the current date to the desired pattern
//        String formattedDate = date.format(formatter);
//
//        return Integer.parseInt(formattedDate);
//    }
//
//    public LocalDate convertIntToDate(int dateInt) {
//        // Convert the integer to a string in "yyyyMMdd" format
//        String dateString = String.valueOf(dateInt);
//
//        // Define the format of the input date string
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//
//        // Parse the string to a LocalDate
//        return LocalDate.parse(dateString, formatter);
//    }
//
//    public LocalDateTime convertToLocalDateTime(String timestampString) {
//        long timestamp = Long.parseLong(timestampString);
//
//        // Convert the Unix timestamp to an Instant
//        Instant instant = Instant.ofEpochSecond(timestamp);
//
//        // Convert the Instant to a LocalDateTime using the Vietnam time zone
//        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
//        return LocalDateTime.ofInstant(instant, zoneId);
//    }
//}
