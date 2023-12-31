package org.interswitch.bookstore.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class PagedResponse {
    Map<String, Object> pagedResponse;
}
