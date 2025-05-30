package com.Hyperfume.Backend.dto.response;

import java.util.Collections;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    int totalPages;
    int pageSize;
    long totalElements;

    @Builder.Default
    List<T> Data = Collections.emptyList();
}
