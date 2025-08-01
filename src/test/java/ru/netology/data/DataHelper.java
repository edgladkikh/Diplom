package ru.netology.data;

public class DataHelper {
    private DataHelper() {
    }

    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getNullCardNumber() {
        return "0000 0000 0000 0000";
    }

    public static String getIncompletelyMonth() {
        return "3";
    }

    public static String getNulllMonth() {
        return "00";
    }

    public static String getInvalidMonth() {
        return "13";
    }

    public static String getIncompletelyYear() {
        return "2";
    }

    public static String getNulllYear() {
        return "00";
    }

    public static String getNullCVC() {
        return "00";
    }

    public static String getInvalidOwnerSymbols() {
        return "Iv@n Iv@nov";
    }

    public static String getInvalidOwnerRus() {
        return "Иван Иванов";
    }

    public static String getInvalidOwnerOnlyName() {
        return "Ivan";
    }

    public static String getInvalidOwnerNumbers() {
        return "1van 1vanov";
    }
}