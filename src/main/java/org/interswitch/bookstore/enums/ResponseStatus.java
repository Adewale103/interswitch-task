package org.interswitch.bookstore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    SUCCESS("SUCCESS"), ERROR("ERROR");

    private final String status;
}