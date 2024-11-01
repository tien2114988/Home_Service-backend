package com.threeChickens.homeService.enums;

import lombok.Getter;

@Getter
public enum MonthEnum {
    JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;

    public MonthEnum next() {
        // No bounds checking required here, because the last instance overrides
        return values()[ordinal() + 1];
    }
}
