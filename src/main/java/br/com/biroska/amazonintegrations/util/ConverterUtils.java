package br.com.biroska.amazonintegrations.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConverterUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate stringToLocalDate(String dateString) {

        return LocalDate.parse(dateString, FORMATTER);
    }

    public static String dateToString(LocalDate date) {

        return date.format(FORMATTER);
    }

}
