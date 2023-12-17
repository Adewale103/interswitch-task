package org.interswitch.bookstore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genre {
    FICTION("FICTION"), THRILLER("THRILLER"), MYSTERY("MYSTERY"), HORROR("HORROR"), SATIRE("SATIRE"),POETRY("POETRY");
    private final String genre;
}
